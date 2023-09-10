package menu;

import me.dreamdevs.github.randomlootchest.utils.ColourUtil;
import org.bukkit.Material;

public class NextPageMenuItem extends MenuItem {

    public NextPageMenuItem() {
        super(Material.ARROW, "&aNext Page »");
    }

    @Override
    public void performAction(ClickInventoryEvent event) {
        BookMenu bookMenu = (BookMenu) event.getMenu();

        if(((bookMenu.index + 1) < bookMenu.getActualItems().size())) {
            bookMenu.page = bookMenu.page+1;
            event.getPlayer().closeInventory();
            bookMenu.open(event.getPlayer());
        } else {
            event.getPlayer().sendMessage(ColourUtil.colorize("&cYou are on the last page!"));
        }
    }
}