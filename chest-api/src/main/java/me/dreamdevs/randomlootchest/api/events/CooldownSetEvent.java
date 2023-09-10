package me.dreamdevs.randomlootchest.api.events;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

@Getter
public class CooldownSetEvent extends Event {

    private final UUID uuid;
    private final Location location;
    private int time;
    private @Getter static final HandlerList handlerList = new HandlerList();

    public CooldownSetEvent(UUID uuid, Location location, int time) {
        this.uuid = uuid;
        this.location = location;
        this.time = time;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}