package me.dreamdevs.randomlootchest.api.events;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class ItemClickEvent
{
	private @Getter final Player player;
	private final @Getter ClickType clicktype;
	private boolean goBack = false;
	private boolean close = false;
	private boolean update = false;
	private final @Getter ItemStack clickedItem;

	public ItemClickEvent(Player player,ItemStack stack, ClickType type) {
		this.player = player;
		this.clickedItem = stack;
		this.clicktype = type;
	}

	public boolean willGoBack() {
		return goBack;
	}

	public void setWillGoBack(boolean goBack) {
		this.goBack = goBack;
		if (goBack) {
			close = false;
			update = false;
		}
	}

	public boolean willClose() {
		return close;
	}

	public void setWillClose(boolean close) {
		this.close = close;
		if (close) {
			goBack = false;
			update = false;
		}
	}

	public boolean willUpdate() {
		return update;
	}

	public void setWillUpdate(boolean update) {
		this.update = update;
		if (update) {
			goBack = false;
			close = false;
		}
	}
}