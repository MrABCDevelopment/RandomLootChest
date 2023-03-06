package me.dreamdevs.github.randomlootchest.api.inventory.actions;

import me.dreamdevs.github.randomlootchest.api.events.ClickInventoryEvent;
import me.dreamdevs.github.randomlootchest.api.inventory.Action;

public class CloseAction implements Action {

    @Override
    public void performAction(ClickInventoryEvent event) {
        event.getPlayer().closeInventory();
    }
}