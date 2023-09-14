package me.dreamdevs.randomlootchest.commands.subcommands;

import me.dreamdevs.randomlootchest.RandomLootChestMain;
import me.dreamdevs.randomlootchest.api.commands.ArgumentCommand;
import me.dreamdevs.randomlootchest.menus.ReloadMenu;
import me.dreamdevs.randomlootchest.objects.WandItem;
import me.dreamdevs.randomlootchest.utils.Settings;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class ReloadSubCommand implements ArgumentCommand {
    @Override
    public boolean execute(CommandSender commandSender, String[] args) {
        if(!(commandSender instanceof Player)) {
            if(args.length > 2) {
                commandSender.sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessage("no-arguments"));
                return true;
            }
            if(!getArguments().contains(args[1])) {
                commandSender.sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessage("no-argument"));
                return true;
            }
            if(args.length == 1) {
                RandomLootChestMain.getInstance().getConfigManager().reload("config.yml");
                Settings.loadVars();
                RandomLootChestMain.getInstance().getConfigManager().reload("messages.yml");
                RandomLootChestMain.getInstance().getMessagesManager().load(RandomLootChestMain.getInstance());
                RandomLootChestMain.getInstance().getChestsManager().load(RandomLootChestMain.getInstance());
                RandomLootChestMain.getInstance().getLocationManager().save();
                RandomLootChestMain.getInstance().getConfigManager().reload("items.yml");
                RandomLootChestMain.getInstance().getItemsManager().load(RandomLootChestMain.getInstance());
                WandItem.loadVars();
                commandSender.sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessages().get("chest-command-reload"));
                return true;
            } else if(args.length == 2) {
                if(args[1].equalsIgnoreCase("messages")) {
                    RandomLootChestMain.getInstance().getConfigManager().reload("messages.yml");
                    RandomLootChestMain.getInstance().getMessagesManager().load(RandomLootChestMain.getInstance());
                    commandSender.sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessage("chest-command-reload-messages"));
                    return true;
                }
                if(args[1].equalsIgnoreCase("config")) {
                    RandomLootChestMain.getInstance().getConfigManager().reload("config.yml");
                    Settings.loadVars();
                    WandItem.loadVars();
                    commandSender.sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessage("chest-command-reload-config"));
                    return true;
                }
                if(args[1].equalsIgnoreCase("items")) {
                    RandomLootChestMain.getInstance().getConfigManager().reload("items.yml");
                    RandomLootChestMain.getInstance().getItemsManager().load(RandomLootChestMain.getInstance());
                    commandSender.sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessage("chest-command-reload-items"));
                    return true;
                }
                if(args[1].equalsIgnoreCase("chests")) {
                    RandomLootChestMain.getInstance().getChestsManager().load(RandomLootChestMain.getInstance());
                    commandSender.sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessage("chest-command-reload-chests"));
                    return true;
                }
                if(args[1].equalsIgnoreCase("extensions")) {
                    RandomLootChestMain.getInstance().getExtensionManager().getEnabledExtensions().forEach(extension -> {
                        extension.reloadConfig();
                        extension.onExtensionDisable();
                        extension.onExtensionEnable();
                        commandSender.sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessage("extensions-reload-config").replaceAll("%EXTENSION_NAME%", extension.getDescription().getExtensionName()));
                    });
                    return true;
                }
            }
        } else {
            Player player = (Player) commandSender;
            new ReloadMenu().open(player);
        }
        return true;
    }

    @Override
    public String getHelpText() {
        return "&6/randomlootchest reload [type] - reloads all configurations";
    }

    @Override
    public String getPermission() {
        return "randomlootchest.admin.reload";
    }

    @Override
    public List<String> getArguments() {
        return Arrays.asList("messages", "items", "config", "chests", "extensions");
    }
}
