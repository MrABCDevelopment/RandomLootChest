package me.dreamdevs.treasurechestextension.listeners;

import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.utils.Util;
import me.dreamdevs.treasurechestextension.ExtensionMain;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class PlayerBreakListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void breakBlockEvent(BlockBreakEvent event) {
        if(event.isCancelled())
            return;
        if(Util.chance(ExtensionMain.CHANCE)) {
            RandomLootChestMain.getInstance().getGameManager().openChest(event.getPlayer(), "treasurechest");
        }
    }

}