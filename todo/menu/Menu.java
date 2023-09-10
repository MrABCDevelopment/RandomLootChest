package menu;

import lombok.Getter;
import me.dreamdevs.github.randomlootchest.listeners.InventoryListener;
import me.dreamdevs.github.randomlootchest.utils.ColourUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class Menu implements InventoryHolder {

    private final String title;
    private Inventory inventory;
    private int size;

    private MenuItem[] menuItems;

    private boolean protect;

    public Menu(String title, MenuSize menuSize, boolean protect) {
        this.title = ColourUtil.colorize(title);
        this.size = menuSize.getSize();
        this.protect = protect;
        this.menuItems = new MenuItem[size];
    }

    public abstract void setItems();

    public void open(Player player) {
        this.inventory = Bukkit.createInventory(this, size, this.title);

        this.setItems();

        InventoryListener.menus.put(player.getUniqueId(), this);
        player.openInventory(inventory);
    }

    public void setItem(int slot, MenuItem menuItem) {
        this.menuItems[slot] = menuItem;
    }

    public void fill(MenuItem menuItem) {
        for(int x = 0; x<size; x++) {
            setItem(x, menuItem);
        }
    }

    public List<MenuItem> getActualItems() {
        List<MenuItem> actualItems = new ArrayList<>();
        for(MenuItem menuItem : getMenuItems()) {
            if(menuItem != null) {
                if(!(menuItem instanceof CloseMenuItem) && !(menuItem instanceof NextPageMenuItem) && !(menuItem instanceof ReturnPageMenuItem) && !(menuItem instanceof StaticMenuItem)) {
                    actualItems.add(menuItem);
                }
            }
        }
        return actualItems;
    }

    public void addItem(MenuItem menuItem) {
        for(int x = 0; x<size; x++) {
            if(menuItems[x] == null) {
                setItem(x, menuItem);
                break;
            }
        }
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}