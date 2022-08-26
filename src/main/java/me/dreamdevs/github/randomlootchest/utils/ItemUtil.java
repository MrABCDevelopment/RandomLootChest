package me.dreamdevs.github.randomlootchest.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.List;

public class ItemUtil {

    public static ItemStack parsedBasicItem(String material, int amount) {
        try {
            ItemStack itemStack = new ItemStack(Material.getMaterial(material.toUpperCase()), amount);
            return itemStack;
        } catch (NullPointerException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Cannot parse item with type: "+material);
            return null;
        }
    }

    public static ItemStack parsedBasicItem(String material, int amount, String displayName) {
        try {
            ItemStack itemStack = new ItemStack(Material.getMaterial(material.toUpperCase()), amount);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(ColourUtil.hexText(displayName));
            itemStack.setItemMeta(itemMeta);
            return itemStack;
        } catch (NullPointerException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Cannot parse item with type: "+material);
            return null;
        }
    }

    public static ItemStack parsedItem(String material, int amount, String displayName, List<String> lore, List<String> enchantments, boolean unbreakable, boolean glowing) {
        try {
            ItemStack itemStack = null;

            if(material.equalsIgnoreCase("enchanted_golden_apple")) {
                if (VersionUtil.isLegacy()) {
                    itemStack = new ItemStack(Material.GOLDEN_APPLE, amount, (byte) 1);
                }
            }
            if(itemStack == null)
                itemStack = new ItemStack(Material.getMaterial(material.toUpperCase()), amount);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(ColourUtil.hexText(displayName));
            if(lore != null)
                itemMeta.setLore(ColourUtil.colouredLore(lore));
            if(!VersionUtil.isLegacy())
                itemMeta.setUnbreakable(unbreakable);
            if(glowing) {
                itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
            itemStack.setItemMeta(itemMeta);
            for(String s : enchantments) {
                String[] splits = s.split(":");
                itemStack.addUnsafeEnchantment(Enchantment.getByName(splits[0].toUpperCase()), Integer.parseInt(splits[1]));
            }
            return itemStack;
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Cannot parse item with type: "+material);
            return null;
        }
    }

    public static ItemStack parsedItem(String material, int amount, String displayName, List<String> lore, List<String> enchantments, boolean unbreakable) {
        return parsedItem(material, amount, displayName, lore, enchantments, unbreakable, false);
    }

    public static ItemStack parsedItem(String material, int amount, String displayName, List<String> lore) {
        return parsedItem(material, amount, displayName, lore, new ArrayList<>(), false, false);
    }

    public static ItemStack parsedItem(String material, String displayName, List<String> lore) {
        return parsedItem(material, 1, displayName, lore, new ArrayList<>(), false, false);
    }

    public static ItemStack getPotion(String material, int amount, String displayName, List<String> lore, List<String> enchantments, boolean unbreakable, boolean glowing, String potionType, int tier) {
        try {
            Potion potion = new Potion(PotionType.valueOf(potionType.toUpperCase()), tier, material == "POTION_SPLASH");
            ItemStack itemStack = potion.toItemStack(amount);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(ColourUtil.hexText(displayName));
            if(lore != null)
                itemMeta.setLore(ColourUtil.colouredLore(lore));
            if(!VersionUtil.isLegacy())
                itemMeta.setUnbreakable(unbreakable);
            if(glowing) {
                itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
            itemStack.setItemMeta(itemMeta);
            for(String s : enchantments) {
                String[] splits = s.split(":");
                itemStack.addUnsafeEnchantment(Enchantment.getByName(splits[0].toUpperCase()), Integer.parseInt(splits[1]));
            }
            return itemStack;
        } catch (NullPointerException | IllegalArgumentException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Cannot get potion with type: "+potionType);
            return null;
        }
    }

    public static ItemStack getPotion(String material, int amount, String displayName, List<String> lore, List<String> enchantments, boolean unbreakable, boolean glowing, String potionType, boolean extended, boolean upgraded) {
        try {
            ItemStack itemStack = parsedItem(material, amount, displayName, lore, enchantments, unbreakable, glowing);
            PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();
            PotionData potionData = new PotionData(PotionType.valueOf(potionType.toUpperCase()), extended, upgraded);
            potionMeta.setBasePotionData(potionData);
            itemStack.setItemMeta(potionMeta);
            return itemStack;
        } catch (NullPointerException | IllegalArgumentException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Cannot get potion with type: "+material+" and type "+potionType);
            return null;
        }
    }

}