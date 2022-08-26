package me.dreamdevs.github.randomlootchest.managers;

import lombok.Getter;
import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.api.events.CooldownExpiredEvent;
import me.dreamdevs.github.randomlootchest.api.events.CooldownSetEvent;
import me.dreamdevs.github.randomlootchest.utils.ReflectionUtils;
import me.dreamdevs.github.randomlootchest.utils.Settings;
import me.dreamdevs.github.randomlootchest.utils.Util;
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
        CooldownSetEvent rlcCooldownSetEvent = new CooldownSetEvent(uuid, location, seconds);
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
                            Location location = entry.getValue();
                            if(Settings.filledChestsInfo.get("chat"))
                                player.sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessages().get("filled-chest-info").replace("{LOCATION}", Util.getLocationString(location)));
                            if(Settings.filledChestsInfo.get("actionbar"))
                                ReflectionUtils.sendActionBar(player, RandomLootChestMain.getInstance().getMessagesManager().getMessages().get("filled-chest-info").replace("{LOCATION}", Util.getLocationString(location)));
                            //if(Settings.filledChestsInfo.get("title"))//
                            //if(Settings.filledChestInfo) {
                            //    if (player != null)
                            //        player.sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessages().get("filled-chest-info").replace("{LOCATION}", Util.getLocationString(location)));
                            //}
                            chestCooldowns.remove(map.getKey());
                            CooldownExpiredEvent cooldownExpiredEvent = new CooldownExpiredEvent(player.getUniqueId(), location);
                            Bukkit.getPluginManager().callEvent(cooldownExpiredEvent);
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