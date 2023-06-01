package me.dreamdevs.github.randomlootchest.api.menu;

import me.dreamdevs.github.randomlootchest.api.events.ClickInventoryEvent;
import org.bukkit.inventory.ItemStack;

public class NormalMenuItem extends MenuItem {

    public NormalMenuItem(ItemStack itemStack) {
        super(itemStack);
    }

    @Override
    public void performAction(ClickInventoryEvent event) {

    }
}
