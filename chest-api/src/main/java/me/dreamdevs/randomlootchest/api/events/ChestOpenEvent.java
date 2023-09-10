package me.dreamdevs.randomlootchest.api.events;

import lombok.Getter;
import me.dreamdevs.randomlootchest.api.objects.IChestGame;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class ChestOpenEvent extends Event{

    private @Getter static final HandlerList handlerList = new HandlerList();

    private final Player player;
    private final IChestGame chestGame;

    public ChestOpenEvent(Player player, IChestGame chestGame) {
        this.player = player;
        this.chestGame = chestGame;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

}