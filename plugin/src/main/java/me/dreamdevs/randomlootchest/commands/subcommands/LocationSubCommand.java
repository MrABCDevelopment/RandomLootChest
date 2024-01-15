package me.dreamdevs.randomlootchest.commands.subcommands;

import me.dreamdevs.randomlootchest.RandomLootChestMain;
import me.dreamdevs.randomlootchest.api.Language;
import me.dreamdevs.randomlootchest.api.commands.ArgumentCommand;
import me.dreamdevs.randomlootchest.api.utils.ColourUtil;
import me.dreamdevs.randomlootchest.api.utils.Util;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class LocationSubCommand implements ArgumentCommand {

	@Override
	public boolean execute(CommandSender commandSender, String[] args) {
		if (args.length != 2) {
			commandSender.sendMessage(Language.GENERAL_NO_ARGUMENTS.toString());
			return true;
		}
		if (!getArguments().contains(args[1])) {
			commandSender.sendMessage(Language.GENERAL_NO_ARGUMENT.toString());
			return true;
		}
		if (args[1].equalsIgnoreCase("clear")) {
			RandomLootChestMain.getInstance().getLocationManager().getLocations().clear();
			RandomLootChestMain.getInstance().getLocationManager().save();
			commandSender.sendMessage(ColourUtil.colorize("&aYou cleared all chest locations!"));
			return true;
		}
		if (args[1].equalsIgnoreCase("fix")) {
			RandomLootChestMain.getInstance().getLocationManager().getLocations().keySet().stream().map(Util::getStringLocation)
					.filter(Objects::nonNull)
					.filter(location -> location.getBlock().getType() != Material.AIR &&
										location.getBlock().getType() != Material.CHEST)
					.forEach(location -> RandomLootChestMain.getInstance().getLocationManager().removeLocation(location));
			commandSender.sendMessage(ColourUtil.colorize("&aAll non-chest locations were removed from locations!"));
			return true;
		}
		return true;
	}

	@Override
	public String getHelpText() {
		return "&6/randomlootchest locations [clear/fix] - reloads all configurations";
	}

	@Override
	public String getPermission() {
		return "randomlootchest.admin.locations";
	}

	@Override
	public boolean hasArguments() {
		return true;
	}

	@Override
	public List<String> getArguments() {
		return Arrays.asList("clear", "fix");
	}
}