package me.dreamdevs.randomlootchest.api.inventory.buttons;

import me.dreamdevs.randomlootchest.api.events.ItemClickEvent;
import me.dreamdevs.randomlootchest.api.inventory.handlers.ItemClickHandler;
import org.bukkit.inventory.ItemStack;

public class ActionMenuItem extends MenuItem
{
	private ItemClickHandler handler;

	public ActionMenuItem(String displayName, ItemClickHandler handler, ItemStack icon, String... lore)
	{
		super(displayName, icon, lore);
		this.handler = handler;
	}

	@Override
	public void onItemClick(ItemClickEvent event)
	{
		handler.onItemClick(event);
	}

	public ItemClickHandler getHandler()
	{
		return this.handler;
	}

	public void setHandler(ItemClickHandler handler)
	{
		this.handler = handler;
	}
}
