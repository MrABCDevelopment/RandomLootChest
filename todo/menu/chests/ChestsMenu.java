package menu.chests;

import menu.BookMenu;
import me.dreamdevs.github.randomlootchest.api.objects.ChestGame;
import me.dreamdevs.github.randomlootchest.RandomLootChestMain;

import java.util.ArrayList;
import java.util.List;

public class ChestsMenu extends BookMenu
{

    public ChestsMenu() {
        super(RandomLootChestMain.getInstance().getMessagesManager().getMessage("chests-menu-title"));
    }

    @Override
    public void setItems() {
        super.setupMenuNavigation();

        List<ChestGame> chestGames = new ArrayList<>(RandomLootChestMain.getInstance().getChestsManager().getChests().values());

        for(int i = 0; i < 45; i++) {
            index = 45 * page + i;
            if (index >= chestGames.size())
                break;
            if (chestGames.get(index) != null) {
                ChestGameMenuItem chestGameMenuItem = new ChestGameMenuItem(chestGames.get(index));
                addItem(chestGameMenuItem);
                getInventory().addItem(chestGameMenuItem.getItemStack());
            }
        }
    }
}