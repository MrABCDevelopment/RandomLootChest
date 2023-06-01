package me.dreamdevs.github.randomlootchest.managers;

import lombok.Getter;
import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.utils.ColourUtil;
import me.dreamdevs.github.randomlootchest.utils.Util;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
public class MessagesManager {

    private final Map<String, String> messages;

    public MessagesManager(RandomLootChestMain plugin) {
        messages = new HashMap<>();
        load(plugin);
    }

    public void load(RandomLootChestMain plugin) {
        messages.clear();
        FileConfiguration config = plugin.getConfigManager().getConfig("messages.yml");
        ConfigurationSection section = config.getConfigurationSection("messages");
        section.getKeys(false).forEach(s -> messages.put(s, ColourUtil.colorize(Objects.requireNonNull(section.getString(s)))));
        Util.sendPluginMessage(messages.get("loaded-messages").replaceAll("%AMOUNT%", Integer.toString(messages.size())));
    }

    public String getMessage(String key) {
        return messages.get(key);
    }

    public void addMessage(String key, String message) {
        messages.putIfAbsent(key, ColourUtil.colorize(message));
    }

}