package me.dreamdevs.github.randomlootchest.api.inventory;

import lombok.Getter;
import me.dreamdevs.github.randomlootchest.listeners.InventoryListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GUI implements InventoryHolder {

    private Inventory inventory;
    private String title;
    private int size;
    private List<GItem> gItems;

    private ItemStack[] itemStacks;
    private boolean protect;

    public GUI(String title, GUISize guiSize, boolean protect) {
        this.title = ChatColor.translateAlternateColorCodes('&', title);
        this.size = guiSize.getSize();
        this.gItems = new ArrayList<>();
        this.itemStacks = new ItemStack[size];
        this.protect = protect;
        this.inventory = Bukkit.createInventory(this, size, this.title);
    }

    public GUI(String title, GUISize guiSize) {
        this(title, guiSize, true);
    }

    public void openGUI(Player player) {
        InventoryListener.guis.put(player.getUniqueId(), this);
        player.openInventory(inventory);
    }

    public void addItem(GItem gItem) {
        if(isProtect()) {
            gItems.add(gItem);
        }
        inventory.addItem(gItem.getItemStack());
    }

    public void setItem(int slot, GItem gItem) {
        this.itemStacks[slot] = gItem.getItemStack();
        inventory.setItem(slot, gItem.getItemStack());
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}