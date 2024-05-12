package me.dreamdevs.randomlootchest.hooks;

import io.lumine.mythic.bukkit.MythicBukkit;
import me.dreamdevs.randomlootchest.api.util.Util;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.Callable;

public class MythicMobsHook {

	public static MythicMobsHook INSTANCE;
	private final MythicBukkit mythicBukkit;

	public MythicMobsHook() {
		INSTANCE = this;

		mythicBukkit = MythicBukkit.inst();
		Util.sendPluginMessage("&aHooked into MythicMobs!");
	}

	public Callable<ItemStack> getItemStack(String id) {
		return () -> mythicBukkit.getItemManager().getItemStack(id);
	}

}