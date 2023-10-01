package me.dreamdevs.randomlootchest.api.utils;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;

@UtilityClass
public class Util {

    private final @Getter static Random random = new Random();

    public static boolean chance(double chance) {
        return Math.random() < chance;
    }

    public static int randomSlot(int max) {
        return random.nextInt(max);
    }

    public static Location getStringLocation(@NotNull String location) {
        try {
            String[] params = location.split(":");
            int x = Integer.parseInt(params[0]);
            int y = Integer.parseInt(params[1]);
            int z = Integer.parseInt(params[2]);
            String world = params[3];
            return new Location(Bukkit.getWorld(world),x,y,z);
        } catch (Exception e) {

        }
        return null;
    }

    public static String getLocationString(@NotNull Location location) {
        return location.getBlockX()+":"+location.getBlockY()+":"+location.getBlockZ()+":"
                + Objects.requireNonNull(location.getWorld()).getName();
    }

    public static void sendPluginMessage(String message) {
        Bukkit.getConsoleSender().sendMessage(ColourUtil.colorize(message));
    }

    public static void tryCreateFile(@NotNull File file) {
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                // To do nothing
            }
        }
    }

}