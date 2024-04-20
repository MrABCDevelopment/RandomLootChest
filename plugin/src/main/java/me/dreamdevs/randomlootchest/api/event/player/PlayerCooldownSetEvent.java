package me.dreamdevs.randomlootchest.api.event.player;

import lombok.Getter;
import me.dreamdevs.randomlootchest.api.event.RandomPlayerEvent;
import org.bukkit.Location;

import java.util.UUID;

public class PlayerCooldownSetEvent extends RandomPlayerEvent {

	private final @Getter Location location;
	private final @Getter int seconds;

	public PlayerCooldownSetEvent(UUID uuid, Location location, int seconds) {
		super(uuid, false);
		this.location = location;
		this.seconds = seconds;
	}

}