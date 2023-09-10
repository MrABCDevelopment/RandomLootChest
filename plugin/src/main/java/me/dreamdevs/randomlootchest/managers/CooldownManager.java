package me.dreamdevs.randomlootchest.managers;

import lombok.Getter;
import me.dreamdevs.randomlootchest.api.database.IPlayerData;
import me.dreamdevs.randomlootchest.RandomLootChestMain;
import me.dreamdevs.randomlootchest.api.events.CooldownExpiredEvent;
import me.dreamdevs.randomlootchest.api.events.CooldownSetEvent;
import me.dreamdevs.randomlootchest.database.data.PlayerData;
import me.dreamdevs.randomlootchest.utils.Settings;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CooldownManager {

    private @Getter final List<PlayerData> players;
    private @Getter final Map<Location, AtomicInteger> locations;

    public CooldownManager() {
        players = new ArrayList<>();
        locations = new ConcurrentHashMap<>();
        onSecond();
    }

    public IPlayerData getPlayerData(OfflinePlayer player) {
        return players.stream().filter(playerData -> playerData.getPlayer().equals(player)).findFirst().orElse(null);
    }

    public int getCooldownByLocation(Player player, Location location) {
        return getPlayerData(player).getLeftCooldownSeconds(location);
    }

    public int getCooldownForAllByLocation(Location location) {
        return locations.getOrDefault(location, new AtomicInteger(0)).get();
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
            if (Settings.personalCooldown) {
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
            } else {
                if (locations.isEmpty()) {
                    // Nothing else will happen...
                    return;
                }

                locations.forEach((location, atomicInteger) -> {
                    int value = atomicInteger.decrementAndGet();
                    if (value <= 0) {
                        locations.remove(location);
                    }
                });
            }
        }, 0L, 20L);
    }

}