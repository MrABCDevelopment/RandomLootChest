package me.dreamdevs.randomlootchest.api.inventory;

import lombok.Getter;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class ItemMenuHolder implements InventoryHolder {

	private final @Getter ItemMenu menu;
	private final @Getter Inventory inventory;

	public ItemMenuHolder(ItemMenu menu, Inventory inventory) {
		this.menu = menu;
		this.inventory = inventory;
	}

}