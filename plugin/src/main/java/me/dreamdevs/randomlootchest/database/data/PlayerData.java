package me.dreamdevs.randomlootchest.database.data;

import lombok.Getter;
import me.dreamdevs.randomlootchest.RandomLootChestMain;
import me.dreamdevs.randomlootchest.api.database.IPlayerData;
import me.dreamdevs.randomlootchest.api.object.IChestGame;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class PlayerData implements IPlayerData {

    private final UUID uuid;
    private final OfflinePlayer player;
    private final Map<Location, AtomicInteger> cooldown;

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
        this.player = Bukkit.getOfflinePlayer(uuid);
        this.cooldown = new ConcurrentHashMap<>();
    }

    @Override
    public void applyCooldown(Location location, int seconds) {
        cooldown.putIfAbsent(location, new AtomicInteger(seconds));
    }

    @Override
    public boolean hasCooldown(Location location) {
        return cooldown.containsKey(location) && cooldown.get(location).get() > 0;
    }

    @Override
    public int getLeftCooldownSeconds(Location location) {
        return hasCooldown(location) ? getCooldown().get(location).get() : 0;
    }

    @Override
    public void openChest(String type) {
        Player onlinePlayer = Bukkit.getPlayer(uuid);
        if (onlinePlayer == null) {
            return;
        }

        RandomLootChestMain.getInstance().getChestsManager().openChest(onlinePlayer, type);
    }

    @Override
    public void openChest(IChestGame chestGame) {
        this.openChest(chestGame.getId());
    }

}