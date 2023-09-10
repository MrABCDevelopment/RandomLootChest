package menu.items;

import menu.BookMenu;
import me.dreamdevs.github.randomlootchest.api.objects.RandomItem;
import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import menu.NormalMenuItem;


import java.util.ArrayList;
import java.util.List;

public class ItemsMenu extends BookMenu
{

    public ItemsMenu() {
        super(RandomLootChestMain.getInstance().getMessagesManager().getMessage("items-menu-title"));
    }

    @Override
    public void setItems() {
        super.setupMenuNavigation();

        List<RandomItem> randomItems = new ArrayList<>(RandomLootChestMain.getInstance().getItemsManager().getItems().values());

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