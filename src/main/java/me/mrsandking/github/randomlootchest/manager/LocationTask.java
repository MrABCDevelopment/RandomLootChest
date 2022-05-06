package me.mrsandking.github.randomlootchest.manager;

import me.mrsandking.github.randomlootchest.RandomLootChestMain;
import me.mrsandking.github.randomlootchest.util.Settings;
import me.mrsandking.github.randomlootchest.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Map;

public class LocationTask {

    public LocationTask() {
        if(Settings.activeParticles) {
            BukkitScheduler scheduler = Bukkit.getScheduler();
            scheduler.runTaskTimerAsynchronously(RandomLootChestMain.getInstance(), new Runnable() {
                @Override
                public void run() {
                    for(Map.Entry<String, String> map : RandomLootChestMain.getInstance().getLocationManager().getLocations().entrySet()) {
                        Location location = Util.getStringLocation(map.getKey());
                        try {
                            location.getWorld().playEffect(location.add(0.5, 0.7, 0.5), Effect.valueOf(Settings.particleType), Settings.particleAmount);
                        } catch (Exception e) {}
                    }
                }
            }, 0L, 20L);
        }
    }

}