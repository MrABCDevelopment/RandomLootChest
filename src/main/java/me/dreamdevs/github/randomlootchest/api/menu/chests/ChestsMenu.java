package me.dreamdevs.github.randomlootchest.api.menu.chests;

import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.api.menu.Menu;
import me.dreamdevs.github.randomlootchest.api.menu.MenuSize;
import me.dreamdevs.github.randomlootchest.api.objects.ChestGame;
import org.bukkit.entity.Player;

public class ChestsMenu extends Menu
{

    public ChestsMenu(Player player) {
        super(RandomLootChestMain.getInstance().getMessagesManager().getMessage("chests-menu-title"), MenuSize.sizeOf(RandomLootChestMain.getInstance().getChestsManager().getChests().size()));

        for(ChestGame chestGame : RandomLootChestMain.getInstance().getChestsManager().getChests().values())
            addItem(new ChestGameMenuItem(chestGame));

        open(player);
    }

}