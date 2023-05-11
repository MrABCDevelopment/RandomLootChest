package me.dreamdevs.github.randomlootchest.api.events;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

@Getter
public class CombatStartPlayerEvent extends Event {

    private UUID uuid;
    private static final HandlerList list = new HandlerList();

    public CombatStartPlayerEvent(UUID uuid) {
        this.uuid = uuid;
    }

    public OfflinePlayer getPlayer() {
        return Bukkit.getOfflinePlayer(uuid);
    }

    @Override
    public HandlerList getHandlers() {
        return list;
    }
}