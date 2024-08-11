package me.dreamdevs.randomlootchest.listeners;

import me.dreamdevs.randomlootchest.api.Config;
import me.dreamdevs.randomlootchest.api.Language;
import me.dreamdevs.randomlootchest.api.database.IPlayerData;
import me.dreamdevs.randomlootchest.RandomLootChestMain;
import me.dreamdevs.randomlootchest.api.event.chest.ChestDisappearEvent;
import me.dreamdevs.randomlootchest.api.event.player.PlayerDestroyChestEvent;
import me.dreamdevs.randomlootchest.api.event.player.PlayerInteractChestEvent;
import me.dreamdevs.randomlootchest.api.object.IChestGame;
import me.dreamdevs.randomlootchest.api.util.Util;
import me.dreamdevs.randomlootchest.menus.ChestPlaceMenu;
import me.dreamdevs.randomlootchest.objects.WandItem;
import me.dreamdevs.randomlootchest.utils.TimeUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.concurrent.atomic.AtomicInteger;

public final class PlayerInteractListener implements Listener {

    private final RandomLootChestMain plugin;
    private static final String TIME_PLACEHOLDER = "%TIME%";

    public PlayerInteractListener(RandomLootChestMain plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteractChest(final PlayerInteractEvent event) {
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
                event.getClickedBlock().getLocation());
        Bukkit.getPluginManager().callEvent(playerInteractChestEvent);

        // Maybe something will cancel this event, so...
        if (playerInteractChestEvent.isCancelled()) {
            return;
        }

        if (Config.USE_COMBAT_BLOCKER.toBoolean() && RandomLootChestMain.getInstance().getCombatManager().isInCombat(event.getPlayer())) {
            event.getPlayer().sendMessage(Language.GENERAL_COMBAT_MESSAGE.toString()
                    .replace(TIME_PLACEHOLDER,
                            TimeUtil.formattedTime(plugin.getCooldownManager().getCooldownByLocation(event.getPlayer(),
                            event.getClickedBlock().getLocation()))));
                    return;
        }

        IPlayerData playerData = plugin.getCooldownManager().getPlayerData(event.getPlayer().getUniqueId());

        if (Config.USE_PERSONAL_COOLDOWN.toBoolean()) {
            if (playerData.hasCooldown(event.getClickedBlock().getLocation())) {
                event.getPlayer().sendMessage(Language.CHEST_COOLDOWN_MESSAGE.toString()
                        .replace(TIME_PLACEHOLDER,
                                TimeUtil.formattedTime(plugin.getCooldownManager().getCooldownByLocation(event.getPlayer(),
                                        event.getClickedBlock().getLocation()))));
                return;
            }
        } else {
            if (RandomLootChestMain.getInstance().getCooldownManager().getCooldownForAllByLocation(event.getClickedBlock().getLocation()) > 0) {
                event.getPlayer().sendMessage(Language.CHEST_COOLDOWN_MESSAGE.toString()
                        .replace(TIME_PLACEHOLDER,
                                TimeUtil.formattedTime(plugin.getCooldownManager().getCooldownForAllByLocation(event.getClickedBlock().getLocation()))));

                return;
            }
        }

        if (Config.USE_RANDOM_CHESTS.toBoolean()) {
            IChestGame chestGame = plugin.getChestsManager().getRandomChest();
            plugin.getChestsManager().openChest(event.getPlayer(), chestGame.getId());
            if (Config.USE_PERSONAL_COOLDOWN.toBoolean()) {
                plugin.getCooldownManager().setCooldown(event.getPlayer().getUniqueId(), event.getClickedBlock().getLocation(), (int) chestGame.getTime(), true);
            } else {
                plugin.getCooldownManager().getLocations().put(event.getClickedBlock().getLocation(), new AtomicInteger((int) chestGame.getTime()));
            }
            return;
        }

        String type = plugin.getLocationManager().getLocations().get(Util.getLocationString(event.getClickedBlock().getLocation()));
        IChestGame chestGame = plugin.getChestsManager().getChestGameByRarity(type);

        plugin.getChestsManager().openChest(event.getPlayer(), type);

        if (Config.USE_PERSONAL_COOLDOWN.toBoolean()) {
            plugin.getCooldownManager().setCooldown(event.getPlayer().getUniqueId(), event.getClickedBlock().getLocation(), (int) chestGame.getTime(), true);
        } else {
            ChestDisappearEvent chestDisappearEvent = new ChestDisappearEvent(chestGame, event.getClickedBlock().getLocation());
            Bukkit.getPluginManager().callEvent(chestDisappearEvent);

            event.getClickedBlock().setType(Material.AIR);
            plugin.getCooldownManager().getLocations().put(event.getClickedBlock().getLocation(), new AtomicInteger((int) chestGame.getTime()));
        }
    }

    @EventHandler
    public void addBlockEvent(BlockBreakEvent event) {
        if (event.getBlock().getType() != Material.CHEST) {
            return;
        }
        if (plugin.getLocationManager().getLocations().containsKey(Util.getLocationString(event.getBlock().getLocation()))) {
            event.setCancelled(true);
        } else {
            if (!event.getPlayer().getInventory().getItemInMainHand().equals(WandItem.WANDITEM))
                return;
            if (Config.USE_WAND_ITEM_PERMISSION.toBoolean() && !event.getPlayer().hasPermission("wanditem.permission"))
                return;
            event.setCancelled(true);
            new ChestPlaceMenu(event.getBlock().getLocation()).open(event.getPlayer());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void removeBlockEvent(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        if (event.getClickedBlock() != null && event.getClickedBlock().getType() != Material.CHEST) {
            return;
        }
        if (!event.getPlayer().getInventory().getItemInMainHand().equals(WandItem.WANDITEM)) {
            return;
        }
        if (Config.USE_WAND_ITEM_PERMISSION.toBoolean() && !event.getPlayer().hasPermission("wanditem.permission"))
            return;
        if (plugin.getLocationManager().getLocations().containsKey(Util.getLocationString(event.getClickedBlock().getLocation()))) {
            IChestGame chestGame = RandomLootChestMain.getInstance().getChestsManager().getChestGameByLocation(event.getClickedBlock().getLocation());
            event.setCancelled(true);
            event.getPlayer().sendMessage(Language.CHEST_REMOVE_FROM_MAP_MESSAGE.toString().replace("%TYPE%", chestGame.getTitle()));
            plugin.getLocationManager().removeLocation(event.getClickedBlock().getLocation());
            plugin.getLocationManager().save();
            PlayerDestroyChestEvent chestRemoveEvent = new PlayerDestroyChestEvent(event.getPlayer().getUniqueId(), chestGame, event.getClickedBlock().getLocation());
            Bukkit.getPluginManager().callEvent(chestRemoveEvent);
        }
    }

}