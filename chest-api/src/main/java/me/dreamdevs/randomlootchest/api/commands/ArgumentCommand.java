package me.dreamdevs.randomlootchest.api.commands;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface ArgumentCommand {

    boolean execute(CommandSender commandSender, String[] args);

    String getHelpText();

    String getPermission();

    List<String> getArguments();

}