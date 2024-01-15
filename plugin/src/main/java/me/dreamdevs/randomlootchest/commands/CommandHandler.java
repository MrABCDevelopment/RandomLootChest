package me.dreamdevs.randomlootchest.commands;

import lombok.Getter;
import me.dreamdevs.randomlootchest.RandomLootChestMain;
import me.dreamdevs.randomlootchest.api.Language;
import me.dreamdevs.randomlootchest.api.commands.ArgumentCommand;
import me.dreamdevs.randomlootchest.api.utils.Util;
import me.dreamdevs.randomlootchest.commands.subcommands.*;
import me.dreamdevs.randomlootchest.api.utils.ColourUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.permissions.Permission;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class CommandHandler implements TabExecutor {

    private final @Getter Map<String, ArgumentCommand> arguments;

    public CommandHandler(@NotNull RandomLootChestMain plugin) {
        this.arguments = new LinkedHashMap<>();
        registerCommand("wand", WandSubCommand.class);
        registerCommand("reload", ReloadSubCommand.class);
        registerCommand("chests", ChestsSubCommand.class);
        registerCommand("extensions", ExtensionsSubCommand.class);
        registerCommand("locations", LocationSubCommand.class);
        registerCommand("items", ItemsSubCommand.class);
        registerCommand("create", CreateChestSubCommand.class);
        Objects.requireNonNull(plugin.getCommand("randomlootchest")).setExecutor(this);
        Objects.requireNonNull(plugin.getCommand("randomlootchest")).setTabCompleter(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] strings) {
        try {
            if(strings.length >= 1) {
                if(arguments.containsKey(strings[0])) {
                    ArgumentCommand argumentCommand = arguments.get(strings[0]);
                    if(commandSender.hasPermission(argumentCommand.getPermission())) {
                        if(strings.length > 1 && argumentCommand.getArguments().isEmpty() && argumentCommand.hasArguments()) {
                            commandSender.sendMessage(Language.GENERAL_NO_ARGUMENTS.toString());
                            return true;
                        }
                        argumentCommand.execute(commandSender, strings);
                        return true;
                    } else {
                        commandSender.sendMessage(Language.GENERAL_NO_PERMISSION.toString());
                        return true;
                    }
                } else {
                    commandSender.sendMessage(Language.GENERAL_NO_ARGUMENT.toString());
                    return true;
                }
            } else {
                commandSender.sendMessage(ColourUtil.colorize("&aHelp for RandomLootChest:"));
                for(ArgumentCommand argumentCommand : arguments.values()) {
                    commandSender.sendMessage(ColourUtil.colorize(argumentCommand.getHelpText()));
                }
                return true;
            }
        } catch (Exception exception) {
            commandSender.sendMessage(ColourUtil.colorize("&cSomething went wrong! Contact with developer! Look at the console!"));
            Util.sendPluginMessage("&cException message: "+exception.getMessage());
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] strings) {
        List<String> completions = new ArrayList<>();
        if(strings.length == 1) {
            StringUtil.copyPartialMatches(strings[0], arguments.keySet(), completions);
            Collections.sort(completions);
            return completions;
        } else if(strings.length == 2 && !getArgumentsForSubcommand(strings[0]).isEmpty()) {
            return getArgumentsForSubcommand(strings[0]);
        } else return Collections.emptyList();
    }

    private List<String> getArgumentsForSubcommand(@NotNull String subcommand) {
        if (arguments.get(subcommand) == null) {
            return Collections.emptyList();
        }

        List<String> listArguments = arguments.get(subcommand).getArguments();

        Collections.sort(listArguments);
        return listArguments;
    }

    public void registerCommand(String command, Class<? extends ArgumentCommand> clazz) {
        try {
            ArgumentCommand argumentCommand = clazz.getConstructor().newInstance();
            arguments.put(command, argumentCommand);
            Bukkit.getPluginManager().addPermission(new Permission(argumentCommand.getPermission()));
        } catch (Exception e) {
            Util.sendPluginMessage("&cSomething went wrong while registering argument '"+command+"'!");
        }
    }

}