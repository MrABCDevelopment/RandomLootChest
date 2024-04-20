package me.dreamdevs.randomlootchest.listeners;

import me.dreamdevs.randomlootchest.RandomLootChestMain;
import me.dreamdevs.randomlootchest.api.database.IPlayerData;
import me.dreamdevs.randomlootchest.database.data.PlayerData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public final class PlayerListeners implements Listener {

	@EventHandler
	public void joinEvent(PlayerJoinEvent event) {
		IPlayerData playerData = RandomLootChestMain.getInstance().getCooldownManager().getPlayerData(event.getPlayer().getUniqueId());
		if (playerData == null) {
			PlayerData newData = new PlayerData(event.getPlayer().getUniqueId());
			RandomLootChestMain.getInstance().getCooldownManager().getPlayers().add(newData);
		}
	}

}