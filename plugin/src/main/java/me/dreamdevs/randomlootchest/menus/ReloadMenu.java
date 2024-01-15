package me.dreamdevs.randomlootchest.menus;

import me.dreamdevs.randomlootchest.api.Config;
import me.dreamdevs.randomlootchest.api.Language;
import me.dreamdevs.randomlootchest.api.inventory.ItemMenu;
import me.dreamdevs.randomlootchest.RandomLootChestMain;
import me.dreamdevs.randomlootchest.api.inventory.buttons.ActionMenuItem;
import me.dreamdevs.randomlootchest.api.inventory.buttons.MenuItem;
import me.dreamdevs.randomlootchest.objects.WandItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ReloadMenu extends ItemMenu {

	public ReloadMenu() {
		super(Language.MENU_CHEST_RELOAD_TITLE.toString(), Size.THREE_LINE);

		// Setting up items, then add them into gui...
		MenuItem reloadAllItem = new ActionMenuItem(Language.MENU_CHEST_RELOAD_ALL.toString(), event -> {
			event.setWillClose(true);
			RandomLootChestMain.getInstance().loadConfig();
			RandomLootChestMain.getInstance().getItemsManager().save();
			RandomLootChestMain.getInstance().getItemsManager().load(RandomLootChestMain.getInstance());
			Language.reloadLanguage();
			RandomLootChestMain.getInstance().loadLanguage();
			RandomLootChestMain.getInstance().getChestsManager().load(RandomLootChestMain.getInstance());
			event.getPlayer().sendMessage(Language.COMMAND_RELOAD_FILES.toString());
		}, new ItemStack(Material.BOOK));

		MenuItem reloadMessagesItem = new ActionMenuItem(Language.MENU_CHEST_RELOAD_MESSAGES.toString(), event -> {
			event.setWillClose(true);
			Language.reloadLanguage();
			RandomLootChestMain.getInstance().loadLanguage();
			event.getPlayer().sendMessage(Language.COMMAND_RELOAD_MESSAGES.toString());
		}, new ItemStack(Material.PAPER));

		MenuItem reloadChestsItem = new ActionMenuItem(Language.MENU_CHEST_RELOAD_ALL_CHESTS.toString(), event -> {
			event.setWillClose(true);
			RandomLootChestMain.getInstance().getChestsManager().load(RandomLootChestMain.getInstance());
			event.getPlayer().sendMessage(Language.COMMAND_RELOAD_CHESTS.toString());
		}, new ItemStack(Material.CHEST));

		MenuItem reloadConfigItem = new ActionMenuItem(Language.MENU_CHEST_RELOAD_CONFIG.toString(), event -> {
			event.setWillClose(true);
			Config.reloadFile();
			RandomLootChestMain.getInstance().loadConfig();
			WandItem.loadVars();
			event.getPlayer().sendMessage(Language.COMMAND_RELOAD_CONFIG.toString());
		}, new ItemStack(Material.REPEATER));

		MenuItem reloadItemsItem = new ActionMenuItem(Language.MENU_CHEST_RELOAD_ITEMS.toString(), event -> {
			RandomLootChestMain.getInstance().getItemsManager().save();
			RandomLootChestMain.getInstance().getItemsManager().load(RandomLootChestMain.getInstance());
			event.getPlayer().sendMessage(Language.COMMAND_RELOAD_ITEMS.toString());
		}, new ItemStack(Material.DIAMOND_SWORD));

		setItem(11, reloadItemsItem);
		setItem(12, reloadConfigItem);
		setItem(13, reloadChestsItem);
		setItem(14, reloadMessagesItem);
		setItem(15, reloadAllItem);
	}

}