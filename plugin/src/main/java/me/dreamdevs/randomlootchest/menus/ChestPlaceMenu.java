package me.dreamdevs.randomlootchest.menus;

import me.dreamdevs.randomlootchest.RandomLootChestMain;
import me.dreamdevs.randomlootchest.api.events.ItemClickEvent;
import me.dreamdevs.randomlootchest.api.inventory.BookItemMenu;
import me.dreamdevs.randomlootchest.api.inventory.buttons.MenuItem;
import me.dreamdevs.randomlootchest.api.inventory.buttons.NextMenuItem;
import me.dreamdevs.randomlootchest.api.inventory.buttons.ReturnMenuItem;
import me.dreamdevs.randomlootchest.api.objects.IChestGame;
import me.dreamdevs.randomlootchest.utils.Util;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.stream.Collectors;

public class ChestPlaceMenu extends BookItemMenu {

	public ChestPlaceMenu(Location location) {
		super(RandomLootChestMain.getInstance().getMessagesManager().getMessage("placing-menu-title"),
				buildItems(location), true, false);
	}

	private static List<MenuItem> buildItems(Location location) {
		return RandomLootChestMain.getInstance().getChestsManager().getChests().values().stream().map(chestGame -> new PlaceChest(chestGame, location)).collect(Collectors.toList());
	}

	private static class PlaceChest extends MenuItem {

		private final IChestGame chestGame;
		private final Location location;

		public PlaceChest(IChestGame iChestGame, Location location) {
			super(iChestGame.getTitle(), new ItemStack(Material.CHEST));
			this.chestGame = iChestGame;
			this.location = location;
		}

		@Override
		public void onItemClick(ItemClickEvent event) {
			event.getPlayer().closeInventory();
			RandomLootChestMain.getInstance().getLocationManager().addLocation(chestGame.getId(), location);
			RandomLootChestMain.getInstance().getLocationManager().save();
			event.getPlayer().sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessages().get("chest-place-on-map")
					.replace("%TYPE%", chestGame.getTitle()).replace("%LOCATION%", Util.getLocationString(location)));
		}
	}

}