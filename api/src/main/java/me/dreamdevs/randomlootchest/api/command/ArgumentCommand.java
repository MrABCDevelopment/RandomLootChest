package me.dreamdevs.randomlootchest.api.command;

import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public interface ArgumentCommand {

    /**
     * Execute
     */

    boolean execute(CommandSender commandSender, String[] args);

    String getHelpText();

    String getPermission();

    default boolean hasArguments() {
        return false;
    }

    default List<String> getArguments() {
        return Collections.emptyList();
    }

}