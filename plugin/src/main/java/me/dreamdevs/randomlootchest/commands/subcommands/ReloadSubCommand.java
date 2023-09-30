package me.dreamdevs.randomlootchest.commands.subcommands;

import me.dreamdevs.randomlootchest.RandomLootChestMain;
import me.dreamdevs.randomlootchest.api.Config;
import me.dreamdevs.randomlootchest.api.Language;
import me.dreamdevs.randomlootchest.api.commands.ArgumentCommand;
import me.dreamdevs.randomlootchest.menus.ReloadMenu;
import me.dreamdevs.randomlootchest.objects.WandItem;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class ReloadSubCommand implements ArgumentCommand {
    @Override
    public boolean execute(CommandSender commandSender, String[] args) {
        if(!(commandSender instanceof Player)) {
            if (args.length > 2) {
                commandSender.sendMessage(Language.GENERAL_NO_ARGUMENTS.toString());
                return true;
            }

            if (args.length == 1) {
                Config.reloadFile();
                Language.reloadLanguage();
                RandomLootChestMain.getInstance().getChestsManager().load(RandomLootChestMain.getInstance());
                RandomLootChestMain.getInstance().getLocationManager().save();
                RandomLootChestMain.getInstance().getItemsManager().save();
                RandomLootChestMain.getInstance().getItemsManager().load(RandomLootChestMain.getInstance());
                WandItem.loadVars();
                commandSender.sendMessage(Language.COMMAND_RELOAD_FILES.toString());
                return true;
            }

            if (!getArguments().contains(args[1])) {
                commandSender.sendMessage(Language.GENERAL_NO_ARGUMENT.toString());
                return true;
            }

            if(args[1].equalsIgnoreCase("messages")) {
                Language.reloadLanguage();
                commandSender.sendMessage(Language.COMMAND_RELOAD_MESSAGES.toString());
                return true;
            }
            if(args[1].equalsIgnoreCase("config")) {
                Config.reloadFile();
                WandItem.loadVars();
                commandSender.sendMessage(Language.COMMAND_RELOAD_CONFIG.toString());
                return true;
            }
            if(args[1].equalsIgnoreCase("items")) {
                RandomLootChestMain.getInstance().getItemsManager().save();
                RandomLootChestMain.getInstance().getItemsManager().load(RandomLootChestMain.getInstance());
                commandSender.sendMessage(Language.COMMAND_RELOAD_ITEMS.toString());
                return true;
            }
            if(args[1].equalsIgnoreCase("chests")) {
                RandomLootChestMain.getInstance().getChestsManager().load(RandomLootChestMain.getInstance());
                commandSender.sendMessage(Language.COMMAND_RELOAD_CHESTS.toString());
                return true;
            }
            if(args[1].equalsIgnoreCase("extensions")) {
                RandomLootChestMain.getInstance().getExtensionManager().getEnabledExtensions().forEach(extension -> {
                    extension.reloadConfig();
                    extension.onExtensionDisable();
                    extension.onExtensionEnable();
                    commandSender.sendMessage(Language.GENERAL_EXTENSION_CONFIG_RELOADED.toString().replace("%EXTENSION_NAME%", extension.getDescription().getExtensionName()));
                });
                return true;
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
