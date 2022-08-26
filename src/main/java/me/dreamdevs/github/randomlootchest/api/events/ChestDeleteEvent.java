package me.dreamdevs.github.randomlootchest.api.events;

import lombok.Getter;
import me.dreamdevs.github.randomlootchest.objects.ChestGame;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

@Getter
public class ChestDeleteEvent extends Event {

    private UUID uuid;
    private ChestGame chestGame;
    private static final HandlerList list = new HandlerList();

    public ChestDeleteEvent(UUID uuid, ChestGame chestGame) {
        this.uuid = uuid;
        this.chestGame = chestGame;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    @Override
    public HandlerList getHandlers() {
        return list;
    }
}