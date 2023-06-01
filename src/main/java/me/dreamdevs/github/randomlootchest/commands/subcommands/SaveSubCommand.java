package me.dreamdevs.github.randomlootchest.commands.subcommands;

import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.api.commands.ArgumentCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SaveSubCommand implements ArgumentCommand {
    @Override
    public boolean execute(CommandSender commandSender, String[] args) {
        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessages().get("not-player"));
            return false;
        }
        Player player = (Player) commandSender;
        RandomLootChestMain.getInstance().getLocationManager().save();
        player.sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessages().get("locations-saved"));
        return true;
    }

    @Override
    public String getHelpText() {
        return "&6/randomlootchest save - saves all locations";
    }

    @Override
    public String getPermission() {
        return "randomlootchest.admin.save";
    }

    @Override
    public List<String> getArguments() {
        return new ArrayList<>();
    }
}
