package me.dreamdevs.github.randomlootchest.managers;

import lombok.Getter;
import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class StarterManager {

    private @Getter ItemStack[] itemStacks;

    public StarterManager() {
        load();
    }
    public void load() {
        FileConfiguration config = RandomLootChestMain.getInstance().getConfigManager().getConfig("starter.yml");
        itemStacks = new ItemStack[config.getStringList("starter-items").size()];
        for(String k : RandomLootChestMain.getInstance().getConfigManager().getConfig("config.yml").getStringList("start-items")) {
            String[] param = k.split(":");
            try {
                ItemStack itemStack = new ItemStack(Material.getMaterial(param[0].toUpperCase()));
                ItemMeta itemMeta = itemStack.getItemMeta();
                if(param.length > 2) {
                    itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', param[2]));
                    String[] param1 = param[3].split(";");

           //         itemMeta.setLore(Util.colouredLore(param1));
                }
                if(itemMeta.getLore() == null || itemMeta.getLore().isEmpty())
                    itemMeta.setLore(Arrays.asList(ChatColor.GOLD+"Starter Item"));
                else {
                    List<String> list = itemMeta.getLore();
                    list.add(ChatColor.GOLD + "Starter Item");
                    itemMeta.setLore(list);
                }
                itemStack.setItemMeta(itemMeta);
                itemStack.setAmount(param.length > 1 ? Integer.parseInt(param[1]) : 1);
            } catch (NullPointerException e) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"There is an error with '"+ param[0] +"' in config.yml");
                continue;
            }
        }
    }

    public void loadItems(Player player) {
        player.getInventory().addItem(itemStacks);
    }

    public boolean isStarterItem(ItemStack itemStack) {
        return itemStack.getItemMeta().getLore() != null && itemStack.getItemMeta().getLore().contains(ChatColor.GOLD+"Starter Item");
    }

}