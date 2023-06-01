package me.dreamdevs.github.randomlootchest.commands.subcommands;

import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.api.commands.ArgumentCommand;
import me.dreamdevs.github.randomlootchest.api.menu.extensions.ExtensionsMenu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ExtensionsSubCommand implements ArgumentCommand {

    @Override
    public boolean execute(CommandSender commandSender, String[] args) {
        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessages().get("not-player"));
            return false;
        }
        Player player = (Player) commandSender;
        new ExtensionsMenu(player);
        return true;
    }

    @Override
    public String getHelpText() {
        return "&6/randomlootchest extensions - opens extensions menu";
    }

    @Override
    public String getPermission() {
        return "randomlootchest.admin.extensions";
    }

    @Override
    public List<String> getArguments() {
        return new ArrayList<>();
    }
}