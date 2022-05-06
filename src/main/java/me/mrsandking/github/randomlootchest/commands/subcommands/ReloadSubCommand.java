package me.mrsandking.github.randomlootchest.commands.subcommands;

import me.mrsandking.github.randomlootchest.RandomLootChestMain;
import me.mrsandking.github.randomlootchest.commands.ArgumentCommand;
import me.mrsandking.github.randomlootchest.objects.WandItem;
import me.mrsandking.github.randomlootchest.util.Settings;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ReloadSubCommand implements ArgumentCommand {
    @Override
    public boolean execute(CommandSender commandSender, String[] args) {
        RandomLootChestMain.getInstance().getConfigManager().reload("config.yml");
        new Settings(RandomLootChestMain.getInstance());
        RandomLootChestMain.getInstance().getConfigManager().reload("chests.yml");
        RandomLootChestMain.getInstance().getConfigManager().reload("messages.yml");
        RandomLootChestMain.getInstance().getMessagesManager().load(RandomLootChestMain.getInstance());
        RandomLootChestMain.getInstance().getChestsManager().load(RandomLootChestMain.getInstance());
        RandomLootChestMain.getInstance().getStarterManager().load();
        RandomLootChestMain.getInstance().getLocationManager().save();
        new WandItem();
        commandSender.sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessages().get("chest-command-reload"));
        return true;
    }

    @Override
    public String getHelpText() {
        return ChatColor.GOLD+"/randomlootchest reload - reloads all configurations";
    }

    @Override
    public String getPermission() {
        return "randomlootchest.admin.reload";
    }
}
