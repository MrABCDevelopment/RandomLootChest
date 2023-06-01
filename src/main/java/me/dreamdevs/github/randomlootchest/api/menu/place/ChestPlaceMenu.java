package me.dreamdevs.github.randomlootchest.api.menu.place;

import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.api.menu.Menu;
import me.dreamdevs.github.randomlootchest.api.menu.MenuSize;
import me.dreamdevs.github.randomlootchest.api.objects.ChestGame;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ChestPlaceMenu extends Menu {

    public ChestPlaceMenu(Player player, Location location) {
        super(RandomLootChestMain.getInstance().getMessagesManager().getMessage("placing-menu-title"), MenuSize.sizeOf(RandomLootChestMain.getInstance().getChestsManager().getChests().size()));

        for(ChestGame chestGame : RandomLootChestMain.getInstance().getChestsManager().getChests().values())
            addItem(new ChestToPlaceMenuItem(chestGame, location));

        open(player);
    }

}