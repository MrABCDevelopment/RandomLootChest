package me.dreamdevs.github.randomlootchest.listeners;

import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final RandomLootChestMain plugin;

    public PlayerJoinListener(RandomLootChestMain plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void joinEvent(PlayerJoinEvent event) {
        if(!event.getPlayer().hasPlayedBefore()) {
            plugin.getStarterManager().loadItems(event.getPlayer());
        }
    }

}