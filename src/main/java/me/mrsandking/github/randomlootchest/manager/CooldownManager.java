package me.mrsandking.github.randomlootchest.manager;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class CooldownManager
{

    private HashMap<HashMap<UUID, Location>, Long> chestCooldowns;

    public CooldownManager() {
        chestCooldowns = new HashMap<>();
    }

    public void setCooldown(HashMap<UUID, Location> hashMap, int seconds) {
        chestCooldowns.put(hashMap, System.currentTimeMillis() + (seconds * 1000));
    }

    public HashMap<HashMap<UUID, Location>, Long> getChestCooldowns() {
        return chestCooldowns;
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