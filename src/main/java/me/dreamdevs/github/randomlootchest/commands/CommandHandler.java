package me.dreamdevs.github.randomlootchest.commands;

import lombok.Getter;
import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.commands.subcommands.*;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CommandHandler implements TabExecutor {

    private RandomLootChestMain plugin;
    private @Getter HashMap<String, Class<? extends ArgumentCommand>> arguments;

    public CommandHandler(RandomLootChestMain plugin) {
        this.plugin = plugin;
        this.arguments = new HashMap<>();
        arguments.put("wand", WandSubCommand.class);
        arguments.put("save", SaveSubCommand.class);
        arguments.put("reload", ReloadSubCommand.class);
        arguments.put("chests", ChestsSubCommand.class);
        arguments.put("delete", DeleteChestSubCommand.class);
        arguments.put("create", CreateChestSubCommand.class);
        plugin.getCommand("randomlootchest").setExecutor(this);
        plugin.getCommand("randomlootchest").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        try {
            if(strings.length == 0) {
                commandSender.sendMessage(ChatColor.GREEN+"Help for RandomLootChest:");
                for(Class<? extends ArgumentCommand> argumentCommand : arguments.values()) {
                    commandSender.sendMessage(argumentCommand.newInstance().getHelpText());
                }
                return true;
            } else if(strings.length >= 1) {
                if(arguments.containsKey(strings[0])) {
                    Class<? extends ArgumentCommand> argumentCommand = arguments.get(strings[0]).asSubclass(ArgumentCommand.class);
                    ArgumentCommand argument = argumentCommand.newInstance();
                    if(commandSender.hasPermission(argument.getPermission())) {
                        argument.execute(commandSender, strings);
                    } else {
                        commandSender.sendMessage(ChatColor.RED+"You don't have permission to do this!");
                    }
                    return true;
                } else {
                    commandSender.sendMessage(ChatColor.RED+"Argument doesn't exist!");
                    return true;
                }
            }
        } catch (Exception e) {

        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> completions = new ArrayList<>();
        if(strings.length == 1) {
            StringUtil.copyPartialMatches(strings[0], arguments.keySet(), completions);
            Collections.sort(completions);
            return completions;
        } else return null;
    }
}