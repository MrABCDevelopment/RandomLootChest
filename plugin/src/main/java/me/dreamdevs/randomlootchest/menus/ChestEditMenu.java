package me.dreamdevs.randomlootchest.menus;

import me.dreamdevs.randomlootchest.api.inventory.ItemMenu;
import me.dreamdevs.randomlootchest.api.inventory.buttons.ActionMenuItem;
import me.dreamdevs.randomlootchest.api.objects.IChestGame;
import me.dreamdevs.randomlootchest.utils.ColourUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ChestEditMenu extends ItemMenu {

	public ChestEditMenu(IChestGame chestGame) {
		super(chestGame.getTitle(), Size.THREE_LINE);

		setItem(10, new ActionMenuItem(ColourUtil.colorize("&aCooldown: "+chestGame.getTime()+"s"), event -> {
			ItemMeta itemMeta = event.getClickedItem().getItemMeta();

			if (event.getClicktype().isLeftClick()) {
				chestGame.setTime(chestGame.getTime()+1);
			} else if (event.getClicktype().isRightClick()) {
				chestGame.setTime(chestGame.getTime()-1);
			}
			itemMeta.setDisplayName(ColourUtil.colorize("&aCooldown: "+chestGame.getTime()+"s"));
			event.getClickedItem().setItemMeta(itemMeta);

			event.setWillUpdate(true);
		}, new ItemStack(Material.CARROT), ColourUtil.colouredLore("&eLeft-click to add 1 second", "&eRight-click to remove 1 second").toArray(String[]::new)));
	}

}