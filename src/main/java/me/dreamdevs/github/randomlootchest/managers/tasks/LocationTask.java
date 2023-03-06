package me.dreamdevs.github.randomlootchest.managers.tasks;

import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.api.objects.ChestGame;
import me.dreamdevs.github.randomlootchest.utils.Util;
import me.dreamdevs.github.randomlootchest.utils.VersionUtil;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

public class LocationTask extends BukkitRunnable {

    public LocationTask() {
        runTaskTimerAsynchronously(RandomLootChestMain.getInstance(), 20L, 20L);
    }

    @Override
    public void run() {
        for(Map.Entry<String, String> map : RandomLootChestMain.getInstance().getLocationManager().getLocations().entrySet()) {
            ChestGame chestGame = RandomLootChestMain.getInstance().getChestsManager().getChestGameByRarity(map.getValue());
            Location location = Util.getStringLocation(map.getKey());
            if(chestGame.isParticleUse()) {
                if (VersionUtil.is1_8_orOlder()) {
                    if(Effect.valueOf(chestGame.getParticleType().toUpperCase()) != null)
                        location.getWorld().playEffect(location.add(0.5, 0.7, 0.5), Effect.valueOf(chestGame.getParticleType().toUpperCase()), chestGame.getParticleAmount());
                } else {
                    if(Particle.valueOf(chestGame.getParticleType().toUpperCase()) != null)
                        location.getWorld().spawnParticle(Particle.valueOf(chestGame.getParticleType().toUpperCase()), location.add(0.5, 0.7, 0.5), chestGame.getParticleAmount());
                }
            }
        }
    }
}