package me.dreamdevs.randomlootchest;

import me.dreamdevs.randomlootchest.api.database.IPlayerData;
import me.dreamdevs.randomlootchest.api.inventory.handlers.ItemMenuListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public final class RandomLootChestApi extends me.dreamdevs.randomlootchest.api.RandomLootChestApi {

	public RandomLootChestApi(JavaPlugin plugin) {
		this.register(plugin);
	}

	@Override
	public void register(JavaPlugin javaPlugin) {
		INSTANCE = javaPlugin;
		API = this;

		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Registering RandomLootChest API...");

		ItemMenuListener.getInstance().register(INSTANCE);

		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Successfully registered RandomLootChest API!");
	}

	@Override
	public IPlayerData getPlayerData(UUID uuid) {
		return RandomLootChestMain.getInstance().getCooldownManager().getPlayerData(uuid);
	}
}
