package me.dreamdevs.randomlootchest.api.event.player;

import lombok.Getter;
import me.dreamdevs.randomlootchest.api.event.RandomPlayerEvent;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlayerInteractChestEvent extends RandomPlayerEvent {

	private final @Getter Location chestLocation;

	public PlayerInteractChestEvent(Player player, Location chestLocation) {
		super(player.getUniqueId(), false);
		this.chestLocation = chestLocation;
	}

}