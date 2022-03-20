package me.mrsandking.github.randomlootchest.listener;

import me.mrsandking.github.randomlootchest.RandomLootChestMain;
import me.mrsandking.github.randomlootchest.WandItem;
import me.mrsandking.github.randomlootchest.util.Util;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.UUID;

public class PlayerInteractListener implements Listener {

    private RandomLootChestMain plugin;

    public PlayerInteractListener(RandomLootChestMain plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteractChest(PlayerInteractEvent event) {
        if(event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if(!plugin.getLocationManager().getLocations().contains(Util.getLocationString(event.getClickedBlock().getLocation()))) return;
        if(event.getClickedBlock().getType() == Material.CHEST) {
            event.setCancelled(true);
            HashMap<UUID, Location> loc = new HashMap<>();

            loc.put(event.getPlayer().getUniqueId(), event.getClickedBlock().getLocation());

            if(plugin.getCooldownManager().getChestCooldowns().containsKey(loc)) {
                if(plugin.getCooldownManager().getChestCooldowns().get(loc) > System.currentTimeMillis()) {
                    event.getPlayer().sendMessage(plugin.getMessagesManager().getMessages().get("gamechest-cooldown").replace("[time]", Util.getTime(plugin.getCooldownManager().getTime(event.getPlayer().getUniqueId(), event.getClickedBlock().getLocation()))));
                    return;
                }
            }

            plugin.getGameManager().openChest(event.getPlayer());
            plugin.getCooldownManager().setCooldown(loc, plugin.getConfigManager().getConfig("config.yml").getInt("cooldown"));
        }
    }

    @EventHandler
    public void addBlockEvent(BlockBreakEvent event) {
        if(event.getPlayer().getItemInHand() == null) return;
        if(event.getBlock().getType() != Material.CHEST) return;
        if(!event.getPlayer().getItemInHand().equals(WandItem.WANDITEM)) return;
        if(plugin.getLocationManager().getLocations().contains(Util.getLocationString(event.getBlock().getLocation())))
            event.setCancelled(true);
        if(event.getBlock().getType() == Material.CHEST) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(plugin.getMessagesManager().getMessages().get("add-chest"));
            plugin.getLocationManager().addLocation(event.getBlock().getLocation());
        }
    }

    @EventHandler
    public void removeBlockEvent(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getPlayer().getItemInHand() == null) return;
        if (event.getClickedBlock().getType() != Material.CHEST) return;
        if (!event.getPlayer().getItemInHand().equals(WandItem.WANDITEM)) return;
        if (plugin.getLocationManager().getLocations().contains(Util.getLocationString(event.getClickedBlock().getLocation()))) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(plugin.getMessagesManager().getMessages().get("remove-chest"));
            plugin.getLocationManager().removeLocation(event.getClickedBlock().getLocation());
        }
    }

}