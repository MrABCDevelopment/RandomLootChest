package me.mrsandking.github.randomlootchest.events;

import lombok.Getter;
import me.mrsandking.github.randomlootchest.inventory.GUI;
import me.mrsandking.github.randomlootchest.objects.ChestGame;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class RLCOpenInventoryEvent extends Event {

    private static final HandlerList list = new HandlerList();

    private Player player;
    private GUI gui;
    private ChestGame chestGame;

    public RLCOpenInventoryEvent(Player player, ChestGame chestGame, GUI gui) {
        this.player = player;
        this.chestGame = chestGame;
        this.gui = gui;
    }

    public static HandlerList getHandlerList() {
        return list;
    }

    @Override
    public HandlerList getHandlers() {
        return list;
    }
}