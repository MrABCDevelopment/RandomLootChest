package me.mrsandking.github.randomlootchest.manager;

import lombok.Getter;
import me.mrsandking.github.randomlootchest.RandomLootChestMain;
import me.mrsandking.github.randomlootchest.util.Settings;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CooldownManager
{

    private @Getter
    ConcurrentHashMap<HashMap<UUID, Location>, Long> chestCooldowns;

    public CooldownManager() {
        chestCooldowns = new ConcurrentHashMap<>();
        restoreCooldowns();
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

    public void restoreCooldowns() {
        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.runTaskTimerAsynchronously(RandomLootChestMain.getInstance(), new Runnable() {
            @Override
            public void run() {
                if(getChestCooldowns().isEmpty()) return;
                for(HashMap<UUID, Location> map : getChestCooldowns().keySet()) {
                    if(getChestCooldowns().get(map) > System.currentTimeMillis()) {
                        continue;
                    }
                    getChestCooldowns().remove(map);
                }
            }
        }, 0L, Settings.restoreCooldowns*20L);
    }

}