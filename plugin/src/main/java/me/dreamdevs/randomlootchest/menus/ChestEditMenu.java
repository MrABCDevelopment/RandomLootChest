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
		}, new ItemStack(Material.STICK), ColourUtil.colouredLore("&eLeft-click to add 1 item", "&eRight-click to remove 1 item").toArray(String[]::new)));

		setItem(12, new ActionMenuItem(ColourUtil.colorize("&aMax Items In The Same Type: %AMOUNT%")
				.replace("%AMOUNT", String.valueOf(chestGame.getMaxItemsInTheSameType())), event -> {

			if (event.getClicktype().isLeftClick()) {
				chestGame.setMaxItemsInTheSameType(chestGame.getMaxItemsInTheSameType()+1);
			} else if (event.getClicktype().isRightClick()) {
				chestGame.setMaxItemsInTheSameType(chestGame.getMaxItemsInTheSameType()-1);
			}

			event.setWillUpdate(true);
		}, new ItemStack(Material.LEATHER), ColourUtil.colouredLore("&eLeft-click to add 1 item", "&eRight-click to remove 1 item").toArray(String[]::new)));

		setItem(13, new ActionMenuItem(ColourUtil.colorize("&aExp: %AMOUNT%")
				.replace("%AMOUNT", String.valueOf(chestGame.getExp())), event -> {

			if (event.getClicktype().isLeftClick()) {
				chestGame.setExp(chestGame.getExp()+1);
			} else if (event.getClicktype().isRightClick()) {
				chestGame.setExp(chestGame.getExp()-1);
			}

			event.setWillUpdate(true);
		}, new ItemStack(Material.EXPERIENCE_BOTTLE), ColourUtil.colouredLore("&eLeft-click to add 1 exp", "&eRight-click to remove 1 exp").toArray(String[]::new)));

		setItem(14, new ActionMenuItem(ColourUtil.colorize("&aUse Particles: %STATUS%")
				.replace("%STATUS", String.valueOf(chestGame.useParticles())), event -> {

			chestGame.setUseParticles(!chestGame.useParticles());

			event.setWillUpdate(true);
		}, new ItemStack(Material.HEART_OF_THE_SEA), ColourUtil.colouredLore("&eClick to change status.").toArray(String[]::new)));

		setItem(15, new ActionMenuItem(ColourUtil.colorize("&aParticles Amount: %AMOUNT%")
				.replace("%AMOUNT", String.valueOf(chestGame.getParticleAmount())), event -> {

			if (event.getClicktype().isLeftClick()) {
				chestGame.setParticlesAmount(chestGame.getParticleAmount()+1);
			} else if (event.getClicktype().isRightClick()) {
				chestGame.setParticlesAmount(chestGame.getParticleAmount()-1);
			}

			event.setWillUpdate(true);
		}, new ItemStack(Material.GUNPOWDER), ColourUtil.colouredLore("&eLeft-click to add 1", "&eRight-click to remove 1").toArray(String[]::new)));
	}

}