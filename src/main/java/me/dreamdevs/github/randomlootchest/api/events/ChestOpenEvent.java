package me.dreamdevs.github.randomlootchest.api.events;

import lombok.Getter;
import me.dreamdevs.github.randomlootchest.api.objects.ChestGame;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class ChestOpenEvent extends Event {

    private final HandlerList handlerList = new HandlerList();

    private final Player player;
    private final ChestGame chestGame;

    public ChestOpenEvent(Player player, ChestGame chestGame) {
        this.player = player;
        this.chestGame = chestGame;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}