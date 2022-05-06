package me.mrsandking.github.randomlootchest.events;

import lombok.Getter;
import me.mrsandking.github.randomlootchest.inventory.GUI;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class RLCOpenInventoryEvent extends Event {

    private static final HandlerList list = new HandlerList();

    private Player player;
    private GUI gui;

    public RLCOpenInventoryEvent(Player player, GUI gui) {
        this.player = player;
        this.gui = gui;
    }

    @Override
    public HandlerList getHandlers() {
        return list;
    }
}