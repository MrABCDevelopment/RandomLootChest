package me.dreamdevs.randomlootchest.managers.tasks;

import me.dreamdevs.randomlootchest.RandomLootChestMain;
import me.dreamdevs.randomlootchest.api.object.IChestGame;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class LocationTask extends BukkitRunnable {

    public LocationTask() {
        runTaskTimerAsynchronously(RandomLootChestMain.getInstance(), 20L, 20L);
    }

    @Override
    public void run() {
        if (RandomLootChestMain.getInstance().getLocationManager().getLocations().isEmpty()) return;
        RandomLootChestMain.getInstance().getLocationManager().getChestsLocations()
                .forEach(location -> {
            IChestGame chestGame = RandomLootChestMain.getInstance().getChestsManager().getChestGameByLocation(location);

            if (chestGame.useParticles() && location.getBlock().getType() == Material.CHEST && location.getChunk().isLoaded()) {
                location.getWorld().spawnParticle(chestGame.getParticle(), location.add(0.5, 0.7, 0.5), chestGame.getParticleAmount());
            }
        });
    }
}