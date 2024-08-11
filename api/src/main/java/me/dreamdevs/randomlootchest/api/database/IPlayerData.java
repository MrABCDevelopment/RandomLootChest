package me.dreamdevs.randomlootchest.api.database;

import me.dreamdevs.randomlootchest.api.object.IChestGame;
import org.bukkit.Location;

public interface IPlayerData {

    /**
     * Applies the cooldown to a player for specific location.
     */

    void applyCooldown(Location location, int seconds);

    /**
     * Checks if player has cooldown on specific location.
     */

    boolean hasCooldown(Location location);

    /**
     * This method returns the amount of seconds on specific location.
     *
     * This only returns value in seconds.
     */

    int getLeftCooldownSeconds(Location location);

    /**
     * Open specific chest to the player. Uses a string-type.
     * This method does not applies cooldown, use applyCooldown method along.
     */

    void openChest(String type);

    /**
     * Open specific chest to the player.
     * This method does not applies cooldown, use applyCooldown method along.
     */

    void openChest(IChestGame chestGame);

}