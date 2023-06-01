package me.dreamdevs.github.randomlootchest.api.menu.chests;

import me.dreamdevs.github.randomlootchest.api.events.ClickInventoryEvent;
import me.dreamdevs.github.randomlootchest.api.menu.MenuItem;
import me.dreamdevs.github.randomlootchest.api.objects.ChestGame;
import me.dreamdevs.github.randomlootchest.utils.ColourUtil;
import me.dreamdevs.github.randomlootchest.utils.TimeUtil;
import org.bukkit.Material;

public class ChestGameMenuItem extends MenuItem
{

    public ChestGameMenuItem(ChestGame chestGame) {
        super(Material.CHEST, chestGame.getTitle(), ColourUtil.colouredLore(
                "&6» Cooldown: &e"+ TimeUtil.formattedTime(chestGame.getTime()),
                "&6» Max Items: &e"+chestGame.getMaxItems(),
                "&6» Max Items In The Same Type: &e"+chestGame.getMaxItemsInTheSameType(),
                "&6» Use Particle: &e"+(chestGame.isParticleUse() ? "on" : "off"),
                "&6» Particle Type: &e"+chestGame.getParticleType(),
                "&6» Particle Amount: &e"+chestGame.getParticleAmount(),
                "&6» Items (amount): &e"+chestGame.getItems().size()
        ));
    }

    @Override
    public void performAction(ClickInventoryEvent event) {
        
    }
}