package me.dreamdevs.randomlootchest.managers;

import lombok.Getter;
import me.dreamdevs.randomlootchest.RandomLootChestMain;
import me.dreamdevs.randomlootchest.objects.RandomItem;
import me.dreamdevs.randomlootchest.utils.ItemUtil;
import me.dreamdevs.randomlootchest.utils.Util;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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

    public void save(RandomLootChestMain plugin) {
        FileConfiguration config = plugin.getConfigManager().getConfig("items.yml");
        ConfigurationSection section = config.createSection("Items");
        items.forEach(((s, randomItem) -> {
            ConfigurationSection itemSection = section.createSection(s);
            itemSection.set("Material", randomItem.getItemStack().getType().name());
            itemSection.set("Amount", randomItem.getItemStack().getAmount());
            ItemMeta itemMeta = randomItem.getItemStack().getItemMeta();
            if(itemMeta.hasDisplayName())
                itemSection.set("DisplayName", itemMeta.getDisplayName());
            if(itemMeta.hasLore())
                itemSection.set("DisplayLore", itemMeta.getLore());
            itemSection.set("Unbreakable", itemMeta.isUnbreakable());
            itemSection.set("Glowing", itemMeta.getItemFlags().contains(ItemFlag.HIDE_ENCHANTS));
            if(itemMeta.hasEnchants()) {
                ConfigurationSection enchantmentSection = itemSection.createSection("Enchantments");
                for (Map.Entry<Enchantment, Integer> entry : itemMeta.getEnchants().entrySet()) {
                    enchantmentSection.set(entry.getKey().getName(), entry.getValue());
                }
            }
        }));
        plugin.getConfigManager().save("items.yml");
    }

}