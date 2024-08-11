package me.dreamdevs.randomlootchest.api.inventory.handlers;

import me.dreamdevs.randomlootchest.api.event.inventory.ItemClickEvent;

public interface ItemClickHandler {
	void onItemClick(ItemClickEvent event);
}