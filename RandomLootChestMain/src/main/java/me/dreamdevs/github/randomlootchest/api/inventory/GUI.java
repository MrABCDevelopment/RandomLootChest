package me.dreamdevs.github.randomlootchest.api.inventory;

import lombok.Getter;
import me.dreamdevs.github.randomlootchest.listeners.InventoryListener;
import me.dreamdevs.github.randomlootchest.utils.ColourUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

@Getter
public class GUI implements InventoryHolder {

    private Inventory inventory;
    private String title;
    private int size;

    private GItem[] itemStacks;
    private boolean protect;

    public GUI(String title, GUISize guiSize, boolean protect) {
        this.title = ColourUtil.colorize(title);
        this.size = guiSize.getSize();
        this.itemStacks = new GItem[size];
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

    public void setItem(int slot, GItem gItem) {
        this.itemStacks[slot] = gItem;
        inventory.setItem(slot, gItem.getItemStack());
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}