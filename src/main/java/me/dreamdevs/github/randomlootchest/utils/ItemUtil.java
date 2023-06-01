package me.dreamdevs.github.randomlootchest.utils;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@UtilityClass
public class ItemUtil {

    private final String parseError = "&cCannot parse item with type: %MATERIAL%";

    public static ItemStack parsedBasicItem(@NonNull String material, int amount) {
        try {
            return new ItemStack(Objects.requireNonNull(Material.getMaterial(material.toUpperCase())), amount);
        } catch (NullPointerException e) {
            Util.sendPluginMessage(parseError.replaceAll("%MATERIAL%", material));
            return null;
        }
    }

    public static ItemStack parsedItem(String material, int amount, String displayName, List<String> lore, Map<String, Integer> enchantments, boolean unbreakable, boolean glowing) {
        try {
            ItemStack itemStack = parsedBasicItem(material, amount);
            ItemMeta itemMeta = itemStack.getItemMeta();
            if(displayName != null)
                itemMeta.setDisplayName(ColourUtil.colorize(displayName));
            if(lore != null)
                itemMeta.setLore(ColourUtil.colouredLore(lore));
            itemMeta.setUnbreakable(unbreakable);
            if(glowing) {
                itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
            itemStack.setItemMeta(itemMeta);
            for(Map.Entry<String, Integer> enchantmentsEntry : enchantments.entrySet()) {
                itemStack.addUnsafeEnchantment(Objects.requireNonNull(Enchantment.getByName(enchantmentsEntry.getKey())), enchantmentsEntry.getValue());
            }
            return itemStack;
        } catch (Exception e) {
            Util.sendPluginMessage(parseError.replaceAll("%MATERIAL%", material));
            return null;
        }
    }

    public static ItemStack getPotion(String material, int amount, String displayName, List<String> lore, Map<String, Integer> enchantments, boolean unbreakable, boolean glowing, String potionType, boolean extended, boolean upgraded) {
        try {
            ItemStack itemStack = parsedItem(material, amount, displayName, lore, enchantments, unbreakable, glowing);
            PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();
            PotionType potionTypeObj = PotionType.valueOf(potionType.toUpperCase());
            PotionData potionData = new PotionData(potionTypeObj, extended, upgraded);
            potionMeta.setBasePotionData(potionData);
            itemStack.setItemMeta(potionMeta);
            return itemStack;
        } catch (NullPointerException | IllegalArgumentException e) {
            Util.sendPluginMessage("&cCannot get potion with type: "+material+" and type "+potionType);
            return null;
        }
    }

}