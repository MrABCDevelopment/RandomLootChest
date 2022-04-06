package me.mrsandking.github.randomlootchest.commands;

import me.mrsandking.github.randomlootchest.RandomLootChestMain;
import me.mrsandking.github.randomlootchest.objects.WandItem;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChestCommand implements CommandExecutor {

    private final RandomLootChestMain plugin;

    public ChestCommand(RandomLootChestMain plugin) {
        this.plugin = plugin;
        plugin.getCommand("chests").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)) {
            Bukkit.getConsoleSender().sendMessage(plugin.getMessagesManager().getMessages().get("not-player"));
            return true;
        }
        Player player = (Player) commandSender;
        if(strings.length == 1) {
            if(strings[0].equalsIgnoreCase("wand")) {
                player.getInventory().addItem(WandItem.WANDITEM);
                return true;
            } else if(strings[0].equalsIgnoreCase("save")) {
                plugin.getLocationManager().save();
                player.sendMessage(plugin.getMessagesManager().getMessages().get("location-save"));
                return true;
            } else if(strings[0].equalsIgnoreCase("reload")) {
                plugin.getConfigManager().reload("config.yml");
                plugin.getConfigManager().reload("chests.yml");
                plugin.getConfigManager().reload("messages.yml");
                plugin.getMessagesManager().load(plugin);
                plugin.getChestsManager().load(plugin);
                plugin.getStarterManager().load();
                plugin.getLocationManager().save();
                player.sendMessage(plugin.getMessagesManager().getMessages().get("chest-command-reload"));
                return true;
            } else {
                player.sendMessage(plugin.getMessagesManager().getMessages().get("chest-command-correct-usage"));
                return true;
            }
        } else {
            player.sendMessage(plugin.getMessagesManager().getMessages().get("chest-command-correct-usage"));
        }
        return true;
    }
}