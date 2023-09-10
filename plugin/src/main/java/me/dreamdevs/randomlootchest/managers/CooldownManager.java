package me.dreamdevs.randomlootchest.managers;

import lombok.Getter;
import me.dreamdevs.randomlootchest.api.database.IPlayerData;
import me.dreamdevs.randomlootchest.RandomLootChestMain;
import me.dreamdevs.randomlootchest.api.events.CooldownExpiredEvent;
import me.dreamdevs.randomlootchest.api.events.CooldownSetEvent;
import me.dreamdevs.randomlootchest.database.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.*;

public class CooldownManager {

    private @Getter final List<PlayerData> players;

    public CooldownManager() {
        players = new ArrayList<>();
        onSecond();
    }

    public IPlayerData getPlayerData(OfflinePlayer player) {
        return players.stream().filter(playerData -> playerData.getPlayer().equals(player)).findFirst().orElse(null);
    }

    public int getCooldownByLocation(Player player, Location location) {
        return getPlayerData(player).getLeftCooldownSeconds(location);
    }

    public void setCooldown(OfflinePlayer player, Location location, int seconds, boolean fireEvent) {
        IPlayerData playerData = getPlayerData(player);
        if (playerData == null) {
            playerData = new PlayerData(player);
            players.add((PlayerData) playerData);
        }
        playerData.applyCooldown(location, seconds);
        if (fireEvent) {
            CooldownSetEvent rlcCooldownSetEvent = new CooldownSetEvent(player.getUniqueId(), location, seconds);
            Bukkit.getPluginManager().callEvent(rlcCooldownSetEvent);
        }
    }

    private void onSecond() {
        Bukkit.getScheduler().runTaskTimer(RandomLootChestMain.getInstance(), () -> {
            if (players.isEmpty()) {
                // Nothing else will happen...
                return;
            }
            for (PlayerData playerData : getPlayers()) {
                for (Location location : playerData.getCooldown().keySet()) {
                    int value = playerData.getCooldown().get(location).decrementAndGet();
                    if (value <= 0) {
                        playerData.getCooldown().remove(location);
                        CooldownExpiredEvent cooldownExpiredEvent = new CooldownExpiredEvent(playerData.getPlayer().getUniqueId(), location);
                        Bukkit.getPluginManager().callEvent(cooldownExpiredEvent);
                    }
                }
            }
        }, 0L, 20L);
    }

}