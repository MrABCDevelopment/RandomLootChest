package me.dreamdevs.github.randomlootchest.managers;

import lombok.Getter;
import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.utils.ColourUtil;
import me.dreamdevs.github.randomlootchest.utils.ItemUtil;
import me.dreamdevs.github.randomlootchest.utils.Util;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class StarterManager {

    private @Getter ItemStack[] itemStacks;
    private boolean useStarterItems;

    public StarterManager() {
        load();
    }

    public void load() {
        FileConfiguration config = RandomLootChestMain.getInstance().getConfigManager().getConfig("starter.yml");
        itemStacks = new ItemStack[config.getStringList("starter-items").size()];
        String text = ColourUtil.colorize(config.getString("lore-id-text"));
        useStarterItems = config.getBoolean("use-starter-items");
        for(String k : config.getStringList("starter-items")) {
            String[] param = k.split(":");
            try {
                ItemStack itemStack = ItemUtil.parsedItem(param);
                ItemMeta itemMeta = itemStack.getItemMeta();
                if(itemMeta.getLore() == null || itemMeta.getLore().isEmpty())
                    itemMeta.setLore(Arrays.asList(text));
                else {
                    List<String> list = itemMeta.getLore();
                    list.add(text);
                    itemMeta.setLore(list);
                }
                itemStack.setItemMeta(itemMeta);
            } catch (NullPointerException e) {
                Util.sendPluginMessage("&cThere is an error with '"+ param[0] +"' in config.yml");
            }
        }
    }

    public void loadItems(Player player) {
        if(useStarterItems)
            player.getInventory().addItem(itemStacks);
    }

    public boolean isStarterItem(ItemStack itemStack) {
        return itemStack.getItemMeta().getLore() != null && itemStack.getItemMeta().getLore().contains(ChatColor.GOLD+"Starter Item");
    }

}