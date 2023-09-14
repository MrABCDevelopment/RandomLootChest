package me.dreamdevs.randomlootchest.managers;

import lombok.Getter;
import me.dreamdevs.randomlootchest.RandomLootChestMain;
import me.dreamdevs.randomlootchest.utils.ColourUtil;
import me.dreamdevs.randomlootchest.utils.Util;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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
        Optional.ofNullable(config.getConfigurationSection("messages")).ifPresent(section -> {
            section.getKeys(false).forEach(s -> messages.put(s, ColourUtil.colorize(Objects.requireNonNull(section.getString(s)))));
        });
        Util.sendPluginMessage(messages.get("loaded-messages").replace("%AMOUNT%", Integer.toString(messages.size())));
    }

    public String getMessage(@NotNull String key) {
        return messages.getOrDefault(key, "This message key does not exist and is not hooked with any message!");
    }

    public void addMessage(@NotNull String key, @NotNull String message) {
        messages.putIfAbsent(key, ColourUtil.colorize(message));
    }

}