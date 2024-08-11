package me.dreamdevs.randomlootchest.api.event.player;

import me.dreamdevs.randomlootchest.api.event.RandomPlayerEvent;

import java.util.UUID;

public class PlayerCombatEndEvent extends RandomPlayerEvent {

	public PlayerCombatEndEvent(UUID uuid) {
		super(uuid, false);
	}

}
