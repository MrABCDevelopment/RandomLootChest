package me.dreamdevs.randomlootchest.api.inventory.buttons;

import org.bukkit.inventory.ItemStack;

public class FreeMenuItem extends MenuItem {

	public FreeMenuItem(ItemStack itemStack) {
		super(itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName() ? itemStack.getItemMeta().getDisplayName() : null,
				itemStack, itemStack.hasItemMeta() && itemStack.getItemMeta().hasLore()
						? itemStack.getItemMeta().getLore().toArray(String[]::new) : new String[0]);
	}
}