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

    public GUI(String title, GUISize guiSize) {
        this.title = ChatColor.translateAlternateColorCodes('&', title);
        this.size = guiSize.getSize();
        this.gItems = new ArrayList<>();
        this.inventory = Bukkit.createInventory(this, size, this.title);
    }

    public void openGUI(Player player) {
        InventoryListener.guis.put(player.getUniqueId(), this);
        RLCOpenInventoryEvent openInventoryEvent = new RLCOpenInventoryEvent(player, this);
        player.openInventory(inventory);
        Bukkit.getPluginManager().callEvent(openInventoryEvent);
    }

    public void fillGUI(GItem gItem) {
        for(int x = 0; x<size; x++)
            addItem(gItem);
    }

    public void addItem(GItem gItem) {
        gItems.add(gItem);
        inventory.addItem(gItem.getItemStack());
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}