package me.mrsandking.github.randomlootchest.inventory;

import lombok.Getter;
import me.mrsandking.github.randomlootchest.events.RLCOpenInventoryEvent;
import me.mrsandking.github.randomlootchest.listener.InventoryListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GUI implements InventoryHolder {

    private Inventory inventory;
    private String title;
    private int size;
    private List<GItem> gItems;
    private boolean protect;

    public GUI(String title, GUISize guiSize, boolean protect) {
        this.title = ChatColor.translateAlternateColorCodes('&', title);
        this.size = guiSize.getSize();
        this.gItems = new ArrayList<>();
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

    public void fillGUI(GItem gItem) {
        for(int x = 0; x<size; x++)
            addItem(gItem);
    }

    public void addItem(GItem gItem) {
        if(isProtect())
            gItems.add(gItem);
        inventory.addItem(gItem.getItemStack());
    }

    public void setItem(int slot, GItem gItem) {
        if(isProtect())
            gItems.add(slot, gItem);
        inventory.setItem(slot, gItem.getItemStack());
    }

    public void update() {
        inventory.clear();
        for(GItem gItem : gItems) {
            addItem(gItem);
        }
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}