package me.dreamdevs.github.randomlootchest.api.objects;

import me.dreamdevs.github.randomlootchest.utils.Settings;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WandItem {

    public static ItemStack WANDITEM = null;

    public static void loadVars() {
        WANDITEM = new ItemStack(Material.BLAZE_ROD);
        ItemMeta itemMeta = WANDITEM.getItemMeta();
        itemMeta.setDisplayName(Settings.wandItemDisplayName);
        WANDITEM.setItemMeta(itemMeta);
    }

}