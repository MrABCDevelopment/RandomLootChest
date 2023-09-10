package me.dreamdevs.randomlootchest.api.inventory.buttons;

import me.dreamdevs.randomlootchest.api.events.ItemClickEvent;
import me.dreamdevs.randomlootchest.api.inventory.ItemMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ReturnMenuItem extends MenuItem {
	private ItemMenu returnMenu;

	public ReturnMenuItem(ItemMenu returnMenu) {
		super(ChatColor.RED+"<- Return Page", new ItemStack(Material.ARROW));
		this.returnMenu = returnMenu;
	}

	public void setReturnMenu(ItemMenu returnMenu) {
		this.returnMenu = returnMenu;
	}

	@Override
	public void onItemClick(ItemClickEvent event) {
		if (this.returnMenu != null) {
			this.returnMenu.open(event.getPlayer());
		} else {
			event.setWillClose(true);
		}
	}
}