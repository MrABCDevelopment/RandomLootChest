package me.mrsandking.github.randomlootchest.commands.subcommands;

import me.mrsandking.github.randomlootchest.RandomLootChestMain;
import me.mrsandking.github.randomlootchest.commands.ArgumentCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SaveSubCommand implements ArgumentCommand {
    @Override
    public boolean execute(CommandSender commandSender, String[] args) {
        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessages().get("not-player"));
            return false;
        }
        Player player = (Player) commandSender;
        RandomLootChestMain.getInstance().getLocationManager().save();
        player.sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessages().get("location-save"));
        return true;
    }

    @Override
    public String getHelpText() {
        return ChatColor.GOLD+ "/randomlootchest save - saves all locations";
    }

    @Override
    public String getPermission() {
        return "randomlootchest.admin.save";
    }
}
