package me.dreamdevs.randomlootchest.managers;

import com.google.common.base.Preconditions;
import lombok.Getter;
import me.dreamdevs.randomlootchest.RandomLootChestMain;
import me.dreamdevs.randomlootchest.api.Config;
import me.dreamdevs.randomlootchest.api.Language;
import me.dreamdevs.randomlootchest.api.event.chest.ChestSpawnEvent;
import me.dreamdevs.randomlootchest.api.object.IChestGame;
import me.dreamdevs.randomlootchest.api.util.Util;
import org.bukkit.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public final class RandomSpawnManager {

	private @Getter final List<Location> tempLocations = new LinkedList<>();

	public RandomSpawnManager(JavaPlugin plugin) {
		Bukkit.getScheduler().runTaskTimer(plugin, () -> {
			this.tempLocations.stream().filter(location -> location.getChunk().isLoaded()).forEach(location ->  {
				RandomLootChestMain.getInstance().getLocationManager().removeLocation(location);
				location.getBlock().setType(Material.AIR);
			});
			this.tempLocations.clear();

			for (String world : Config.RANDOM_SPAWN_CHESTS_WORLDS.toList()) {
				World bukkitWorld = Bukkit.getWorld(world);
				if (bukkitWorld == null) {
					Util.sendPluginMessage("&cWorld "+world+" does not exist!");
					continue;
				}

				spawnChest(bukkitWorld, Config.RANDOM_SPAWN_CHESTS_MIN_X.toInt(),
						Config.RANDOM_SPAWN_CHESTS_MAX_X.toInt(),
						Config.RANDOM_SPAWN_CHESTS_MIN_Z.toInt(),
						Config.RANDOM_SPAWN_CHESTS_MAX_Z.toInt());
			}
		}, 0L, 600L);
	}

	private void spawnChest(World world, int minX, int maxX, int minZ, int maxZ) {
		Location minLocation = new Location(world, minX, 0, minZ);
		Location maxLocation = new Location(world, maxX, 256, maxZ);

		Location randomLocation = getRandomLocation(minLocation, maxLocation);
		randomLocation.setY(0);

		do {
			randomLocation.setY(randomLocation.getBlockY()+1);
		} while (randomLocation.getBlock().getType() != Material.AIR);

		if (randomLocation.getChunk().isLoaded()) {
			randomLocation.getBlock().setType(Material.CHEST);
		}

		IChestGame chestGame = RandomLootChestMain.getInstance().getChestsManager().getRandomChest();
		RandomLootChestMain.getInstance().getLocationManager().addLocation(chestGame.getId(), randomLocation);

		Bukkit.broadcastMessage(Language.RANDOM_CHEST_SPAWN.toString()
				.replace("%TYPE%", chestGame.getTitle())
				.replace("%COORDINATES%", "world: "+world.getName()+", x: "+randomLocation.getBlockX()
						+", y: "+randomLocation.getBlockY()+", z: "+randomLocation.getBlockZ()));

		Bukkit.getPluginManager().callEvent(new ChestSpawnEvent(chestGame, randomLocation));
	}

	public Location getRandomLocation(Location loc1, Location loc2) {
		Preconditions.checkArgument(loc1.getWorld() == loc2.getWorld());
		int minX = Math.min(loc1.getBlockX(), loc2.getBlockX());
		int minY = Math.min(loc1.getBlockY(), loc2.getBlockY());
		int minZ = Math.min(loc1.getBlockZ(), loc2.getBlockZ());

		int maxX = Math.max(loc1.getBlockX(), loc2.getBlockX());
		int maxY = Math.max(loc1.getBlockY(), loc2.getBlockY());
		int maxZ = Math.max(loc1.getBlockZ(), loc2.getBlockZ());

		return new Location(loc1.getWorld(), randomInt(minX, maxX), randomInt(minY, maxY), randomInt(minZ, maxZ));
	}

	public double randomInt(int min, int max) {
		return min + ThreadLocalRandom.current().nextInt(Math.abs(max - min + 1));
	}

	public boolean isInChunk(Chunk chunk, Location location) {
		return location.getChunk() == chunk;
	}

}