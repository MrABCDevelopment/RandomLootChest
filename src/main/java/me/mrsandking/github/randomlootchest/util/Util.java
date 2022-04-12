package me.mrsandking.github.randomlootchest.util;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import java.util.*;

public class Util {

    private @Getter static Random random = new Random();

    public static String getTime(long timeLeft) {
        int seconds = (int) (timeLeft);
        long sec = seconds % 60;
        long minutes = seconds % 3600 / 60;
        long hours = seconds % 86400 / 3600;
        if(hours > 0) {
            return hours + "h " + minutes + "m " + sec + "s";
        } else if(hours == 0 && minutes > 0) {
            return minutes + "m " + sec + "s";
        } else {
            return sec+"s";
        }
    }

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

    public static List<String> colouredLore(String... lore) {
        List<String> list = new ArrayList<>();
        for(String line : lore) {
            list.add(ChatColor.translateAlternateColorCodes('&', line));
        }
        return list;
    }

    public static List<String> colouredLore(List<String> lore) {
        List<String> list = new ArrayList<>();
        for(String line : lore) {
            list.add(ChatColor.translateAlternateColorCodes('&', line));
        }
        return list;
    }

    public static ItemStack getPotion(String potionType, int level, boolean splash, int amount) {
        try {
            Potion pot = new Potion(PotionType.valueOf(potionType.toUpperCase()), level, splash);
            return pot.toItemStack(amount);
        } catch (NullPointerException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Cannot get potion with type: "+potionType+" and tier "+level);
            return null;
        }
    }

}