package me.mrsandking.github.randomlootchest.manager;

import lombok.Getter;
import me.mrsandking.github.randomlootchest.RandomLootChestMain;
import me.mrsandking.github.randomlootchest.events.RLCCooldownSetEvent;
import me.mrsandking.github.randomlootchest.util.Settings;
import me.mrsandking.github.randomlootchest.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

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
        RLCCooldownSetEvent rlcCooldownSetEvent = new RLCCooldownSetEvent(uuid, location, seconds);
        HashMap<UUID, Location> map = new HashMap<>();
        map.put(uuid, location);
        chestCooldowns.put(map, new AtomicInteger(seconds));
        Bukkit.getPluginManager().callEvent(rlcCooldownSetEvent);
    }

    private void onSecond() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(RandomLootChestMain.getInstance(), new Runnable() {
            @Override
            public void run() {
                if (chestCooldowns.isEmpty()) return;
                for(Map.Entry<HashMap<UUID, Location>, AtomicInteger> map : chestCooldowns.entrySet()) {
                    for(Map.Entry<UUID, Location> entry : map.getKey().entrySet()) {
                        int value = chestCooldowns.get(map.getKey()).decrementAndGet();
                        if (value <= 0) {
                            Player player = Bukkit.getPlayer(entry.getKey());
                            if(Settings.filledChestInfo) {
                                if (player != null)
                                    player.sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessages().get("filled-chest-info").replace("{LOCATION}", Util.getLocationString(entry.getValue())));
                            }
                            chestCooldowns.remove(map.getKey());
                        }
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