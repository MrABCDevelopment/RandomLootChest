package me.dreamdevs.github.randomlootchest.listeners;

import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.api.events.ChestRemoveEvent;
import me.dreamdevs.github.randomlootchest.api.events.PlayerInteractChestEvent;
import me.dreamdevs.github.randomlootchest.api.menu.place.ChestPlaceMenu;
import me.dreamdevs.github.randomlootchest.api.objects.WandItem;
import me.dreamdevs.github.randomlootchest.api.objects.ChestGame;
import me.dreamdevs.github.randomlootchest.utils.Settings;
import me.dreamdevs.github.randomlootchest.utils.TimeUtil;
import me.dreamdevs.github.randomlootchest.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {

    private final RandomLootChestMain plugin;

    public PlayerInteractListener(RandomLootChestMain plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteractChest(PlayerInteractEvent event) {
        if(event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if(!plugin.getLocationManager().getLocations().containsKey(Util.getLocationString(event.getClickedBlock().getLocation()))) return;
        if(event.getClickedBlock().getType() == Material.CHEST) {
            event.setCancelled(true);

            PlayerInteractChestEvent playerInteractChestEvent = new PlayerInteractChestEvent(event.getPlayer(), event.getClickedBlock().getLocation());
            Bukkit.getPluginManager().callEvent(playerInteractChestEvent);

            if(Settings.combatEnabled) {
                if(RandomLootChestMain.getInstance().getCombatManager().isInCombat(event.getPlayer())) {
                    event.getPlayer().sendMessage(plugin.getMessagesManager().getMessages().get("combat-info").replaceAll("%TIME%", TimeUtil.formattedTime(plugin.getCooldownManager().getPlayerCooldown(event.getPlayer().getUniqueId(), event.getClickedBlock().getLocation()))));
                    return;
                }
            }

            if(plugin.getCooldownManager().isOnCooldown(event.getPlayer().getUniqueId(), event.getClickedBlock().getLocation())) {
                event.getPlayer().sendMessage(plugin.getMessagesManager().getMessages().get("chest-on-cooldown").replace("%TIME%", TimeUtil.formattedTime(plugin.getCooldownManager().getPlayerCooldown(event.getPlayer().getUniqueId(), event.getClickedBlock().getLocation()))));
                return;
            }

            if(Settings.randomChests) {
                ChestGame chestGame = plugin.getChestsManager().getRandomChest();
                plugin.getGameManager().openChest(event.getPlayer(), chestGame.getId());
                plugin.getCooldownManager().setCooldown(event.getPlayer().getUniqueId(), event.getClickedBlock().getLocation(), (int) chestGame.getTime(), true);
                return;
            }

            String type = plugin.getLocationManager().getLocations().get(Util.getLocationString(event.getClickedBlock().getLocation()));
            plugin.getGameManager().openChest(event.getPlayer(), type);
            plugin.getCooldownManager().setCooldown(event.getPlayer().getUniqueId(), event.getClickedBlock().getLocation(), (int) plugin.getChestsManager().getChestGameByRarity(type).getTime(), true);
        }
    }

    @EventHandler
    public void addBlockEvent(BlockBreakEvent event) {
        if(event.getPlayer().getInventory().getItemInMainHand() == null) return;
        if(event.getBlock().getType() != Material.CHEST) return;
        if(plugin.getLocationManager().getLocations().containsKey(Util.getLocationString(event.getBlock().getLocation()))) {
            event.setCancelled(true);
        } else {
            if (!event.getPlayer().getInventory().getItemInMainHand().equals(WandItem.WANDITEM))
                return;
            if (Settings.wandItemPermissionToUse && !event.getPlayer().hasPermission("wanditem.permission"))
                return;
            event.setCancelled(true);
            new ChestPlaceMenu(event.getPlayer(), event.getBlock().getLocation());
        }
    }

    @EventHandler
    public void removeBlockEvent(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getPlayer().getInventory().getItemInMainHand() == null) return;
        if (event.getClickedBlock().getType() != Material.CHEST) return;
        if (!event.getPlayer().getInventory().getItemInMainHand().equals(WandItem.WANDITEM)) return;
        if(Settings.wandItemPermissionToUse && !event.getPlayer().hasPermission("wanditem.permission"))
            return;
        if (plugin.getLocationManager().getLocations().containsKey(Util.getLocationString(event.getClickedBlock().getLocation()))) {
            Location location = event.getClickedBlock().getLocation();
            ChestGame chestGame = RandomLootChestMain.getInstance().getChestsManager().getChestGameByLocation(location);
            event.setCancelled(true);
            event.getPlayer().sendMessage(plugin.getMessagesManager().getMessages().get("chest-remove-from-map"));
            plugin.getLocationManager().removeLocation(event.getClickedBlock().getLocation());
            ChestRemoveEvent chestRemoveEvent = new ChestRemoveEvent(event.getPlayer().getUniqueId(), chestGame, location);
            Bukkit.getPluginManager().callEvent(chestRemoveEvent);
        }
    }

}