package me.mrsandking.github.randomlootchest.manager;

import lombok.Getter;
import me.mrsandking.github.randomlootchest.RandomLootChestMain;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CooldownManager
{

    private @Getter ConcurrentHashMap<HashMap<UUID, Location>, AtomicInteger> chestCooldowns;

    public CooldownManager() {
        chestCooldowns = new ConcurrentHashMap<>();
        onSecond();
    }

    public void setCooldown(UUID uuid, Location location, int seconds) {
        HashMap<UUID, Location> map = new HashMap<>();
        map.put(uuid, location);
        chestCooldowns.put(map, new AtomicInteger(seconds));
    }

    private void onSecond() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(RandomLootChestMain.getInstance(), new Runnable() {
            @Override
            public void run() {
                if (chestCooldowns.isEmpty()) return;
                for(HashMap<UUID, Location> map : chestCooldowns.keySet()) {
                    int value = chestCooldowns.get(map).decrementAndGet();
                    if(value<=0) {
                        chestCooldowns.remove(map);
                    }
                }
            }
        }, 0L, 20L);
    }

    public int getPlayerCooldown(UUID uuid, Location location) {
        HashMap<UUID, Location> map = new HashMap<>();
        map.put(uuid, location);
        if(chestCooldowns.containsKey(map))
            return chestCooldowns.get(map).get();
        return 0;
    }

    public boolean isOnCooldown(UUID uuid, Location location) {
        return getPlayerCooldown(uuid, location) > 0;
    }

    public HashMap<HashMap<UUID, Location>, AtomicInteger> getPlayerCooldowns(UUID uuid) {
        HashMap<HashMap<UUID, Location>, AtomicInteger> hashMap = new HashMap<>();
        for(HashMap<UUID, Location> map : getChestCooldowns().keySet()) {
            if(map.containsKey(uuid)) {
                hashMap.put(map, getChestCooldowns().get(map));
            }
        }
        return hashMap;
    }

}