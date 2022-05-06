package me.mrsandking.github.randomlootchest.listener;

import lombok.Getter;
import me.mrsandking.github.randomlootchest.events.RLCClickInventoryEvent;
import me.mrsandking.github.randomlootchest.inventory.GItem;
import me.mrsandking.github.randomlootchest.inventory.GUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.HashMap;
import java.util.UUID;

public class InventoryListener implements Listener {

    public @Getter static HashMap<UUID, GUI> guis = new HashMap<>();

    @EventHandler
    public void clickEvent(InventoryClickEvent event) {
        if(event.getClickedInventory() == null) return;
        if(event.getClickedInventory().getHolder() instanceof GUI) {
            event.setResult(Event.Result.DENY);
            GUI gui = guis.get(event.getWhoClicked().getUniqueId());
            int slot = event.getRawSlot();
            Player player = (Player) event.getWhoClicked();
            if (slot >= 0 && slot < gui.getSize() && slot < gui.getGItems().size()) {
                if (gui.getGItems().get(slot) == null) return;
                GItem gItem = gui.getGItems().get(slot);
                RLCClickInventoryEvent clickInventoryEvent = new RLCClickInventoryEvent(event, player, gui, slot, gItem, event.getClick());
                Bukkit.getPluginManager().callEvent(clickInventoryEvent);
                gItem.execute(clickInventoryEvent);
            }
        }
    }

    @EventHandler
    public void closeEvent(InventoryCloseEvent event) {
        if(event.getInventory().getHolder() instanceof GUI) {
            guis.remove(event.getPlayer().getUniqueId());
        }
    }

}