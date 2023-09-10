package me.dreamdevs.randomlootchest.commands.subcommands;

import me.dreamdevs.randomlootchest.RandomLootChestMain;
import me.dreamdevs.randomlootchest.api.commands.ArgumentCommand;
import me.dreamdevs.randomlootchest.utils.ColourUtil;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class LocationsSubCommand implements ArgumentCommand {

    @Override
    public boolean execute(CommandSender commandSender, String[] args) {
        if (args.length == 1) {
            commandSender.sendMessage(ColourUtil.colorize("&cTry /rlc locations <clear/count>"));
            return true;
        } else if (args.length == 2) {
            if(args[1].equalsIgnoreCase("count")) {
                commandSender.sendMessage(ColourUtil.colorize("&7There are "+ RandomLootChestMain.getInstance().getLocationManager().getLocations().size()+" locations!"));
                return true;
            } else if(args[1].equalsIgnoreCase("clear")) {
                RandomLootChestMain.getInstance().getLocationManager().getLocations().clear();
                RandomLootChestMain.getInstance().getLocationManager().save();
                commandSender.sendMessage(ColourUtil.colorize("&7Removed all locations!"));
                return true;
            } else if(args[1].equalsIgnoreCase("save")) {
                RandomLootChestMain.getInstance().getLocationManager().save();
                commandSender.sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessages().get("locations-saved"));
                return true;
            } else {
                commandSender.sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessage("no-argument"));
                return true;
            }
        } else {
            commandSender.sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessage("no-argument"));
            return true;
        }
    }

    @Override
    public String getHelpText() {
        return "&6/randomlootchest locations [save, clear/count] - save, clear or count chests locations";
    }

    @Override
    public String getPermission() {
        return "randomlootchest.admin.locations";
    }

    @Override
    public List<String> getArguments() {
        List<String> list = new ArrayList<>();
        list.add("clear");
        list.add("count");
        return list;
    }
}