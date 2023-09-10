package me.dreamdevs.randomlootchest.api.events;

import lombok.Getter;
import me.dreamdevs.randomlootchest.api.objects.IChestGame;
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
    private final IChestGame chestGame;
    private static final HandlerList list = new HandlerList();

    public ChestAddEvent(UUID uuid, IChestGame chestGame, Location location) {
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