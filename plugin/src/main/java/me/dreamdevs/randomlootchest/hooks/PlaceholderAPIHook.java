package me.dreamdevs.randomlootchest.hooks;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.dreamdevs.randomlootchest.RandomLootChestMain;
import me.dreamdevs.randomlootchest.api.Config;
import me.dreamdevs.randomlootchest.api.utils.Util;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlaceholderAPIHook extends PlaceholderExpansion {

	public PlaceholderAPIHook() {
		Util.sendPluginMessage("&aHooked into PlaceholderAPI!");
	}

	@Override
	public @NotNull String getIdentifier() {
		return "randomlootchest";
	}

	@Override
	public @NotNull String getAuthor() {
		return "DreamDevs";
	}

	@Override
	public @NotNull String getVersion() {
		return RandomLootChestMain.getInstance().getDescription().getVersion();
	}

	@Override
	public boolean register() {
		return true;
	}

	@Override
	public boolean persist() {
		return true;
	}

	@Override
	public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
		if (params.equalsIgnoreCase("has_any_cooldown")) {
			return (RandomLootChestMain.getInstance().getCooldownManager().getPlayerData(player).getCooldown().isEmpty())
					? ChatColor.GREEN+"Yes" : ChatColor.RED+"No";
		}
		if (params.equalsIgnoreCase("is_in_combat")) {
			return (Config.USE_COMBAT_BLOCKER.toBoolean() && RandomLootChestMain.getInstance().getCombatManager().isInCombat(player.getPlayer()))
					? ChatColor.GREEN+"Yes" : ChatColor.RED+"No";
		}
		if (params.equalsIgnoreCase("total_chests")) {
			return String.valueOf(RandomLootChestMain.getInstance().getChestsManager().getChests().size());
		}
		return super.onRequest(player, params);
	}
}