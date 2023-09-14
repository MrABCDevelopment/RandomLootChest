package me.dreamdevs.randomlootchest.menus;

import me.dreamdevs.randomlootchest.api.inventory.ItemMenu;
import me.dreamdevs.randomlootchest.RandomLootChestMain;
import me.dreamdevs.randomlootchest.api.inventory.buttons.ActionMenuItem;
import me.dreamdevs.randomlootchest.api.inventory.buttons.MenuItem;
import me.dreamdevs.randomlootchest.objects.WandItem;
import me.dreamdevs.randomlootchest.utils.Settings;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ReloadMenu extends ItemMenu {

	public ReloadMenu() {
		super(RandomLootChestMain.getInstance().getMessagesManager().getMessage("chest-menu-reload-title"), Size.THREE_LINE);

		// Setting up items, then add them into gui...
		MenuItem reloadAllItem = new ActionMenuItem(RandomLootChestMain.getInstance().getMessagesManager().getMessage("chest-menu-reload-all-reload-name"), event -> {
			event.setWillClose(true);
			RandomLootChestMain.getInstance().getConfigManager().reload("config.yml");
			Settings.loadVars();
			RandomLootChestMain.getInstance().getConfigManager().reload("items.yml");
			RandomLootChestMain.getInstance().getItemsManager().load(RandomLootChestMain.getInstance());
			RandomLootChestMain.getInstance().getConfigManager().reload("messages.yml");
			RandomLootChestMain.getInstance().getMessagesManager().load(RandomLootChestMain.getInstance());
			RandomLootChestMain.getInstance().getChestsManager().load(RandomLootChestMain.getInstance());
			event.getPlayer().sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessages().get("chest-command-reload"));
		}, new ItemStack(Material.BOOK));

		MenuItem reloadMessagesItem = new ActionMenuItem(RandomLootChestMain.getInstance().getMessagesManager().getMessage("chest-menu-reload-messages-reload-name"), event -> {
			event.setWillClose(true);
			RandomLootChestMain.getInstance().getConfigManager().reload("messages.yml");
			RandomLootChestMain.getInstance().getMessagesManager().load(RandomLootChestMain.getInstance());
			event.getPlayer().sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessage("chest-command-reload-messages"));
		}, new ItemStack(Material.PAPER));

		MenuItem reloadChestsItem = new ActionMenuItem(RandomLootChestMain.getInstance().getMessagesManager().getMessage("chest-menu-reload-chests-reload-name"), event -> {
			event.setWillClose(true);
			RandomLootChestMain.getInstance().getChestsManager().load(RandomLootChestMain.getInstance());
			event.getPlayer().sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessage("chest-command-reload-chests"));
		}, new ItemStack(Material.CHEST));

		MenuItem reloadConfigItem = new ActionMenuItem(RandomLootChestMain.getInstance().getMessagesManager().getMessage("chest-menu-reload-config-reload-name"), event -> {
			event.setWillClose(true);
			RandomLootChestMain.getInstance().getConfigManager().reload("config.yml");
			Settings.loadVars();
			WandItem.loadVars();
			event.getPlayer().sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessage("chest-command-reload-config"));
		}, new ItemStack(Material.REPEATER));

		MenuItem reloadItemsItem = new ActionMenuItem(RandomLootChestMain.getInstance().getMessagesManager().getMessage("chest-menu-reload-items-reload-name"), event -> {
			RandomLootChestMain.getInstance().getConfigManager().reload("items.yml");
			RandomLootChestMain.getInstance().getItemsManager().load(RandomLootChestMain.getInstance());
			event.getPlayer().sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessage("chest-command-reload-items"));
		}, new ItemStack(Material.DIAMOND_SWORD));

		setItem(11, reloadItemsItem);
		setItem(12, reloadConfigItem);
		setItem(13, reloadChestsItem);
		setItem(14, reloadMessagesItem);
		setItem(15, reloadAllItem);
	}

}