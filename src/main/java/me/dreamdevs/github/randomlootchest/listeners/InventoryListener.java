package me.dreamdevs.github.randomlootchest.listeners;

import lombok.Getter;
import me.dreamdevs.github.randomlootchest.api.events.ClickInventoryEvent;
import me.dreamdevs.github.randomlootchest.api.menu.Menu;
import me.dreamdevs.github.randomlootchest.api.menu.MenuItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.UUID;

public class InventoryListener implements Listener {

    public @Getter static HashMap<UUID, Menu> menus = new HashMap<>();

    @EventHandler
    public void clickEvent(InventoryClickEvent event) {
        if(event.getClickedInventory() == null) return;
        if(event.getClickedInventory().getHolder() instanceof Menu) {
            Menu menu = menus.get(event.getWhoClicked().getUniqueId());
            if(menu.isProtect())
                event.setResult(Event.Result.DENY);
            int slot = event.getRawSlot();
            Player player = (Player) event.getWhoClicked();
            if (slot >= 0 && slot < menu.getSize() && slot < menu.getMenuItems().length) {
                if (menu.getMenuItems()[slot] == null) return;
                MenuItem menuItem = menu.getMenuItems()[slot];
                ClickInventoryEvent clickInventoryEvent = new ClickInventoryEvent(event, player, menu, slot, menuItem, event.getClick());
                Bukkit.getPluginManager().callEvent(clickInventoryEvent);
                menuItem.performAction(clickInventoryEvent);
            }
        }
    }

    @EventHandler
    public void closeEvent(InventoryCloseEvent event) {
        if(event.getInventory().getHolder() instanceof Menu) {
            menus.remove(event.getPlayer().getUniqueId());
        }
    }

    @EventHandler
    public void quitEvent(PlayerQuitEvent event) {
        menus.remove(event.getPlayer().getUniqueId());
    }

}