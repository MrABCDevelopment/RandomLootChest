package me.mrsandking.github.randomlootchest.objects;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WandItem {

    public static ItemStack WANDITEM = null;

    static {
        WANDITEM = new ItemStack(Material.BLAZE_ROD);
        ItemMeta itemMeta = WANDITEM.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GOLD+"Chest Wand");
        WANDITEM.setItemMeta(itemMeta);
    }

}