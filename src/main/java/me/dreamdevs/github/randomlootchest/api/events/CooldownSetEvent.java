package me.dreamdevs.github.randomlootchest.api.events;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

@Getter
public class CooldownSetEvent extends Event {

    private UUID uuid;
    private Location location;
    private int time;
    private static final HandlerList list = new HandlerList();

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
        return list;
    }
}