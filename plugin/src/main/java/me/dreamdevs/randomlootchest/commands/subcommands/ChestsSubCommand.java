package me.dreamdevs.randomlootchest.commands.subcommands;

import me.dreamdevs.randomlootchest.RandomLootChestMain;
import me.dreamdevs.randomlootchest.api.Language;
import me.dreamdevs.randomlootchest.api.commands.ArgumentCommand;
import me.dreamdevs.randomlootchest.menus.ChestMenu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ChestsSubCommand implements ArgumentCommand {

    @Override
    public boolean execute(CommandSender commandSender, String[] args) {
        if(!(commandSender instanceof Player player)) {
            commandSender.sendMessage(Language.GENERAL_NOT_PLAYER.toString());
            return false;
        }
        if(RandomLootChestMain.getInstance().getChestsManager().getChests().isEmpty()) {
            player.sendMessage(Language.GENERAL_NO_CHEST.toString());
            return false;
        }

        new ChestMenu().open(player);
        return true;
    }

    @Override
    public String getHelpText() {
        return "&6/randomlootchest chests - opens inventory with chests and their information";
    }

    @Override
    public String getPermission() {
        return "randomlootchest.admin.chests";
    }

    @Override
    public List<String> getArguments() {
        return new ArrayList<>();
    }
}