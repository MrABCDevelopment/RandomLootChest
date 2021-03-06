package me.mrsandking.github.randomlootchest.manager;

import lombok.Getter;
import me.mrsandking.github.randomlootchest.RandomLootChestMain;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;

public class MessagesManager {

    private @Getter HashMap<String, String> messages;

    public MessagesManager(RandomLootChestMain plugin) {
        messages = new HashMap<>();
        load(plugin);
    }

    public void load(RandomLootChestMain plugin) {
        messages.clear();
        FileConfiguration config = plugin.getConfigManager().getConfig("messages.yml");
        ConfigurationSection section = config.getConfigurationSection("messages");
        for(String id : section.getKeys(false))
        {
            String message = ChatColor.translateAlternateColorCodes('&', config.getString("messages."+id));
            messages.put(id, message);
        }
        Bukkit.getConsoleSender().sendMessage(messages.get("loaded-messages").replace("{MESSAGES}", Integer.toString(messages.size())));
    }

}