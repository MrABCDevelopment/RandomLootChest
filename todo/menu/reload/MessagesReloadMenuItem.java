package menu.reload;

import menu.ClickInventoryEvent;
import menu.MenuItem;
import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import org.bukkit.Material;

import java.util.ArrayList;

public class MessagesReloadMenuItem extends MenuItem {

    public MessagesReloadMenuItem() {
        super(Material.PAPER, RandomLootChestMain.getInstance().getMessagesManager().getMessage("chest-menu-reload-messages-reload-name"), new ArrayList<>());
    }

    @Override
    public void performAction(ClickInventoryEvent event) {
        event.getPlayer().closeInventory();
        RandomLootChestMain.getInstance().getConfigManager().reload("messages.yml");
        RandomLootChestMain.getInstance().getMessagesManager().load(RandomLootChestMain.getInstance());
        event.getPlayer().sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessage("chest-command-reload-messages"));
    }
}