package menu.reload;

import menu.BasicMenu;
import menu.CloseMenuItem;
import menu.MenuSize;
import menu.StaticMenuItem;
import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import org.bukkit.entity.Player;

public class ChestReloadMenu extends BasicMenu {

    public ChestReloadMenu(Player player) {
        super(RandomLootChestMain.getInstance().getMessagesManager().getMessage("chest-menu-reload-title"), MenuSize.THREE_ROWS, true);

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