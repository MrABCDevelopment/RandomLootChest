package me.mrsandking.github.randomlootchest.listener;

import me.mrsandking.github.randomlootchest.RandomLootChestMain;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerDeathListener implements Listener {

    private RandomLootChestMain plugin;

    public PlayerDeathListener(RandomLootChestMain plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void deathEvent(PlayerDeathEvent event) {
        event.getDrops().removeIf(itemStack -> plugin.getStarterManager().isStarterItem(itemStack));
    }

    @EventHandler
    public void respawnEvent(PlayerRespawnEvent event) {
        plugin.getStarterManager().load(event.getPlayer());
    }

}