package me.dreamdevs.randomlootchest.menus;

import me.dreamdevs.randomlootchest.RandomLootChestMain;
import me.dreamdevs.randomlootchest.api.Language;
import me.dreamdevs.randomlootchest.api.events.ItemClickEvent;
import me.dreamdevs.randomlootchest.api.inventory.BookItemMenu;
import me.dreamdevs.randomlootchest.api.inventory.buttons.MenuItem;
import me.dreamdevs.randomlootchest.api.objects.IChestGame;
import me.dreamdevs.randomlootchest.api.utils.ColourUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.stream.Collectors;

public class ChestMenu extends BookItemMenu {

	public ChestMenu() {
		super(Language.MENU_CHESTS_TITLE.toString(), buildItems(), true, false);
	}

	private static List<MenuItem> buildItems() {
		return RandomLootChestMain.getInstance().getChestsManager().getChests().values()
				.stream()
				.map(EditChest::new)
				.collect(Collectors.toList());
	}

	private static class EditChest extends MenuItem {

		private final IChestGame chestGame;

		public EditChest(IChestGame chestGame) {
			super(chestGame.getTitle(), new ItemStack(Material.CHEST), ColourUtil.colouredLore("&6Click to edit this chest.").toArray(String[]::new));
			this.chestGame = chestGame;
		}

		@Override
		public void onItemClick(ItemClickEvent event) {
			event.setWillClose(true);

			// Go to new menu
			Bukkit.getScheduler().runTaskLater(RandomLootChestMain.getInstance(), () -> {
				new ChestEditMenu(chestGame).open(event.getPlayer());
			}, 4L);
		}
	}
}