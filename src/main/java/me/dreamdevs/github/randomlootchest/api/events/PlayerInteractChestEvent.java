package me.dreamdevs.github.randomlootchest.api.events;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class PlayerInteractChestEvent extends Event {

    private @Getter static final HandlerList handlerList = new HandlerList();

    private final Player player;
    private final Location chestLocation;

    public PlayerInteractChestEvent(Player player, Location chestLocation) {
        this.player = player;
        this.chestLocation = chestLocation;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}