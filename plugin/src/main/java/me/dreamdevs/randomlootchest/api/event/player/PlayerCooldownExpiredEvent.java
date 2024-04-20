package me.dreamdevs.randomlootchest.api.event.player;

import lombok.Getter;
import me.dreamdevs.randomlootchest.api.event.RandomPlayerEvent;
import org.bukkit.Location;

import java.util.UUID;

public class PlayerCooldownExpiredEvent extends RandomPlayerEvent {

	private final @Getter Location location;

	public PlayerCooldownExpiredEvent(UUID uuid, Location location) {
		super(uuid, false);
		this.location = location;
	}

}