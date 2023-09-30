package me.dreamdevs.randomlootchest.objects;

import me.dreamdevs.randomlootchest.api.Config;
import me.dreamdevs.randomlootchest.api.utils.ColourUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WandItem {

    public static ItemStack WANDITEM = null;

    public static void loadVars() {
        WANDITEM = new ItemStack(Material.BLAZE_ROD);
        ItemMeta itemMeta = WANDITEM.getItemMeta();
        itemMeta.setDisplayName(ColourUtil.colorize(Config.WAND_ITEM_DISPLAY_NAME.toString()));
        WANDITEM.setItemMeta(itemMeta);
    }

}