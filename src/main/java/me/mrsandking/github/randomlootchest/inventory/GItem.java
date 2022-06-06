package me.mrsandking.github.randomlootchest.inventory;

import lombok.Getter;
import lombok.Setter;
import me.mrsandking.github.randomlootchest.events.RLCClickInventoryEvent;
import me.mrsandking.github.randomlootchest.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

@Getter @Setter
public class GItem {

    private ItemStack itemStack;
    private Material material;
    private int amount;
    private List<Action> actions;
    private String displayName;
    private Map<Enchantment, Integer> enchantments;
    private Set<ItemFlag> itemFlags;
    private boolean unbreakable;
    private List<String> lore;
    private ItemMeta itemMeta;

    public GItem(ItemStack itemStack) {
        this(itemStack.getType(), itemStack.getItemMeta().getDisplayName(), itemStack.getItemMeta().getLore(), itemStack.getAmount(), itemStack.getEnchantments(), itemStack.getItemMeta().getItemFlags(), itemStack.getItemMeta().isUnbreakable(), new Action[0]);
    }

    public GItem(Material material, String displayName, List<String> lore, int amount, Map<Enchantment, Integer> enchantments, Set<ItemFlag> itemFlags, boolean unbreakable, Action... actions) {
        this.material = material;
        this.displayName = ChatColor.translateAlternateColorCodes('&', displayName);
        this.lore = Util.colouredLore(lore);
        this.amount = amount;
        this.enchantments = enchantments;
        this.itemFlags = itemFlags;
        this.unbreakable = unbreakable;
        this.actions = new ArrayList<>();
        this.actions.addAll(Collections.unmodifiableList(Arrays.asList(actions)));
        this.itemStack = new ItemStack(material, amount);
        this.itemMeta = this.itemStack.getItemMeta();
        this.itemMeta.setDisplayName(this.displayName);
        this.itemMeta.setLore(this.lore);
        for(ItemFlag flag : this.itemFlags)
            this.itemMeta.addItemFlags(flag);
        this.itemMeta.setUnbreakable(this.unbreakable);
        this.itemStack.setItemMeta(this.itemMeta);
        this.itemStack.setAmount(this.amount);
        this.itemStack.addEnchantments(this.enchantments);
    }

    public GItem(Material material, int amount, Action... action) {
        this(material, null, new ArrayList<>(), amount, new HashMap<>(), new HashSet<>(), false, action);
    }

    public GItem(Material material, int amount) {
        this(material, null, new ArrayList<>(), amount, new HashMap<>(), new HashSet<>(), false,  new Action[0]);
    }

    public GItem(Material material) {
        this(material, null, new ArrayList<>(), 1, new HashMap<>(), new HashSet<>(), false, new Action[0]);
    }

    public GItem(Material material, String displayName, List<String> list) {
        this(material, displayName, list, 1, new HashMap<>(), new HashSet<>(), false, new Action[0]);
    }

    public void addAction(Action action) {
        actions.add(action);
    }

    public void addActions(Action... actions) {
        for(Action action : actions) {
            this.actions.add(action);
        }
    }

    public void execute(RLCClickInventoryEvent event) {
        for(Action action : actions)
            action.performAction(event);
    }

}