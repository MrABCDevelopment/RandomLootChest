package me.dreamdevs.randomlootchest.commands.subcommands;

import me.dreamdevs.randomlootchest.api.Language;
import me.dreamdevs.randomlootchest.api.commands.ArgumentCommand;
import me.dreamdevs.randomlootchest.objects.WandItem;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class WandSubCommand implements ArgumentCommand {

    @Override
    public boolean execute(CommandSender commandSender, String[] args) {
        if(!(commandSender instanceof Player player)) {
            commandSender.sendMessage(Language.GENERAL_NO_PERMISSION.toString());
            return false;
        }
        player.getInventory().addItem(WandItem.WANDITEM);
        return true;
    }

    @Override
    public String getHelpText() {
        return "&6/randomlootchest wand - gives magic RandomLootChest wand";
    }

    @Override
    public String getPermission() {
        return "randomlootchest.admin.wand";
    }

    @Override
    public List<String> getArguments() {
        return Collections.emptyList();
    }
}