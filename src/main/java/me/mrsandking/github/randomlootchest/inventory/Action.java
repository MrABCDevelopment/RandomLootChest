package me.mrsandking.github.randomlootchest.inventory;

import me.mrsandking.github.randomlootchest.events.RLCClickInventoryEvent;

public interface Action {

    void performAction(RLCClickInventoryEvent event);

}