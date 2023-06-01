package me.dreamdevs.github.randomlootchest.commands;

import lombok.Getter;
import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.api.commands.ArgumentCommand;
import me.dreamdevs.github.randomlootchest.commands.subcommands.*;
import me.dreamdevs.github.randomlootchest.utils.ColourUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.permissions.Permission;
import org.bukkit.util.StringUtil;

import java.util.*;

public class CommandHandler implements TabExecutor {

    private final @Getter HashMap<String, Class<? extends ArgumentCommand>> arguments;

    public CommandHandler(RandomLootChestMain plugin) {
        this.arguments = new HashMap<>();
        registerCommand("wand", WandSubCommand.class);
        registerCommand("save", SaveSubCommand.class);
        registerCommand("reload", ReloadSubCommand.class);
        registerCommand("chests", ChestsSubCommand.class);
        registerCommand("extensions", ExtensionsSubCommand.class);
        Objects.requireNonNull(plugin.getCommand("randomlootchest")).setExecutor(this);
        Objects.requireNonNull(plugin.getCommand("randomlootchest")).setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        try {
            if(strings.length >= 1) {
                if(arguments.containsKey(strings[0])) {
                    Class<? extends ArgumentCommand> argumentCommand = arguments.get(strings[0]).asSubclass(ArgumentCommand.class);
                    ArgumentCommand argument = argumentCommand.newInstance();
                    if(commandSender.hasPermission(argument.getPermission())) {
                        if(strings.length > 1 && argument.getArguments().isEmpty()) {
                            commandSender.sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessage("no-arguments"));
                            return true;
                        }
                        argument.execute(commandSender, strings);
                    } else {
                        commandSender.sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessage("no-permission"));
                    }
                    return true;
                } else {
                    commandSender.sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessage("no-argument"));
                    return true;
                }
            } else {
                commandSender.sendMessage(ColourUtil.colorize("&aHelp for RandomLootChest:"));
                for(Class<? extends ArgumentCommand> argumentCommand : arguments.values()) {
                    commandSender.sendMessage(ColourUtil.colorize(argumentCommand.newInstance().getHelpText()));
                }
                return true;
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
        } else if(strings.length == 2 && !getArgumentsForSubcommand(strings[0]).isEmpty()) {
            return getArgumentsForSubcommand(strings[0]);
        } else return Collections.emptyList();
    }

    private List<String> getArgumentsForSubcommand(String subcommand) {
        List<String> listArguments = null;
        try {
            listArguments = arguments.get(subcommand).newInstance().getArguments();
            Collections.sort(listArguments);
        } catch (Exception e) {

        }
        return listArguments;
    }

    public void registerCommand(String command, Class<? extends ArgumentCommand> clazz) {
        arguments.put(command, clazz);
        try {
            Bukkit.getPluginManager().addPermission(new Permission(clazz.newInstance().getPermission()));
        } catch (InstantiationException | IllegalAccessException e) {

        }
    }

}