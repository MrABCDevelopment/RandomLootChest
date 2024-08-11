package me.dreamdevs.randomlootchest.commands.subcommands;

import me.dreamdevs.randomlootchest.api.Language;
import me.dreamdevs.randomlootchest.api.command.ArgumentCommand;
import me.dreamdevs.randomlootchest.objects.WandItem;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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

}