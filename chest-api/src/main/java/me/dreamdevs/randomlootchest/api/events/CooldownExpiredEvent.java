package me.dreamdevs.randomlootchest.api.events;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

@Getter
public class CooldownExpiredEvent extends Event {

    private final UUID uuid;
    private final Location location;
    private @Getter static final HandlerList handlerList = new HandlerList();

    public CooldownExpiredEvent(UUID uuid, Location location) {
        this.uuid = uuid;
        this.location = location;
    }

    public OfflinePlayer getPlayer() {
        return Bukkit.getOfflinePlayer(uuid);
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}