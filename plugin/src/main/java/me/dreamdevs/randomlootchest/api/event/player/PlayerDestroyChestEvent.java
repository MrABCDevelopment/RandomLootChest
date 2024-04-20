package me.dreamdevs.randomlootchest.api.event.player;

import lombok.Getter;
import me.dreamdevs.randomlootchest.api.event.RandomPlayerEvent;
import me.dreamdevs.randomlootchest.api.object.IChestGame;
import org.bukkit.Location;

import java.util.UUID;

public class PlayerDestroyChestEvent extends RandomPlayerEvent {

	private final @Getter IChestGame chestGame;
	private final @Getter Location location;

	public PlayerDestroyChestEvent(UUID uuid, IChestGame chestGame, Location location) {
		super(uuid, true);
		this.chestGame = chestGame;
		this.location = location;
	}
}