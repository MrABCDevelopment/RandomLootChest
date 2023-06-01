package me.dreamdevs.github.randomlootchest.api.menu;

import me.dreamdevs.github.randomlootchest.api.events.ClickInventoryEvent;
import org.bukkit.Material;

public class StaticMenuItem extends MenuItem {

    public StaticMenuItem() {
        super(Material.WHITE_STAINED_GLASS_PANE, " ");
    }

    @Override
    public void performAction(ClickInventoryEvent event) {
        // DO NOTHING
    }

}