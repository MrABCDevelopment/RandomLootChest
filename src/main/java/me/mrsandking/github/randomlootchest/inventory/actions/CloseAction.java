package me.mrsandking.github.randomlootchest.inventory.actions;

import me.mrsandking.github.randomlootchest.events.RLCClickInventoryEvent;
import me.mrsandking.github.randomlootchest.inventory.Action;

public class CloseAction implements Action {

    @Override
    public void performAction(RLCClickInventoryEvent event) {
        event.getPlayer().closeInventory();
    }
}