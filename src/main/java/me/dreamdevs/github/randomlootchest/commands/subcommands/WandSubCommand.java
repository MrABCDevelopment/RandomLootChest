package me.dreamdevs.github.randomlootchest.commands.subcommands;

import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.api.commands.ArgumentCommand;
import me.dreamdevs.github.randomlootchest.api.objects.WandItem;
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
        return "&6/randomlootchest wand - gives magic RandomLootChest wand";
    }

    @Override
    public String getPermission() {
        return "randomlootchest.admin.wand";
    }
}