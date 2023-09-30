package me.dreamdevs.randomlootchest.menus;

import me.dreamdevs.randomlootchest.RandomLootChestMain;
import me.dreamdevs.randomlootchest.api.Language;
import me.dreamdevs.randomlootchest.api.events.ItemClickEvent;
import me.dreamdevs.randomlootchest.api.inventory.BookItemMenu;
import me.dreamdevs.randomlootchest.api.inventory.buttons.MenuItem;
import me.dreamdevs.randomlootchest.objects.RandomItem;

import java.util.List;
import java.util.stream.Collectors;

public class ItemsMenu extends BookItemMenu {

	public ItemsMenu() {
		super(Language.MENU_ITEMS_TITLE.toString(), buildItems(), true, false);
	}

	private static List<MenuItem> buildItems() {
		return RandomLootChestMain.getInstance().getItemsManager().getItems().values().stream()
				.map(RandomItemChestItem::new)
				.collect(Collectors.toList());
	}

	private static class RandomItemChestItem extends MenuItem {

		public RandomItemChestItem(RandomItem randomItem) {
			super(randomItem.getDisplayName(), randomItem.getItemStack(), randomItem.getLore().toArray(String[]::new));
		}

		@Override
		public void onItemClick(ItemClickEvent event) {

		}
	}
}