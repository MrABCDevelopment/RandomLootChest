package me.mrsandking.github.randomlootchest.manager;

import lombok.Getter;
import me.mrsandking.github.randomlootchest.RandomLootChestMain;
import me.mrsandking.github.randomlootchest.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StarterManager {

    private @Getter List<ItemStack> itemStacks;

    public StarterManager() {
        itemStacks = new ArrayList<>();
        load();
    }

    public void load() {
        itemStacks.clear();
        for(String k : RandomLootChestMain.getInstance().getConfigManager().getConfig("config.yml").getStringList("start-items")) {
            String[] param = k.split(":");
            try {
                ItemStack itemStack = new ItemStack(Material.getMaterial(param[0].toUpperCase()));
                ItemMeta itemMeta = itemStack.getItemMeta();
                if(param.length > 2) {
                    itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', param[2]));
                    String[] param1 = param[3].split(";");
                    itemMeta.setLore(Util.colouredLore(param1));
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
                itemStacks.add(itemStack);
            } catch (NullPointerException e) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"There is an error with '"+ param[0] +"' in config.yml");
                continue;
            }
        }
    }

    public void loadItems(Player player) {
        for(ItemStack itemStack : itemStacks) {
            if(isHelmet(itemStack)) player.getInventory().setHelmet(itemStack);
            else if(isChestplate(itemStack)) player.getInventory().setChestplate(itemStack);
            else if(isLeggings(itemStack)) player.getInventory().setLeggings(itemStack);
            else if(isBoots(itemStack)) player.getInventory().setBoots(itemStack);
            else player.getInventory().addItem(itemStack);
        }
    }

    public boolean isStarterItem(ItemStack itemStack) {
        return itemStack.getItemMeta().getLore() != null &&
                itemStack.getItemMeta().getLore().contains(ChatColor.GOLD+"Starter Item");
    }

    private boolean isHelmet(ItemStack itemStack) {
        switch (itemStack.getType()) {
            case LEATHER_HELMET:
            case GOLD_HELMET:
            case CHAINMAIL_HELMET:
            case IRON_HELMET:
            case DIAMOND_HELMET:
                return true;
            default:
                return false;
        }
    }

    private boolean isChestplate(ItemStack itemStack) {
        switch (itemStack.getType()) {
            case LEATHER_CHESTPLATE:
            case GOLD_CHESTPLATE:
            case CHAINMAIL_CHESTPLATE:
            case IRON_CHESTPLATE:
            case DIAMOND_CHESTPLATE:
                return true;
            default:
                return false;
        }
    }

    private boolean isLeggings(ItemStack itemStack) {
        switch (itemStack.getType()) {
            case LEATHER_LEGGINGS:
            case GOLD_LEGGINGS:
            case CHAINMAIL_LEGGINGS:
            case IRON_LEGGINGS:
            case DIAMOND_LEGGINGS:
                return true;
            default:
                return false;
        }
    }

    private boolean isBoots(ItemStack itemStack) {
        switch (itemStack.getType()) {
            case LEATHER_BOOTS:
            case GOLD_BOOTS:
            case CHAINMAIL_BOOTS:
            case IRON_BOOTS:
            case DIAMOND_BOOTS:
                return true;
            default:
                return false;
        }
    }

}