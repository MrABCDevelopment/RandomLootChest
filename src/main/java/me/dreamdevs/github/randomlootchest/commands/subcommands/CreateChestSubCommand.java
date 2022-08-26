package me.dreamdevs.github.randomlootchest.commands.subcommands;

import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.commands.ArgumentCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateChestSubCommand implements ArgumentCommand {

    @Override
    public boolean execute(CommandSender commandSender, String[] args) {
        String id = args[1];
        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessages().get("not-player"));
            return false;
        }
        if(RandomLootChestMain.getInstance().getChestsManager().getChests().get(id) != null) {
            commandSender.sendMessage(ChatColor.RED+"This chest exists! Type other id!");
            return false;
        }

        return true;
    }

    @Override
    public String getHelpText() {
        return ChatColor.GOLD+"/randomlootchest create <id> - creates new chest";
    }

    @Override
    public String getPermission() {
        return "randomlootchest.admin.create";
    }
}