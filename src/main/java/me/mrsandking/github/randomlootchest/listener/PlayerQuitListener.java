package me.mrsandking.github.randomlootchest.listener;

import me.mrsandking.github.randomlootchest.GamePlayer;
import me.mrsandking.github.randomlootchest.RandomLootChestMain;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void quitEvent(PlayerQuitEvent event) {
        GamePlayer gamePlayer = new GamePlayer(event.getPlayer());
       // YamlConfiguration configuration = YamlConfiguration.loadConfiguration(gamePlayer.getDataFile());
        //List<String> list = new ArrayList<>();
       // configuration.set("cooldown", RandomLootChestMain.getInstance().getCooldownManager().getPlayerCooldown(gamePlayer.getPlayer()));
       // try {
       //     configuration.save(gamePlayer.getDataFile());
       // } catch (IOException e) {
      //      e.printStackTrace();
       // }
        //configuration.set("cooldown."+ RandomLootChestMain.getInstance().getCooldownManager().getPlayerCooldown(gamePlayer.getPlayer()));
    }

}