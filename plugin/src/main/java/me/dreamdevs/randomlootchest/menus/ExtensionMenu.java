package me.dreamdevs.randomlootchest.menus;

import me.dreamdevs.randomlootchest.api.Language;
import me.dreamdevs.randomlootchest.api.inventory.BookItemMenu;
import me.dreamdevs.randomlootchest.api.inventory.buttons.ActionMenuItem;
import me.dreamdevs.randomlootchest.api.inventory.buttons.MenuItem;
import me.dreamdevs.randomlootchest.RandomLootChestMain;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.stream.Collectors;

public class ExtensionMenu extends BookItemMenu {

	public ExtensionMenu() {
		super(Language.MENU_EXTENSIONS_TITLE.toString(), buildItems(), true, false);
	}

	private static List<MenuItem> buildItems() {
		return RandomLootChestMain.getInstance().getExtensionManager().getExtensions().stream().map(extension -> {
			MenuItem menuItem = new ActionMenuItem(extension.getDescription().getExtensionName(), event -> {
				event.setWillClose(true);
				extension.reloadConfig();
				extension.onExtensionDisable();
				extension.onExtensionEnable();
				event.getPlayer().sendMessage(Language.GENERAL_EXTENSION_CONFIG_RELOADED.toString().replace("%EXTENSION_NAME%", extension.getDescription().getExtensionName()));
			}, new ItemStack(extension.getDescription().getExtensionMaterial()));
			menuItem.setLore(List.of((extension.isEnabled()) ? ChatColor.GREEN + "Enabled" : ChatColor.RED + "Disabled"));
			return menuItem;
		}).collect(Collectors.toList());
	}

}