package me.dreamdevs.github.randomlootchest.api.menu;

import lombok.Getter;
import lombok.Setter;
import me.dreamdevs.github.randomlootchest.api.events.ClickInventoryEvent;
import me.dreamdevs.github.randomlootchest.utils.ColourUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

@Getter @Setter
public abstract class MenuItem {

    private ItemStack itemStack;
    private Material material;
    private int amount;
    private String displayName;
    private Map<Enchantment, Integer> enchantments;
    private Set<ItemFlag> itemFlags;
    private boolean unbreakable;
    private List<String> lore;
    private ItemMeta itemMeta;

    public MenuItem(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public MenuItem(Material material, String displayName, List<String> lore, int amount, Map<Enchantment, Integer> enchantments, Set<ItemFlag> itemFlags, boolean unbreakable) {
        this.material = material;
        this.displayName = ColourUtil.colorize(displayName);
        this.lore = ColourUtil.colouredLore(lore);
        this.amount = amount;
        this.enchantments = enchantments;
        this.itemFlags = itemFlags;
        this.unbreakable = unbreakable;
        this.itemStack = new ItemStack(material, amount);
        this.itemMeta = this.itemStack.getItemMeta();
        this.itemMeta.setDisplayName(this.displayName);
        this.itemMeta.setLore(this.lore);
        for(ItemFlag flag : this.itemFlags)
            this.itemMeta.addItemFlags(flag);
        this.itemMeta.setUnbreakable(this.unbreakable);
        this.itemStack.setItemMeta(this.itemMeta);
        this.itemStack.setAmount(this.amount);
        this.itemStack.addUnsafeEnchantments(this.enchantments);
    }

    public MenuItem(Material material, int amount) {
        this(material, null, new ArrayList<>(), amount, new HashMap<>(), new HashSet<>(), false);
    }

    public MenuItem(Material material) {
        this(material, material.name(), new ArrayList<>(), 1, new HashMap<>(), new HashSet<>(), false);
    }

    public MenuItem(Material material, String displayName) {
        this(material, displayName, new ArrayList<>(), 1, new HashMap<>(), new HashSet<>(), false);
    }

    public MenuItem(Material material, String displayName, List<String> list) {
        this(material, displayName, list, 1, new HashMap<>(), new HashSet<>(), false);
    }

    public abstract void performAction(ClickInventoryEvent event);

}