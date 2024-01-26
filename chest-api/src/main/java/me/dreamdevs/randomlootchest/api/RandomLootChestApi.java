package me.dreamdevs.randomlootchest.api;

import org.bukkit.plugin.java.JavaPlugin;

public interface RandomLootChestApi {

	RandomLootChestApi api = null;

	static RandomLootChestApi getApi() {
		return api;
	}

	JavaPlugin getPlugin();

}