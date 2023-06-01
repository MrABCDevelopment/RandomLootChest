package me.dreamdevs.github.randomlootchest.managers;

import me.dreamdevs.github.randomlootchest.api.events.ChestOpenEvent;
import me.dreamdevs.github.randomlootchest.api.menu.*;
import me.dreamdevs.github.randomlootchest.api.objects.RandomItem;
import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.api.objects.ChestGame;
import me.dreamdevs.github.randomlootchest.utils.Settings;
import me.dreamdevs.github.randomlootchest.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class GameManager {

    public void openChest(final Player player, String type) {
        ChestGame chestGame = RandomLootChestMain.getInstance().getChestsManager().getChests().get(type);

        Menu menu = new Menu(chestGame.getTitle(), MenuSize.byOption(Settings.randomRows), false);

        int counter = 0;
        for(RandomItem randomItem : chestGame.getItems()) {
            if(counter == chestGame.getMaxItems())
                continue;
            if(Util.chance(randomItem.getChance())) {
                int max = chestGame.getMaxItemsInTheSameType();
                if(max > 0) {
                    int i = 0;
                    for(int x = 0; x<menu.getSize(); x++) {
                        if(menu.getInventory().getItem(x) != null && menu.getInventory().getItem(x).getType() == randomItem.getItemStack().getType() && i<max) {
                            i++;
                        }
                    }
                    if(i<max) {
                        menu.setItem(Util.randomSlot(menu.getSize()), new NormalMenuItem(randomItem.getItemStack()));
                        counter++;
                    }
                } else {
                    menu.setItem(Util.randomSlot(menu.getSize()), new NormalMenuItem(randomItem.getItemStack()));
                    counter++;
                }
            }
        }
        menu.open(player);

        ChestOpenEvent event = new ChestOpenEvent(player, chestGame);
        Bukkit.getPluginManager().callEvent(event);
    }

}