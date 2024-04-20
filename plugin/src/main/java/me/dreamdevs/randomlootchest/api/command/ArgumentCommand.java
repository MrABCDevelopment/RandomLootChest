package me.dreamdevs.randomlootchest.api.command;

import org.bukkit.command.CommandSender;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public interface ArgumentCommand {

    boolean execute(CommandSender commandSender, String[] args);

    String getHelpText();

    String getPermission();

    boolean hasArguments();

    default List<String> getArguments() {
        return Collections.emptyList();
    }

}