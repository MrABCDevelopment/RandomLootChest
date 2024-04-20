package me.dreamdevs.randomlootchest.managers;

import lombok.Getter;
import me.dreamdevs.randomlootchest.RandomLootChestMain;
import me.dreamdevs.randomlootchest.api.util.Util;
import me.dreamdevs.randomlootchest.objects.RandomItem;
import me.dreamdevs.randomlootchest.utils.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class ItemsManager {

    private YamlConfiguration config;
    private final @Getter Map<String, RandomItem> items;

    public ItemsManager() {
        items = new LinkedHashMap<>();
        load(RandomLootChestMain.getInstance());
    }

    public void load(RandomLootChestMain plugin) {
        items.clear();
        config = YamlConfiguration.loadConfiguration(plugin.getItemsFile());

        Optional<ConfigurationSection> section = Optional.ofNullable(config.getConfigurationSection("Items"));
        if (section.isPresent()) {
            section.ifPresent(itemsSection -> itemsSection.getKeys(false).forEach(string -> {
                Map<String, Integer> enchantments = new HashMap<>();
                Optional<ConfigurationSection> enchantmentsSection = Optional.ofNullable(itemsSection.getConfigurationSection("Enchantments"));
                if (enchantmentsSection.isPresent() && !enchantmentsSection.get().getKeys(false).isEmpty()) {
                    for (String key : enchantmentsSection.get().getKeys(false)) {
                        enchantments.put(key.toUpperCase(), enchantmentsSection.get().getInt(key));
                    }
                }

                ItemStack itemStack = ItemUtil.parsedItem(itemsSection.getString(string+".Material","STONE"),
                        itemsSection.getInt(string+".Amount",1), itemsSection.getString(string+".DisplayName"),
                        itemsSection.getStringList(string+".DisplayLore"), enchantments,
                        itemsSection.getBoolean(string+".Unbreakable", false), itemsSection.getBoolean(string+".Glowing",false));
                items.put(string, new RandomItem(itemStack, section.get().getDouble(string+".Chance"), section.get().getBoolean(string+".RandomAmount", false)));
            }));
        }

        Util.sendPluginMessage("&aLoaded "+items.size()+" items from 'items.yml'!");
    }

    public void save() {
        ConfigurationSection section = config.createSection("Items");
        items.forEach(((s, randomItem) -> {
            ConfigurationSection itemSection = section.createSection(s);

            ItemStack itemStack = randomItem.getItemStack();

            itemSection.set("Material", itemStack.getType().name());
            itemSection.set("Amount", itemStack.getAmount());
            ItemMeta itemMeta = itemStack.getItemMeta();

            if (itemMeta == null) {
                itemMeta = Bukkit.getItemFactory().getItemMeta(itemStack.getType());
            }

            if (itemMeta != null) {
                if (itemMeta.hasDisplayName()) {
                    itemSection.set("DisplayName", itemMeta.getDisplayName());
                }
                if (itemMeta.hasLore()) {
                    itemSection.set("DisplayLore", itemMeta.getLore());
                }
                itemSection.set("Unbreakable", itemMeta.isUnbreakable());
                itemSection.set("Glowing", itemMeta.getItemFlags().contains(ItemFlag.HIDE_ENCHANTS));
                if (itemMeta.hasEnchants()) {
                    ConfigurationSection enchantmentSection = itemSection.createSection("Enchantments");
                    for (Map.Entry<Enchantment, Integer> entry : itemMeta.getEnchants().entrySet()) {
                        enchantmentSection.set(entry.getKey().toString(), entry.getValue());
                    }
                }
            }
        }));
        try {
            config.save("items.yml");
        } catch (IOException e) {
            Util.sendPluginMessage("&cSomething went wrong while saving items.yml file!");
        }
    }

}