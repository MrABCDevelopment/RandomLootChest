package me.dreamdevs.randomlootchest.menus;

import me.dreamdevs.randomlootchest.api.inventory.ItemMenu;
import me.dreamdevs.randomlootchest.api.inventory.buttons.MenuItem;
import me.dreamdevs.randomlootchest.RandomLootChestMain;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ReloadMenu extends ItemMenu {

	public ReloadMenu() {
		super(RandomLootChestMain.getInstance().getMessagesManager().getMessage("chest-menu-reload-title"), Size.THREE_LINE);

	}

	private static class ReloadItem extends MenuItem {

		public ReloadItem() {
			super(RandomLootChestMain.getInstance().getMessagesManager().getMessage("chest-menu-reload-chests-reload-name"),
					new ItemStack(Material.CHEST));
		}

	}
}