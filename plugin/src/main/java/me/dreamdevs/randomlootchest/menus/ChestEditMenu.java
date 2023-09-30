package me.dreamdevs.randomlootchest.menus;

import me.dreamdevs.randomlootchest.api.inventory.ItemMenu;
import me.dreamdevs.randomlootchest.api.inventory.buttons.ActionMenuItem;
import me.dreamdevs.randomlootchest.api.objects.IChestGame;
import me.dreamdevs.randomlootchest.api.utils.ColourUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ChestEditMenu extends ItemMenu {

	public ChestEditMenu(IChestGame chestGame) {
		super(chestGame.getTitle(), Size.THREE_LINE);

		setItem(10, new ActionMenuItem(ColourUtil.colorize("&aCooldown: %TIME%s")
				.replace("%TIME%", String.valueOf(chestGame.getTime())), event -> {

			if (event.getClicktype().isLeftClick()) {
				chestGame.setTime(chestGame.getTime()+1);
			} else if (event.getClicktype().isRightClick()) {
				chestGame.setTime(chestGame.getTime()-1);
			}

			event.setWillUpdate(true);
		}, new ItemStack(Material.CARROT), ColourUtil.colouredLore("&eLeft-click to add 1 second", "&eRight-click to remove 1 second").toArray(String[]::new)));

		setItem(11, new ActionMenuItem(ColourUtil.colorize("&aMax Items: %AMOUNT%")
				.replace("%AMOUNT", String.valueOf(chestGame.getMaxItems())), event -> {

			if (event.getClicktype().isLeftClick()) {
				chestGame.setMaxItems(chestGame.getMaxItems()+1);
			} else if (event.getClicktype().isRightClick()) {
				chestGame.setMaxItems(chestGame.getMaxItems()-1);
			}

			event.setWillUpdate(true);
		}, new ItemStack(Material.STICK), ColourUtil.colouredLore("&eLeft-click to add 1 item", "&eRight-click to remove 1 remove").toArray(String[]::new)));
	}

}