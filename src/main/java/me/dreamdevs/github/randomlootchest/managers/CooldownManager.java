package me.dreamdevs.github.randomlootchest.managers;

import lombok.Getter;
import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.api.events.CooldownExpiredEvent;
import me.dreamdevs.github.randomlootchest.api.events.CooldownSetEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class CooldownManager
{

    private final Map<HashMap<UUID, Location>, AtomicInteger> chestCooldowns;

    public CooldownManager() {
        chestCooldowns = new ConcurrentHashMap<>();
        onSecond();
    }

    public void setCooldown(UUID uuid, Location location, int seconds, boolean fireEvent) {
        HashMap<UUID, Location> map = new HashMap<>();
        map.put(uuid, location);
        chestCooldowns.put(map, new AtomicInteger(seconds));
        if(fireEvent) {
            CooldownSetEvent rlcCooldownSetEvent = new CooldownSetEvent(uuid, location, seconds);
            Bukkit.getPluginManager().callEvent(rlcCooldownSetEvent);
        }
    }

    private void onSecond() {
        Bukkit.getScheduler().runTaskTimer(RandomLootChestMain.getInstance(), () -> {
            if (chestCooldowns.isEmpty()) return;
            for(Map.Entry<HashMap<UUID, Location>, AtomicInteger> map : chestCooldowns.entrySet()) {
                for(Map.Entry<UUID, Location> entry : map.getKey().entrySet()) {
                    int value = chestCooldowns.get(map.getKey()).decrementAndGet();
                    if (value <= 0) {
                        OfflinePlayer player = Bukkit.getOfflinePlayer(entry.getKey());
                        Location location = entry.getValue();
                        chestCooldowns.remove(map.getKey());
                        CooldownExpiredEvent cooldownExpiredEvent = new CooldownExpiredEvent(player.getUniqueId(), location);
                        Bukkit.getPluginManager().callEvent(cooldownExpiredEvent);
                    }
                }
            }
        }, 0L, 20L);

    }

    public int getPlayerCooldown(UUID uuid, Location location) {
        return chestCooldowns.keySet().stream().filter(entry -> entry.containsKey(uuid) && entry.containsValue(location)).map(entry -> chestCooldowns.get(entry).get()).findAny().orElse(0);
    }

    public boolean isOnCooldown(UUID uuid, Location location) {
        return getPlayerCooldown(uuid, location) > 0;
    }

    public Map<HashMap<UUID, Location>, AtomicInteger> getPlayerCooldowns(UUID uuid) {
        HashMap<HashMap<UUID, Location>, AtomicInteger> hashMap = new HashMap<>();
        getChestCooldowns().keySet().stream().filter(uuidLocationHashMap -> uuidLocationHashMap.containsKey(uuid)).forEachOrdered(uuidLocationHashMap -> hashMap.put(uuidLocationHashMap, getChestCooldowns().get(uuidLocationHashMap)));
        return hashMap;
    }

}