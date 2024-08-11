package me.dreamdevs.randomlootchest.api.event.player;

import lombok.Getter;
import lombok.Setter;
import me.dreamdevs.randomlootchest.api.event.RandomPlayerEvent;
import me.dreamdevs.randomlootchest.api.object.IChestGame;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerOpenChestEvent extends RandomPlayerEvent {

	private final @Getter IChestGame chestGame;
	private @Setter @Getter ItemStack[] rewards;

	public PlayerOpenChestEvent(Player player, IChestGame chestGame, ItemStack[] rewards) {
		super(player.getUniqueId(), false);
		this.chestGame = chestGame;
		this.rewards = rewards;
	}
}