package me.dreamdevs.github.randomlootchest.commands.subcommands;

import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.api.commands.ArgumentCommand;
import me.dreamdevs.github.randomlootchest.api.extensions.Extension;
import me.dreamdevs.github.randomlootchest.api.menu.extensions.ExtensionsMenu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ExtensionsSubCommand implements ArgumentCommand {

    @Override
    public boolean execute(CommandSender commandSender, String[] args) {
        if(!(commandSender instanceof Player)) {
            if(args[1].equalsIgnoreCase("all")) {
                RandomLootChestMain.getInstance().getExtensionManager().getEnabledExtensions().forEach(extension -> {
                    extension.reloadConfig();
                    extension.onExtensionDisable();
                    extension.onExtensionEnable();
                    commandSender.sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessage("extensions-reload-config").replaceAll("%EXTENSION_NAME%", extension.getDescription().getExtensionName()));
                });
                return true;
            }
            if(!getArguments().contains(args[1])) {
                commandSender.sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessage("no-extension"));
                return true;
            }
            Extension extension = RandomLootChestMain.getInstance().getExtensionManager().getExtensionByName(args[1]);
            extension.reloadConfig();
            extension.onExtensionDisable();
            extension.onExtensionEnable();
            commandSender.sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessage("extensions-reload-config").replaceAll("%EXTENSION_NAME%", extension.getDescription().getExtensionName()));
            return true;
        }
        Player player = (Player) commandSender;
        new ExtensionsMenu(player);
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
        for(Extension extension : RandomLootChestMain.getInstance().getExtensionManager().getEnabledExtensions())
            extensions.add(extension.getDescription().getExtensionName());
        return extensions;
    }
}