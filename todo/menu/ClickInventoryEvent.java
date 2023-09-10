package menu;

import me.dreamdevs.github.randomlootchest.api.old.menu.Menu;
import lombok.Getter;
import me.dreamdevs.github.randomlootchest.api.old.menu.MenuItem;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

@Getter
public class ClickInventoryEvent extends Event {

    private @Getter static final HandlerList list = new HandlerList();

    private final InventoryClickEvent event;
    private final Player player;
    private final int slot;
    private final Menu menu;
    private final MenuItem menuItem;
    private final ClickType clickType;

    public ClickInventoryEvent(InventoryClickEvent event, Player player, Menu menu, int slot, MenuItem menuItem, ClickType clickType) {
        this.event = event;
        this.player = player;
        this.menu = menu;
        this.slot = slot;
        this.menuItem = menuItem;
        this.clickType = clickType;
    }

    @Override
    public HandlerList getHandlers() {
        return list;
    }
}