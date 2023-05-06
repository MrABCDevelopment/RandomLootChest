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

public class ItemsManager {

    private @Getter HashMap<String, RandomItem> items;

    public ItemsManager() {
        items = new HashMap<>();
        FileConfiguration config = RandomLootChestMain.getInstance().getConfigManager().getConfig("items.yml");
        ConfigurationSection section = config.getConfigurationSection("Items");
        section.getKeys(false).forEach(s -> {
            ItemStack itemStack = ItemUtil.parsedItem(section.getString(s+".Material"), section.getInt(s+".Amount"), section.getString(s+".DisplayName"), section.getStringList(s+".DisplayLore"), section.getStringList(s+".Enchantments"), section.getBoolean(s+".Unbreakable"), section.getBoolean(s+".Glowing"));
            items.put(s, new RandomItem(itemStack, section.getDouble(s+".Chance")));
        });

        Util.sendPluginMessage("&aLoaded "+items.size()+" items from 'items.yml'!");
    }

}