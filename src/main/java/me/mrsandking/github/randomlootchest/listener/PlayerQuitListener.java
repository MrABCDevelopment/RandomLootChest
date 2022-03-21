package me.mrsandking.github.randomlootchest.listener;

import me.mrsandking.github.randomlootchest.GamePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;


public class PlayerQuitListener implements Listener {

    @EventHandler
    public void quitEvent(PlayerQuitEvent event) {
        GamePlayer gamePlayer = new GamePlayer(event.getPlayer());
    }

}