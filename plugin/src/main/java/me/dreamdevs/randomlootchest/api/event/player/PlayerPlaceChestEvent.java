package me.dreamdevs.randomlootchest.api.event.player;

import me.dreamdevs.randomlootchest.api.event.RandomPlayerEvent;

import java.util.UUID;

public class PlayerPlaceChestEvent extends RandomPlayerEvent {

	public PlayerPlaceChestEvent(UUID uuid, boolean cancelled) {
		super(uuid, cancelled);
	}
}