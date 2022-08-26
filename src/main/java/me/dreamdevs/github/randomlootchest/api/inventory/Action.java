package me.dreamdevs.github.randomlootchest.api.inventory;

import me.dreamdevs.github.randomlootchest.api.events.ClickInventoryEvent;

public interface Action {

    void performAction(ClickInventoryEvent event);

}