package me.mrsandking.github.randomlootchest.manager;

import me.mrsandking.github.randomlootchest.objects.RandomItem;
import me.mrsandking.github.randomlootchest.RandomLootChestMain;
import me.mrsandking.github.randomlootchest.objects.ChestGame;
import me.mrsandking.github.randomlootchest.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class GameManager {

    private RandomLootChestMain plugin;

    public GameManager(RandomLootChestMain plugin) {
        this.plugin = plugin;
    }

    public void openChest(Player player, String type) {
        ChestGame chestGame = RandomLootChestMain.getInstance().getChestsManager().getChests().get(type);
        Inventory inventory = Bukkit.createInventory(null, 27, chestGame.getTitle());
        int counter = 0;
        for(RandomItem randomItem : chestGame.getItems()) {
            if(counter == chestGame.getMaxItems())
                continue;
            if(Util.chance(randomItem.getChance())) {
                int max = chestGame.getMaxItemsInTheSameType();
                if(max > 0) {
                    int i = 0;
                    for(int x = 0; x<inventory.getSize(); x++) {
                        if(inventory.getItem(x) != null && inventory.getItem(x).getType() == randomItem.getItemStack().getType() && i<max) {
                            i++;
                        }
                    }
                    if(i<max) {
                        inventory.setItem(Util.randomSlot(inventory.getSize()), randomItem.getItemStack());
                        counter++;
                    }
                } else {
                    inventory.setItem(Util.randomSlot(inventory.getSize()), randomItem.getItemStack());
                    counter++;
                }
            }
        }
        player.openInventory(inventory);
    }

}