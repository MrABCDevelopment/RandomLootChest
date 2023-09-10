package menu.reload;

import menu.ClickInventoryEvent;
import menu.MenuItem;
import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import org.bukkit.Material;

import java.util.ArrayList;

public class ChestsReloadMenuItem extends MenuItem
{

    public ChestsReloadMenuItem() {
        super(Material.CHEST, RandomLootChestMain.getInstance().getMessagesManager().getMessage("chest-menu-reload-chests-reload-name"), new ArrayList<>());
    }

    @Override
    public void performAction(ClickInventoryEvent event) {
        event.getPlayer().closeInventory();
        RandomLootChestMain.getInstance().getChestsManager().load(RandomLootChestMain.getInstance());
        event.getPlayer().sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessage("chest-command-reload-chests"));
    }
}