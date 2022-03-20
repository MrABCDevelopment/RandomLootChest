package me.mrsandking.github.randomlootchest.manager;

import lombok.Getter;
import me.mrsandking.github.randomlootchest.GamePlayer;
import me.mrsandking.github.randomlootchest.RandomLootChestMain;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.HashMap;
import java.util.UUID;

public class PlayerManager {

    private @Getter HashMap<UUID, Player> gamePlayers;

    public PlayerManager() {
        gamePlayers = new HashMap<>();
    }

    public HashMap<UUID, Player> getGamePlayers() {
        return gamePlayers;
    }

    public void saveTask() {
        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.runTaskTimer(RandomLootChestMain.getInstance(), new Runnable() {
            @Override
            public void run() {
                Thread thread = new Thread(() -> {
                    gamePlayers.forEach((uuid, player) -> {
                        GamePlayer gamePlayer = new GamePlayer(player);
                        //YamlConfiguration config = YamlConfiguration.loadConfiguration(gamePlayer.getDataFile());
                        //config.set("cooldowns."+RandomLootChestMain.getInstance().getCooldownManager().getChestCooldowns());
                    });
                    //for(GamePlayer gamePlayer : gamePlayers) {
                    //    YamlConfiguration config = YamlConfiguration.loadConfiguration(gamePlayer.getDataFile());
                    //}
                });
                thread.start();
            }
        }, 0L, 20*300L);
    }
}