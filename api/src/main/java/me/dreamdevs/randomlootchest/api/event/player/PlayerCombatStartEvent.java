package me.dreamdevs.randomlootchest.api.event.player;

import me.dreamdevs.randomlootchest.api.event.RandomPlayerEvent;

import java.util.UUID;

public class PlayerCombatStartEvent extends RandomPlayerEvent {

	public PlayerCombatStartEvent(UUID uuid) {
		super(uuid, false);
	}
}