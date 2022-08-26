package me.dreamdevs.github.randomlootchest.listeners;

import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerDeathListener implements Listener {

    private final RandomLootChestMain plugin;

    public PlayerDeathListener(RandomLootChestMain plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void deathEvent(PlayerDeathEvent event) {
        event.getDrops().removeIf(itemStack -> plugin.getStarterManager().isStarterItem(itemStack));
    }

    @EventHandler
    public void respawnEvent(PlayerRespawnEvent event) {
        plugin.getStarterManager().loadItems(event.getPlayer());
    }

}