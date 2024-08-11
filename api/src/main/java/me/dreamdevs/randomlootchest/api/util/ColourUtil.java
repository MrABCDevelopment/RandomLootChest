package me.dreamdevs.randomlootchest.api.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@UtilityClass
public final class ColourUtil {

    public static String colorize(@NonNull String string) {
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        for (Matcher matcher = pattern.matcher(string); matcher.find(); matcher = pattern.matcher(string)) {
            String color = string.substring(matcher.start(), matcher.end());
            string = string.replace(color, ChatColor.of(color) + ""); // You're missing this replacing
        }
        return ChatColor.translateAlternateColorCodes('&', string); // Translates any & codes too
    }

    public static List<String> colouredLore(@NotNull String... lore) {
        return Arrays.stream(lore).map(ColourUtil::colorize).collect(Collectors.toList());
    }

    public static List<String> colouredLore(@NotNull List<String> lore) {
        return lore.stream().map(ColourUtil::colorize).collect(Collectors.toList());
    }

}