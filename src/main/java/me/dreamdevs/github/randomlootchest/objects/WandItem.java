package me.dreamdevs.github.randomlootchest.objects;

import me.dreamdevs.github.randomlootchest.utils.Settings;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WandItem {

    public static ItemStack WANDITEM = null;

    public WandItem() {
        WANDITEM = new ItemStack(Material.BLAZE_ROD);
        ItemMeta itemMeta = WANDITEM.getItemMeta();
        itemMeta.setDisplayName(Settings.wandItemDisplayName);
        WANDITEM.setItemMeta(itemMeta);
    }

}