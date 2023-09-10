package me.dreamdevs.randomlootchest.listeners;

import me.dreamdevs.randomlootchest.api.database.IPlayerData;
import me.dreamdevs.randomlootchest.api.objects.IChestGame;
import me.dreamdevs.randomlootchest.RandomLootChestMain;
import me.dreamdevs.randomlootchest.api.events.PlayerInteractChestEvent;
import me.dreamdevs.randomlootchest.menus.ChestPlaceMenu;
import me.dreamdevs.randomlootchest.objects.WandItem;
import me.dreamdevs.randomlootchest.utils.Settings;
import me.dreamdevs.randomlootchest.utils.TimeUtil;
import me.dreamdevs.randomlootchest.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.concurrent.atomic.AtomicInteger;

public class PlayerInteractListener implements Listener {

    private final RandomLootChestMain plugin;

    public PlayerInteractListener(RandomLootChestMain plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteractChest(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        if (event.getClickedBlock() == null || event.getClickedBlock().getType() != Material.CHEST) {
            return;
        }
        if (!plugin.getLocationManager().getLocations().containsKey(Util.getLocationString(event.getClickedBlock().getLocation()))) {
            return;
        }
        if (event.getPlayer().getInventory().getItemInMainHand().equals(WandItem.WANDITEM)) {
            return;
        }

        event.setCancelled(true);

        PlayerInteractChestEvent playerInteractChestEvent = new PlayerInteractChestEvent(event.getPlayer(),
                event.getClickedBlock().getLocation(), false);
        Bukkit.getPluginManager().callEvent(playerInteractChestEvent);

        // Maybe something will cancel this event, so...
        if (playerInteractChestEvent.isCancelled()) {
            return;
        }

        if (Settings.combatEnabled) {
            if (RandomLootChestMain.getInstance().getCombatManager().isInCombat(event.getPlayer())) {
                event.getPlayer().sendMessage(plugin.getMessagesManager().getMessages().get("combat-info")
                        .replaceAll("%TIME%",
                                TimeUtil.formattedTime(plugin.getCooldownManager().getCooldownByLocation(event.getPlayer(),
                                event.getClickedBlock().getLocation()))));
                    return;
            }
        }

        IPlayerData playerData = plugin.getCooldownManager().getPlayerData(event.getPlayer());

        if (Settings.personalCooldown) {
            if (playerData.hasCooldown(event.getClickedBlock().getLocation())) {
                event.getPlayer().sendMessage(plugin.getMessagesManager().getMessages().get("chest-on-cooldown")
                        .replaceAll("%TIME%",
                                TimeUtil.formattedTime(plugin.getCooldownManager().getCooldownByLocation(event.getPlayer(),
                                        event.getClickedBlock().getLocation()))));
                return;
            }
        } else {
            if (RandomLootChestMain.getInstance().getCooldownManager().getCooldownForAllByLocation(event.getClickedBlock().getLocation()) > 0) {
                event.getPlayer().sendMessage(plugin.getMessagesManager().getMessages().get("chest-on-cooldown")
                        .replaceAll("%TIME%",
                                TimeUtil.formattedTime(plugin.getCooldownManager().getCooldownForAllByLocation(event.getClickedBlock().getLocation()))));

                return;
            }
        }

        if (Settings.randomChests) {
            IChestGame chestGame = plugin.getChestsManager().getRandomChest();
            plugin.getChestsManager().openChest(event.getPlayer(), chestGame.getId());
            if (Settings.personalCooldown) {
                plugin.getCooldownManager().setCooldown(event.getPlayer(), event.getClickedBlock().getLocation(), (int) chestGame.getTime(), true);
            } else {
                plugin.getCooldownManager().getLocations().put(event.getClickedBlock().getLocation(), new AtomicInteger((int) chestGame.getTime()));
            }
            return;
        }

        String type = plugin.getLocationManager().getLocations().get(Util.getLocationString(event.getClickedBlock().getLocation()));
        plugin.getChestsManager().openChest(event.getPlayer(), type);

        if (Settings.personalCooldown) {
            plugin.getCooldownManager().setCooldown(event.getPlayer(), event.getClickedBlock().getLocation(), (int) plugin.getChestsManager().getChestGameByRarity(type).getTime(), true);
        } else {
            plugin.getCooldownManager().getLocations().put(event.getClickedBlock().getLocation(), new AtomicInteger((int) plugin.getChestsManager().getChestGameByRarity(type).getTime()));
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
            new ChestPlaceMenu(event.getBlock().getLocation()).open(event.getPlayer());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void removeBlockEvent(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getPlayer().getInventory().getItemInMainHand() == null) return;
        if (event.getClickedBlock().getType() != Material.CHEST) return;
        if (!event.getPlayer().getInventory().getItemInMainHand().equals(WandItem.WANDITEM)) return;
        if(Settings.wandItemPermissionToUse && !event.getPlayer().hasPermission("wanditem.permission"))
            return;
        if (plugin.getLocationManager().getLocations().containsKey(Util.getLocationString(event.getClickedBlock().getLocation()))) {
            IChestGame chestGame = RandomLootChestMain.getInstance().getChestsManager().getChestGameByLocation(event.getClickedBlock().getLocation());
            event.setCancelled(true);
            event.getPlayer().sendMessage(plugin.getMessagesManager().getMessages().get("chest-remove-from-map").replaceAll("%TYPE%", chestGame.getTitle()));
            plugin.getLocationManager().removeLocation(event.getClickedBlock().getLocation());
          //  ChestRemoveEvent chestRemoveEvent = new ChestRemoveEvent(event.getPlayer().getUniqueId(), chestGame, event.getClickedBlock().getLocation());
           // Bukkit.getPluginManager().callEvent(chestRemoveEvent);
        }
    }

}