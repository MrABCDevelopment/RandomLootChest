package me.dreamdevs.github.randomlootchest.api.menu;

import me.dreamdevs.github.randomlootchest.api.events.ClickInventoryEvent;
import org.bukkit.Material;

public class CloseMenuItem extends MenuItem
{

    public CloseMenuItem() {
        super(Material.BARRIER, "&cClose");
    }

    @Override
    public void performAction(ClickInventoryEvent event) {
        event.getPlayer().closeInventory();
    }
}