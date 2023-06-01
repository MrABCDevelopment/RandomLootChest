package me.dreamdevs.github.randomlootchest.commands.subcommands;

import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.api.commands.ArgumentCommand;
import me.dreamdevs.github.randomlootchest.api.menu.chests.ChestsMenu;
import me.dreamdevs.github.randomlootchest.utils.ColourUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ChestsSubCommand implements ArgumentCommand {

    @Override
    public boolean execute(CommandSender commandSender, String[] args) {
        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessages().get("not-player"));
            return false;
        }
        Player player = (Player) commandSender;
        if(RandomLootChestMain.getInstance().getChestsManager().getChests().isEmpty()) {
            player.sendMessage(ColourUtil.colorize("&cCouldn't find any chest."));
            return false;
        }
        new ChestsMenu(player);
        return true;
    }

    @Override
    public String getHelpText() {
        return "&6/randomlootchest chests - opens inventory with chests and their informations";
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