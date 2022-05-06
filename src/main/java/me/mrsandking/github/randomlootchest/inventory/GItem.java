package me.mrsandking.github.randomlootchest.inventory;

import lombok.Getter;
import lombok.Setter;
import me.mrsandking.github.randomlootchest.events.RLCClickInventoryEvent;
import me.mrsandking.github.randomlootchest.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Getter @Setter
public class GItem {

    private ItemStack itemStack;
    private Material material;
    private int amount;
    private List<Action> actions;
    private String displayName;

    public GItem(Material material, int amount, Action... action) {
        this.material = material;
        this.amount = amount;
        this.actions = new ArrayList<>();
        actions.addAll(Collections.unmodifiableList(Arrays.asList(action)));
        this.itemStack = new ItemStack(material, amount);
    }

    public GItem(Material material, int amount) {
        this(material, amount, new Action[0]);
    }

    public GItem(Material material) {
        this(material, 1, new Action[0]);
    }

    public GItem(Material material, String displayName, List<String> list) {
        this(material, 1, new Action[0]);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
        itemMeta.setLore(Util.colouredLore(list));
        itemStack.setItemMeta(itemMeta);
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