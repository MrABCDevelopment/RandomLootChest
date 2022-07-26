package me.mrsandking.github.randomlootchest.manager;

import me.mrsandking.github.randomlootchest.events.RLCOpenInventoryEvent;
import me.mrsandking.github.randomlootchest.inventory.GItem;
import me.mrsandking.github.randomlootchest.inventory.GUI;
import me.mrsandking.github.randomlootchest.inventory.GUISize;
import me.mrsandking.github.randomlootchest.objects.RandomItem;
import me.mrsandking.github.randomlootchest.RandomLootChestMain;
import me.mrsandking.github.randomlootchest.objects.ChestGame;
import me.mrsandking.github.randomlootchest.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class GameManager {

    private RandomLootChestMain plugin;

    public GameManager(RandomLootChestMain plugin) {
        this.plugin = plugin;
    }

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
        RLCOpenInventoryEvent rlcOpenInventoryEvent = new RLCOpenInventoryEvent(player, chestGame, gui);
        Bukkit.getPluginManager().callEvent(rlcOpenInventoryEvent);
    }

}