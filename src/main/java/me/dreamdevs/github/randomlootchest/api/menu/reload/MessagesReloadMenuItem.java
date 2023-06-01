package me.dreamdevs.github.randomlootchest.api.menu.reload;

import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.api.events.ClickInventoryEvent;
import me.dreamdevs.github.randomlootchest.api.menu.MenuItem;
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