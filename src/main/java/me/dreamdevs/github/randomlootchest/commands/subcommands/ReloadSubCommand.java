package me.dreamdevs.github.randomlootchest.commands.subcommands;

import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.api.commands.ArgumentCommand;
import me.dreamdevs.github.randomlootchest.menus.ChestReloadMenu;
import me.dreamdevs.github.randomlootchest.api.objects.WandItem;
import me.dreamdevs.github.randomlootchest.utils.Settings;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadSubCommand implements ArgumentCommand {
    @Override
    public boolean execute(CommandSender commandSender, String[] args) {
        if(!(commandSender instanceof Player)) {
            RandomLootChestMain.getInstance().getConfigManager().reload("config.yml");
            Settings.loadVars();
            RandomLootChestMain.getInstance().getConfigManager().reload("messages.yml");
            RandomLootChestMain.getInstance().getMessagesManager().load(RandomLootChestMain.getInstance());
            RandomLootChestMain.getInstance().getChestsManager().load(RandomLootChestMain.getInstance());
            RandomLootChestMain.getInstance().getLocationManager().save();
            WandItem.loadVars();
            commandSender.sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessages().get("chest-command-reload"));
        } else {
            Player player = (Player) commandSender;
            new ChestReloadMenu(player);
        }
        return true;
    }

    @Override
    public String getHelpText() {
        return "&6/randomlootchest reload - reloads all configurations";
    }

    @Override
    public String getPermission() {
        return "randomlootchest.admin.reload";
    }
}
