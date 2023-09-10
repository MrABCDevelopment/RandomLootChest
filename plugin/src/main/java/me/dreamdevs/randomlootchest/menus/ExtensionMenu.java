package me.dreamdevs.randomlootchest.menus;

import me.dreamdevs.randomlootchest.api.inventory.BookItemMenu;
import me.dreamdevs.randomlootchest.api.inventory.buttons.MenuItem;
import me.dreamdevs.randomlootchest.RandomLootChestMain;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ExtensionMenu extends BookItemMenu {

	public ExtensionMenu() {
		super(RandomLootChestMain.getInstance().getMessagesManager().getMessage("extensions-menu-title"),
				buildItems(), true, false);
	}

	private static List<MenuItem> buildItems() {
		return RandomLootChestMain.getInstance().getExtensionManager().getExtensions().stream().map(extension -> {
			MenuItem menuItem = new MenuItem(extension.getDescription().getExtensionName(),
					new ItemStack(extension.getDescription().getExtensionMaterial()));
			menuItem.setLore(Arrays.asList((extension.isEnabled()) ? ChatColor.GREEN+"Enabled" : ChatColor.RED+"Disabled"));
			return menuItem;
		}).collect(Collectors.toList());
	}

}