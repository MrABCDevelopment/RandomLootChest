package me.dreamdevs.github.randomlootchest.api.events;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

@Getter
public class CooldownExpiredEvent extends Event {

    private UUID uuid;
    private Location location;
    private static final HandlerList list = new HandlerList();

    public CooldownExpiredEvent(UUID uuid, Location location) {
        this.uuid = uuid;
        this.location = location;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    @Override
    public HandlerList getHandlers() {
        return list;
    }
}