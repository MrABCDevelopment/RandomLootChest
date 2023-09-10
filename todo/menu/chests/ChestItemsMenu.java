package menu.chests;

import menu.BookMenu;
import menu.NormalMenuItem;
import me.dreamdevs.github.randomlootchest.api.objects.ChestGame;
import me.dreamdevs.github.randomlootchest.api.objects.RandomItem;
import me.dreamdevs.github.randomlootchest.RandomLootChestMain;

import java.util.ArrayList;
import java.util.List;

public class ChestItemsMenu extends BookMenu {

    private ChestGame chestGame;

    public ChestItemsMenu(ChestGame chestGame) {
        super(chestGame.getTitle() + " Items");
        this.chestGame = chestGame;
    }

    @Override
    public void setItems() {
        super.setupMenuNavigation();

        List<RandomItem> randomItems = new ArrayList<>(chestGame.getItems());

        for(int i = 0; i < 45; i++) {
            index = 45 * page + i;
            if (index >= randomItems.size())
                break;
            if (randomItems.get(index) != null) {
                NormalMenuItem normalMenuItem = new NormalMenuItem(randomItems.get(index).getItemStack().clone());
                addItem(normalMenuItem);
                getInventory().addItem(randomItems.get(index).getItemStack());
            }
        }
    }
}