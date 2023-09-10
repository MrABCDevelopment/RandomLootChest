package me.dreamdevs.randomlootchest.api.events;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

@Getter
public class CombatEndPlayerEvent extends Event {

    private final UUID uuid;
    private @Getter static final HandlerList handlerList = new HandlerList();

    public CombatEndPlayerEvent(UUID uuid) {
        this.uuid = uuid;
    }

    public OfflinePlayer getPlayer() {
        return Bukkit.getOfflinePlayer(uuid);
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}