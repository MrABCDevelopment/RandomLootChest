package menu;

import org.bukkit.inventory.ItemStack;

public class NormalMenuItem extends MenuItem {

    public NormalMenuItem(ItemStack itemStack) {
        super(itemStack);
    }

    @Override
    public void performAction(ClickInventoryEvent event) {

    }
}
