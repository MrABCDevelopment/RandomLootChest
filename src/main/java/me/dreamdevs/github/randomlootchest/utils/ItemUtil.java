package me.dreamdevs.github.randomlootchest.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.*;

import java.util.List;

@UtilityClass
public class ItemUtil {

    public static ItemStack parsedBasicItem(String material, int amount) {
        try {
            ItemStack itemStack = new ItemStack(Material.getMaterial(material.toUpperCase()), amount);
            return itemStack;
        } catch (NullPointerException e) {
            Util.sendPluginMessage(ChatColor.RED+"Cannot parse item with type: "+material);
            return null;
        }
    }

    public static ItemStack parsedBasicItem(String material, int amount, String displayName) {
        try {
            ItemStack itemStack = parsedBasicItem(material, amount);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(ColourUtil.colorize(displayName));
            itemStack.setItemMeta(itemMeta);
            return itemStack;
        } catch (NullPointerException e) {
            Util.sendPluginMessage(ChatColor.RED+"Cannot parse item with type: "+material);
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
                itemStack = parsedBasicItem(material, amount, displayName);
            ItemMeta itemMeta = itemStack.getItemMeta();
            if(lore != null)
                itemMeta.setLore(ColourUtil.colouredLore(lore));
            if(!VersionUtil.is1_8_orOlder())
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
            Util.sendPluginMessage(ChatColor.RED+"Cannot parse item with type: "+material);
            return null;
        }
    }

    public static ItemStack getPotion(String material, int amount, String displayName, List<String> lore, List<String> enchantments, boolean unbreakable, boolean glowing, String potionType, int tier) {
        try {
            Potion potion = new Potion(PotionType.valueOf(potionType.toUpperCase()), tier, material == "POTION_SPLASH");
            ItemStack itemStack = potion.toItemStack(amount);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(ColourUtil.colorize(displayName));
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
            Util.sendPluginMessage(ChatColor.RED+"Cannot get potion with type: "+potionType);
            return null;
        }
    }

    public static ItemStack getPotion(String material, int amount, String displayName, List<String> lore, List<String> enchantments, boolean unbreakable, boolean glowing, String potionType, boolean extended, boolean upgraded) {
        try {
            ItemStack itemStack = parsedItem(material, amount, displayName, lore, enchantments, unbreakable, glowing);
            PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();
            PotionType potionTypeObj = PotionType.valueOf(potionType.toUpperCase());
            PotionData potionData = new PotionData(potionTypeObj, extended, upgraded);
            potionMeta.setBasePotionData(potionData);
            itemStack.setItemMeta(potionMeta);
            return itemStack;
        } catch (NullPointerException | IllegalArgumentException e) {
            Util.sendPluginMessage(ChatColor.RED+"Cannot get potion with type: "+material+" and type "+potionType);
            return null;
        }
    }

    public static ItemStack parsedItem(String[] strings) {
        try {
            ItemStack itemStack = null;
            if (strings[0].equalsIgnoreCase("enchanted_golden_apple") && VersionUtil.isLegacy())
                itemStack = new ItemStack(Material.GOLDEN_APPLE, Integer.parseInt(strings[1], (short)1));
            if (itemStack == null)
                itemStack = new ItemStack(Material.getMaterial(strings[0].toUpperCase()),  Integer.parseInt(strings[1]));
            ItemMeta itemMeta = itemStack.getItemMeta();
            if(strings.length >= 3)
                itemMeta.setDisplayName(ColourUtil.colorize(strings[2]));
            if(strings.length >= 4) {
                String[] lore = strings[3].split(";");
                if (lore != null)
                    itemMeta.setLore(ColourUtil.colouredLore(lore));
            }
            return itemStack;
        } catch (Exception e) {
            e.printStackTrace();
            Util.sendPluginMessage(ChatColor.RED + "Cannot parse item with type: " + strings[0]);
            return null;
        }
    }

}