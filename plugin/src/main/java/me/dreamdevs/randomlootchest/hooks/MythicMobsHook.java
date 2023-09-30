package me.dreamdevs.randomlootchest.hooks;

import io.lumine.mythic.bukkit.MythicBukkit;
import me.dreamdevs.randomlootchest.api.utils.Util;
import org.bukkit.inventory.ItemStack;

public class MythicMobsHook {

	public static MythicMobsHook INSTANCE;
	private final MythicBukkit mythicBukkit;

	public MythicMobsHook() {
		INSTANCE = this;

		mythicBukkit = MythicBukkit.inst();
		Util.sendPluginMessage("&aHooked into MythicMobs!");
	}

	public ItemStack getItemStack(String id) {
		return mythicBukkit.getItemManager().getItemStack(id);
	}

}