package me.dreamdevs.github.randomlootchest.managers;

import lombok.Getter;
import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.api.objects.RandomItem;
import me.dreamdevs.github.randomlootchest.utils.ItemUtil;
import me.dreamdevs.github.randomlootchest.utils.Util;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ItemsManager {

    private final @Getter HashMap<String, RandomItem> items;

    public ItemsManager() {
        items = new HashMap<>();
        load(RandomLootChestMain.getInstance());
    }

    public void load(RandomLootChestMain plugin) {
        items.clear();
        FileConfiguration config = plugin.getConfigManager().getConfig("items.yml");
        ConfigurationSection section = config.getConfigurationSection("Items");
        section.getKeys(false).forEach(s -> {
            Map<String, Integer> enchantments = new HashMap<>();
            if(section.getConfigurationSection("Enchantments") != null)
                for(String key : section.getConfigurationSection("Enchantments").getKeys(false))
                    enchantments.put(key.toUpperCase(), section.getConfigurationSection("Enchantments").getInt(key));
            ItemStack itemStack = ItemUtil.parsedItem(section.getString(s+".Material"), section.getInt(s+".Amount"), section.getString(s+".DisplayName"), section.getStringList(s+".DisplayLore"), enchantments, section.getBoolean(s+".Unbreakable"), section.getBoolean(s+".Glowing"));
            items.put(s, new RandomItem(itemStack, section.getDouble(s+".Chance")));
        });

        Util.sendPluginMessage("&aLoaded "+items.size()+" items from 'items.yml'!");
    }

}