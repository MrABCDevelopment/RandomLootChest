package me.dreamdevs.randomlootchest.database.data;

import lombok.Getter;
import me.dreamdevs.randomlootchest.api.database.IPlayerData;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class PlayerData implements IPlayerData {

    private final OfflinePlayer player;
    private final Map<Location, AtomicInteger> cooldown;

    public PlayerData(OfflinePlayer player) {
        this.player = player;
        this.cooldown = new ConcurrentHashMap<>();
    }

    @Override
    public void applyCooldown(Location location, int seconds) {
        cooldown.putIfAbsent(location, new AtomicInteger(seconds));
    }

    @Override
    public boolean hasCooldown(Location location) {
        return cooldown.containsKey(location) && cooldown.get(location).get() > 0;
    }

    @Override
    public int getLeftCooldownSeconds(Location location) {
        return hasCooldown(location) ? getCooldown().get(location).get() : 0;
    }

}