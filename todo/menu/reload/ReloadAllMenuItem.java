package menu.reload;

import menu.ClickInventoryEvent;
import menu.MenuItem;
import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.utils.Settings;
import org.bukkit.Material;

import java.util.ArrayList;

public class ReloadAllMenuItem extends MenuItem
{

    public ReloadAllMenuItem() {
        super(Material.BOOK, RandomLootChestMain.getInstance().getMessagesManager().getMessage("chest-menu-reload-all-reload-name"), new ArrayList<>());
    }

    @Override
    public void performAction(ClickInventoryEvent event) {
        event.getPlayer().closeInventory();
        RandomLootChestMain.getInstance().getConfigManager().reload("config.yml");
        Settings.loadVars();
        RandomLootChestMain.getInstance().getConfigManager().reload("items.yml");
        RandomLootChestMain.getInstance().getItemsManager().load(RandomLootChestMain.getInstance());
        RandomLootChestMain.getInstance().getConfigManager().reload("messages.yml");
        RandomLootChestMain.getInstance().getMessagesManager().load(RandomLootChestMain.getInstance());
        RandomLootChestMain.getInstance().getChestsManager().load(RandomLootChestMain.getInstance());
        event.getPlayer().sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessages().get("chest-command-reload"));
    }
}