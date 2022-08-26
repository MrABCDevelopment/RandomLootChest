package me.dreamdevs.github.randomlootchest.managers;

import me.dreamdevs.github.randomlootchest.api.events.OpenInventoryEvent;
import me.dreamdevs.github.randomlootchest.api.inventory.GItem;
import me.dreamdevs.github.randomlootchest.api.inventory.GUI;
import me.dreamdevs.github.randomlootchest.api.inventory.GUISize;
import me.dreamdevs.github.randomlootchest.objects.RandomItem;
import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.objects.ChestGame;
import me.dreamdevs.github.randomlootchest.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GameManager {

    private RandomLootChestMain plugin;

    public GameManager(RandomLootChestMain plugin) {
        this.plugin = plugin;
    }

    /**
     * Since update v1.5.0, there's a little bit different items randomizing.
     */

    public void openChest(Player player, String type) {
        ChestGame chestGame = RandomLootChestMain.getInstance().getChestsManager().getChests().get(type);
        GUI gui = new GUI(chestGame.getTitle(), GUISize.THREE_ROWS, false);

        for(int x = 0; x<chestGame.getMaxItems();) {
            int rI = Util.getRandom().nextInt(chestGame.getItems().size());
            RandomItem randomItem = chestGame.getItems().get(rI);
            if(Util.chance(randomItem.getChance())) {
                int max = chestGame.getMaxItemsInTheSameType();
                if(max > 0) {
                    if(gui.getItemStacks().length == 0) {
                        gui.setItem(Util.randomSlot(gui.getSize()), new GItem(randomItem.getItemStack()));
                        x++;
                        continue;
                    }
                    for(ItemStack itemStack : gui.getItemStacks()) {
                        if(randomItem.getItemStack().getType() != itemStack.getType()) {
                            gui.setItem(Util.randomSlot(gui.getSize()), new GItem(randomItem.getItemStack()));
                            x++;
                        }
                    }
                } else {
                    gui.setItem(Util.randomSlot(gui.getSize()), new GItem(randomItem.getItemStack()));
                    x++;
                }
            }
        }
        gui.openGUI(player);

        OpenInventoryEvent rlcOpenInventoryEvent = new OpenInventoryEvent(player, chestGame, gui);
        Bukkit.getPluginManager().callEvent(rlcOpenInventoryEvent);
    }

}