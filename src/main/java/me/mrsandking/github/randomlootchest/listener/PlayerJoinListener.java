package me.mrsandking.github.randomlootchest.listener;

import me.mrsandking.github.randomlootchest.GamePlayer;
import me.mrsandking.github.randomlootchest.RandomLootChestMain;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private RandomLootChestMain plugin;

    public PlayerJoinListener(RandomLootChestMain plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void joinEvent(PlayerJoinEvent event) {
        GamePlayer gamePlayer = new GamePlayer(event.getPlayer());
        if(!gamePlayer.getDataFile().exists()) {
            gamePlayer.createFile();
            plugin.getStarterManager().loadItems(event.getPlayer());
        }
    }

}