package me.dreamdevs.github.randomlootchest.utils;

import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class ColourUtil {

    private static final Pattern pattern = Pattern.compile("(?<!\\\\)(#[a-fA-F0-9]{6})");

    public static String hexText(String message) {
        if(message == null) return "";

        if(VersionUtil.is1_16orNewer()) {

            Matcher matcher = pattern.matcher(message);

            while (matcher.find()) {
                String color = message.substring(matcher.start(), matcher.end());
                message = message.replace(color, "" + ChatColor.of(color));
            }

            return message;
        } else {
            return ChatColor.translateAlternateColorCodes('&', message);
        }
    }

    public static List<String> colouredLore(String... lore) {
        List<String> list = new ArrayList<>();
        for(String line : lore) {
            list.add(org.bukkit.ChatColor.translateAlternateColorCodes('&', line));
        }
        return list;
    }

    public static List<String> colouredLore(List<String> lore) {
        List<String> list = new ArrayList<>();
        if(lore == null || lore.isEmpty()) return list;
        for(String line : lore) {
            list.add(org.bukkit.ChatColor.translateAlternateColorCodes('&', line));
        }
        return list;
    }


}