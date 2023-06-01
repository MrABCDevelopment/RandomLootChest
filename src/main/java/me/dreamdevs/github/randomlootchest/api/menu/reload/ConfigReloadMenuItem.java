package me.dreamdevs.github.randomlootchest.api.menu.reload;

import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.api.events.ClickInventoryEvent;
import me.dreamdevs.github.randomlootchest.api.menu.MenuItem;
import me.dreamdevs.github.randomlootchest.api.objects.WandItem;
import me.dreamdevs.github.randomlootchest.utils.Settings;
import org.bukkit.Material;

import java.util.ArrayList;

public class ConfigReloadMenuItem extends MenuItem {

    public ConfigReloadMenuItem() {
        super(Material.REPEATER, RandomLootChestMain.getInstance().getMessagesManager().getMessage("chest-menu-reload-config-reload-name"), new ArrayList<>());
    }

    @Override
    public void performAction(ClickInventoryEvent event) {
        event.getPlayer().closeInventory();
        RandomLootChestMain.getInstance().getConfigManager().reload("config.yml");
        Settings.loadVars();
        WandItem.loadVars();
        event.getPlayer().sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessage("chest-command-reload-config"));
    }
}