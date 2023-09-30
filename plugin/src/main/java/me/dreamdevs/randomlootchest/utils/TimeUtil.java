package me.dreamdevs.randomlootchest.utils;

import lombok.experimental.UtilityClass;
import me.dreamdevs.randomlootchest.api.Config;

@UtilityClass
public final class TimeUtil {

    public static long convertStringToCooldown(String str) {
        long cooldownTime = 0;

        String[] split = str.split(" ");

        for(String time : split) {
            char key = time.charAt(1);
            int value = Integer.parseInt(""+time.charAt(0));

            if(time.length() >= 3) {
                key = time.charAt(2);
                value = Integer.parseInt(""+time.charAt(0) + time.charAt(1));
            }

            if(key == 'd') {
                cooldownTime += value * 86400L;
            }
            if(key == 'h') {
                cooldownTime += value * 3600L;
            }
            if(key == 'm') {
                cooldownTime += value * 60L;
            }
            if(key == 's') {
                cooldownTime += value;
            }
        }

        return cooldownTime;
    }

    public static String formattedTime(long time) {
        long days = time / 86400;
        long hours = time % 86400 / 3600;
        long minutes = time % 3600 / 60;
        long seconds = time % 60;

        if (days > 0) {
            if (Config.USE_ROUNDED_COOLDOWN_FORMAT.toBoolean()) {
                return days+"d";
            }
            return days+"d "+hours+"h "+minutes+"m "+seconds+"s";
        }
        if (hours > 0) {
            if (Config.USE_ROUNDED_COOLDOWN_FORMAT.toBoolean()) {
                return hours+"h";
            }
            return hours + "h " + minutes + "m " + seconds + "s";
        } else if(hours == 0 && minutes > 0) {
            if (Config.USE_ROUNDED_COOLDOWN_FORMAT.toBoolean()) {
                return minutes+"m";
            }
            return minutes + "m " + seconds + "s";
        } else {
            return seconds+"s";
        }
    }

}