package me.dreamdevs.randomlootchest.api.database;

import org.bukkit.Location;

public interface IPlayerData {

    void applyCooldown(Location location, int seconds);

    boolean hasCooldown(Location location);

    int getLeftCooldownSeconds(Location location);

}