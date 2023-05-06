package me.dreamdevs.github.randomlootchest.managers;

import me.dreamdevs.github.randomlootchest.api.events.OpenInventoryEvent;
import me.dreamdevs.github.randomlootchest.api.inventory.GItem;
import me.dreamdevs.github.randomlootchest.api.inventory.GUI;
import me.dreamdevs.github.randomlootchest.api.inventory.GUISize;
import me.dreamdevs.github.randomlootchest.api.objects.RandomItem;
import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.api.objects.ChestGame;
import me.dreamdevs.github.randomlootchest.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class GameManager {

    public void openChest(Player player, String type) {
        ChestGame chestGame = RandomLootChestMain.getInstance().getChestsManager().getChests().get(type);
        GUI gui = new GUI(chestGame.getTitle(), GUISize.THREE_ROWS, false);

        int counter = 0;
        for(RandomItem randomItem : chestGame.getItems()) {
            if(counter == chestGame.getMaxItems())
                continue;
            if(Util.chance(randomItem.getChance())) {
                int max = chestGame.getMaxItemsInTheSameType();
                if(max > 0) {
                    int i = 0;
                    for(int x = 0; x<gui.getSize(); x++) {
                        if(gui.getInventory().getItem(x) != null && gui.getInventory().getItem(x).getType() == randomItem.getItemStack().getType() && i<max) {
                            i++;
                        }
                    }
                    if(i<max) {
                        gui.setItem(Util.randomSlot(gui.getSize()), new GItem(randomItem.getItemStack()));
                        counter++;
                    }
                } else {
                    gui.setItem(Util.randomSlot(gui.getSize()), new GItem(randomItem.getItemStack()));
                    counter++;
                }
            }
        }
        gui.openGUI(player);

        OpenInventoryEvent rlcOpenInventoryEvent = new OpenInventoryEvent(player, chestGame, gui);
        Bukkit.getPluginManager().callEvent(rlcOpenInventoryEvent);
    }

}