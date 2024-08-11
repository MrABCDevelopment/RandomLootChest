package me.dreamdevs.randomlootchest.api.event;

import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class RandomLootChestEvent extends Event {

	private @Getter static final HandlerList handlerList = new HandlerList();

	@NotNull
	@Override
	public HandlerList getHandlers() {
		return handlerList;
	}

}