package me.dreamdevs.randomlootchest.commands.subcommands;

import me.dreamdevs.randomlootchest.RandomLootChestMain;
import me.dreamdevs.randomlootchest.api.Language;
import me.dreamdevs.randomlootchest.api.commands.ArgumentCommand;
import me.dreamdevs.randomlootchest.api.objects.IChestGame;
import me.dreamdevs.randomlootchest.objects.ChestGame;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CreateChestSubCommand implements ArgumentCommand {

	@Override
	public boolean execute(CommandSender commandSender, String[] args) {
		if (!(commandSender instanceof Player player)) {
			commandSender.sendMessage(Language.GENERAL_NOT_PLAYER.toString());
			return true;
		}

		if (args.length != 2) {
			player.sendMessage(Language.GENERAL_NO_ARGUMENTS.toString());
			return true;
		}

		if (args[1] == null) {
			player.sendMessage(Language.CHEST_NO_ID_MESSAGE.toString());
			return true;
		}

		Optional<IChestGame> optionalChestGame = Optional.ofNullable(RandomLootChestMain.getInstance().getChestsManager().getChestGameByRarity(args[1]));
		if (optionalChestGame.isPresent()) {
			player.sendMessage(Language.CHEST_EXISTS_MESSAGE.toString());
			return true;
		}

		ChestGame chestGame = new ChestGame(args[1]);
		chestGame.setupDefaultValues();
		player.sendMessage(Language.CHEST_CREATED_MESSAGE.toString());

		return true;
	}

	@Override
	public String getHelpText() {
		return "&6/randomlootchest create <id> - creates new chest with id";
	}

	@Override
	public String getPermission() {
		return "randomlootchest.admin.create";
	}

	@Override
	public boolean hasArguments() {
		return false;
	}

	@Override
	public List<String> getArguments() {
		return Collections.emptyList();
	}
}