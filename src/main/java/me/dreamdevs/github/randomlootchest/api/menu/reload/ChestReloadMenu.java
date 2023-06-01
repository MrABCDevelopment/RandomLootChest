package me.dreamdevs.github.randomlootchest.api.menu.reload;

import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.api.menu.CloseMenuItem;
import me.dreamdevs.github.randomlootchest.api.menu.Menu;
import me.dreamdevs.github.randomlootchest.api.menu.MenuSize;
import me.dreamdevs.github.randomlootchest.api.menu.StaticMenuItem;
import org.bukkit.entity.Player;

public class ChestReloadMenu extends Menu {

    public ChestReloadMenu(Player player) {
        super(RandomLootChestMain.getInstance().getMessagesManager().getMessage("chest-menu-reload-title"), MenuSize.THREE_ROWS);

        fill(new StaticMenuItem());

        setItem(11, new ItemsReloadMenuItem());
        setItem(12, new ConfigReloadMenuItem());
        setItem(13, new ChestsReloadMenuItem());
        setItem(14, new MessagesReloadMenuItem());
        setItem(15, new ReloadAllMenuItem());

        setItem(26, new CloseMenuItem());

        open(player);
    }

}