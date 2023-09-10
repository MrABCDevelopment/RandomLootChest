package menu.creator;

import menu.BasicMenu;
import menu.CloseMenuItem;
import menu.MenuSize;
import menu.StaticMenuItem;
import me.dreamdevs.github.randomlootchest.api.objects.ChestGame;

public class ChestCreatorMenu extends BasicMenu {

    public ChestCreatorMenu(ChestGame chestGame) {
        super("Create chest: "+chestGame.getId(), MenuSize.TWO_ROWS, true);
        fill(new StaticMenuItem());

        setItem(17, new CloseMenuItem());
    }
}