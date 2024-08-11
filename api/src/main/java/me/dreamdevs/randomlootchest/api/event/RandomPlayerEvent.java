package me.dreamdevs.randomlootchest.api.event;

import lombok.Getter;
import me.dreamdevs.randomlootchest.api.RandomLootChestApi;
import me.dreamdevs.randomlootchest.api.database.IPlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

import java.util.UUID;

public class RandomPlayerEvent extends RandomLootChestEvent implements Cancellable {

	private final @Getter UUID playerUUID;
	private final @Getter IPlayerData playerData;
	private boolean cancelled;

	public RandomPlayerEvent(UUID uuid, boolean cancelled) {
		this.playerUUID = uuid;
		this.playerData = RandomLootChestApi.API.getPlayerData(uuid);
		this.cancelled = cancelled;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public Player getOnlinePlayer() {
		return Bukkit.getPlayer(playerUUID);
	}

}