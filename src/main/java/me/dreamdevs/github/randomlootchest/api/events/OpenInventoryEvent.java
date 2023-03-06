package me.dreamdevs.github.randomlootchest.api.events;

import lombok.Getter;
import me.dreamdevs.github.randomlootchest.api.inventory.GUI;
import me.dreamdevs.github.randomlootchest.api.objects.ChestGame;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class OpenInventoryEvent extends Event implements Cancellable {

    private static final HandlerList list = new HandlerList();

    private Player player;
    private GUI gui;
    private ChestGame chestGame;
    private boolean cancelled;

    public OpenInventoryEvent(Player player, ChestGame chestGame, GUI gui) {
        this.player = player;
        this.chestGame = chestGame;
        this.gui = gui;
        this.cancelled = false;
    }

    public static HandlerList getHandlerList() {
        return list;
    }

    @Override
    public HandlerList getHandlers() {
        return list;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}