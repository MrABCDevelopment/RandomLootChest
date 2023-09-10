package me.dreamdevs.randomlootchest.managers.tasks;

import me.dreamdevs.randomlootchest.api.objects.IChestGame;
import me.dreamdevs.randomlootchest.RandomLootChestMain;
import me.dreamdevs.randomlootchest.utils.Util;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.Objects;

public class LocationTask extends BukkitRunnable {

    public LocationTask() {
        runTaskTimerAsynchronously(RandomLootChestMain.getInstance(), 20L, 20L);
    }

    @Override
    public void run() {
        for(Map.Entry<String, String> map : RandomLootChestMain.getInstance().getLocationManager().getLocations().entrySet()) {
            IChestGame chestGame = RandomLootChestMain.getInstance().getChestsManager().getChestGameByRarity(map.getValue());
            if(chestGame == null) continue;
            Location location = Util.getStringLocation(map.getKey());
            if(chestGame.useParticles()) {
                Objects.requireNonNull(location.getWorld()).spawnParticle(chestGame.getParticle(), location.add(0.5, 0.7, 0.5), chestGame.getParticleAmount());
            }
        }
    }
}