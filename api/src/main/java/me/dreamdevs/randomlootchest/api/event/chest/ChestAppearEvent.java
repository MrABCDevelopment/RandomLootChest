package me.dreamdevs.randomlootchest.api.event.chest;

import lombok.Getter;
import me.dreamdevs.randomlootchest.api.event.RandomLootChestEvent;
import me.dreamdevs.randomlootchest.api.object.IChestGame;
import org.bukkit.Location;

public class ChestAppearEvent extends RandomLootChestEvent {

	private @Getter final IChestGame chestGame;
	private @Getter final Location location;

	public ChestAppearEvent(IChestGame chestGame, Location location) {
		this.chestGame = chestGame;
		this.location = location;
	}

}