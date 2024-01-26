package me.dreamdevs.randomlootchest;

import me.dreamdevs.randomlootchest.api.RandomLootChestApi;
import me.dreamdevs.randomlootchest.api.inventory.handlers.ItemMenuListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class ApiHandler {

	public static JavaPlugin plugin;

	public static void registerApi(JavaPlugin hook) {
		plugin = hook;

		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Registering RandomLootChest API...");

		ItemMenuListener.getInstance().register(plugin);

		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Successfully registered RandomLootChest API!");
	}

}