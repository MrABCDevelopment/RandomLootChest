package me.mrsandking.github.randomlootchest.commands.subcommands;

import me.mrsandking.github.randomlootchest.RandomLootChestMain;
import me.mrsandking.github.randomlootchest.commands.ArgumentCommand;
import me.mrsandking.github.randomlootchest.objects.WandItem;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WandSubCommand implements ArgumentCommand {

    @Override
    public boolean execute(CommandSender commandSender, String[] args) {
        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessages().get("not-player"));
            return false;
        }
        Player player = (Player) commandSender;
        player.getInventory().addItem(WandItem.WANDITEM);
        return true;
    }

    @Override
    public String getHelpText() {
        return ChatColor.GOLD+"/randomlootchest wand - gives magic RandomLootChest wand";
    }

    @Override
    public String getPermission() {
        return "randomlootchest.admin.wand";
    }
}