package me.dreamdevs.github.randomlootchest.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class ConsoleUtil {

    public static void sendPluginMessage(String message) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

}