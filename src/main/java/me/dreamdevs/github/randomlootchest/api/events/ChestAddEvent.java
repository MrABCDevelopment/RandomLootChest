package me.dreamdevs.github.randomlootchest.api.events;

import lombok.Getter;
import me.dreamdevs.github.randomlootchest.api.objects.ChestGame;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

@Getter
public class ChestAddEvent extends Event {

    private final UUID uuid;
    private final Location location;
    private final ChestGame chestGame;
    private static final HandlerList list = new HandlerList();

    public ChestAddEvent(UUID uuid, ChestGame chestGame, Location location) {
        this.uuid = uuid;
        this.chestGame = chestGame;
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