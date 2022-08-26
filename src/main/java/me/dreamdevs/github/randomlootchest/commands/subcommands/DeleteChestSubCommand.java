package me.dreamdevs.github.randomlootchest.commands.subcommands;

import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.commands.ArgumentCommand;
import me.dreamdevs.github.randomlootchest.menus.ChestRemoveMenu;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DeleteChestSubCommand implements ArgumentCommand {

    @Override
    public boolean execute(CommandSender commandSender, String[] args) {
        String id = args[1];
        if(RandomLootChestMain.getInstance().getChestsManager().getChests().get(id) == null || args.length < 1) {
            if(!(commandSender instanceof Player)) {
                commandSender.sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessages().get("not-player"));
                return false;
            }
            new ChestRemoveMenu((Player) commandSender);
            return true;
        }
        RandomLootChestMain.getInstance().getChestsManager().getChests().get(id).delete();
        commandSender.sendMessage(ChatColor.GREEN+"You deleted "+id+" chest!");
        return true;
    }

    @Override
    public String getHelpText() {
        return ChatColor.GOLD+"/randomlootchest delete <id> - removes chest with id";
    }

    @Override
    public String getPermission() {
        return "randomlootchest.admin.delete";
    }

}
