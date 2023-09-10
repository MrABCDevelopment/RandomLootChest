package menu.place;

import menu.BookMenu;
import me.dreamdevs.github.randomlootchest.api.objects.ChestGame;
import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ChestPlaceMenu extends BookMenu {

    private final Location location;

    public ChestPlaceMenu(Player player, Location location) {
        super(RandomLootChestMain.getInstance().getMessagesManager().getMessage("placing-menu-title"));
        this.location = location;

        super.open(player);
    }

    @Override
    public void setItems() {
        super.setupMenuNavigation();

        List<ChestGame> chestGames = new ArrayList<>(RandomLootChestMain.getInstance().getChestsManager().getChests().values());

        for(int i = 0; i < 45; i++) {
            index = 45 * page + i;
            if(index >= chestGames.size())
                break;
            if (chestGames.get(index) != null) {
                ChestToPlaceMenuItem chestToPlaceMenuItem = new ChestToPlaceMenuItem(chestGames.get(index), location);
                addItem(chestToPlaceMenuItem);

                getInventory().addItem(chestToPlaceMenuItem.getItemStack());
            }
        }

    }
}