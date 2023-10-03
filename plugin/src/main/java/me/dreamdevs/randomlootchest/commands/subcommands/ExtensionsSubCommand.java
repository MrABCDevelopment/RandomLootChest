package me.dreamdevs.randomlootchest.commands.subcommands;

import me.dreamdevs.randomlootchest.RandomLootChestMain;
import me.dreamdevs.randomlootchest.api.Language;
import me.dreamdevs.randomlootchest.api.commands.ArgumentCommand;
import me.dreamdevs.randomlootchest.api.extensions.Extension;
import me.dreamdevs.randomlootchest.menus.ExtensionMenu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ExtensionsSubCommand implements ArgumentCommand {

    @Override
    public boolean execute(CommandSender commandSender, String[] args) {
        if(!(commandSender instanceof Player player)) {
            if(args[1].equalsIgnoreCase("all")) {
                RandomLootChestMain.getInstance().getExtensionManager().getEnabledExtensions().forEach(extension -> {
                    extension.reloadConfig();
                    extension.onExtensionDisable();
                    extension.onExtensionEnable();
                    commandSender.sendMessage(Language.GENERAL_EXTENSION_CONFIG_RELOADED.toString().replace("%EXTENSION_NAME%", extension.getDescription().getExtensionName()));
                });
                return true;
            }
            if(!getArguments().contains(args[1])) {
                commandSender.sendMessage(Language.GENERAL_NO_EXTENSION.toString());
                return false;
            }
            Extension extension = RandomLootChestMain.getInstance().getExtensionManager().getExtensionByName(args[1]);
            extension.reloadConfig();
            extension.onExtensionDisable();
            extension.onExtensionEnable();
            commandSender.sendMessage(Language.GENERAL_EXTENSION_CONFIG_RELOADED.toString().replace("%EXTENSION_NAME%", extension.getDescription().getExtensionName()));
            return true;
        }
        new ExtensionMenu().open(player);
        return true;
    }

    @Override
    public String getHelpText() {
        return "&6/randomlootchest extensions [name] - opens extensions menu or reloads an extension";
    }

    @Override
    public String getPermission() {
        return "randomlootchest.admin.extensions";
    }

    @Override
    public List<String> getArguments() {
        List<String> extensions = new ArrayList<>();
        extensions.add("all");
        RandomLootChestMain.getInstance().getExtensionManager().getEnabledExtensions().forEach(extension ->
                extensions.add(extension.getDescription().getExtensionName()));
        return extensions;
    }
}