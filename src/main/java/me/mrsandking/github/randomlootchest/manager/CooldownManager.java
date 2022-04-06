package me.mrsandking.github.randomlootchest.manager;

import lombok.Getter;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CooldownManager
{

    private @Getter
    ConcurrentHashMap<HashMap<UUID, Location>, Long> chestCooldowns;

    public CooldownManager() {
        chestCooldowns = new ConcurrentHashMap<>();
    }

    public void setCooldown(HashMap<UUID, Location> hashMap, int seconds) {
        chestCooldowns.put(hashMap, System.currentTimeMillis() + (seconds * 1000));
    }

    public long getTime(UUID uuid, Location location) {
        HashMap<UUID, Location> map = new HashMap<>();
        map.put(uuid, location);
        if(chestCooldowns.containsKey(map)) {
            return (chestCooldowns.get(map) - System.currentTimeMillis()) / 1000;
        }
        return 0L;
    }

}