package me.dreamdevs.randomlootchest.api;

import me.dreamdevs.randomlootchest.api.database.IPlayerData;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public abstract class RandomLootChestApi {

	public static JavaPlugin INSTANCE;

	public static RandomLootChestApi API;

	/**
	 * This function registers api to RandomLootChest plugin.
	 */

	public abstract void register(JavaPlugin plugin);

	/**
	 * Gets player data by player's id.
	 */

	public abstract IPlayerData getPlayerData(UUID uuid);

	/**
	 *
	 */

}