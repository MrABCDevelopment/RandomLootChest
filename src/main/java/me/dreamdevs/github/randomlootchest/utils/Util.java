package me.dreamdevs.github.randomlootchest.utils;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.*;

public class Util {

    private @Getter static Random random = new Random();

    public static boolean chance(double chance) {
        return Math.random() < chance;
    }

    public static int randomSlot(int max) {
        return random.nextInt(max);
    }

    public static Location getStringLocation(String location) {
        if(location == null) return null;
        try {
            String params[] = location.split(":");
            int x = Integer.parseInt(params[0]);
            int y = Integer.parseInt(params[1]);
            int z = Integer.parseInt(params[2]);
            String world = params[3];
            return new Location(Bukkit.getWorld(world),x,y,z);
        } catch (Exception e) {

        }
        return null;
    }

    public static String getLocationString(Location location) {
        if(location == null) return null;
        return location.getBlockX()+":"+location.getBlockY()+":"+location.getBlockZ()+":"+location.getWorld().getName();
    }

}