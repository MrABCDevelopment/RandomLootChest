package me.dreamdevs.github.randomlootchest.utils;

import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@UtilityClass
public class ColourUtil {

    public static String colorize(String string) {
        if(string == null) return null;
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        for (Matcher matcher = pattern.matcher(string); matcher.find(); matcher = pattern.matcher(string)) {
            String color = string.substring(matcher.start(), matcher.end());
            string = string.replace(color, ChatColor.of(color) + ""); // You're missing this replacing
        }
        string = ChatColor.translateAlternateColorCodes('&', string); // Translates any & codes too
        return string;
    }

    public static List<String> colouredLore(String... lore) {
        if(lore == null) return new ArrayList<>();
        return Arrays.stream(lore).map(s -> ChatColor.translateAlternateColorCodes('&', s)).collect(Collectors.toList());
    }

    public static List<String> colouredLore(List<String> lore) {
        if(lore == null) return new ArrayList<>();
        return lore.stream().map(s -> ChatColor.translateAlternateColorCodes('&', s)).collect(Collectors.toList());
    }

}