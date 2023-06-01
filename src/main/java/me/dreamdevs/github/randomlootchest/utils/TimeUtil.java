package me.dreamdevs.github.randomlootchest.utils;

import lombok.experimental.UtilityClass;

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
        long sec = time % 60;
        long minutes = time % 3600 / 60;
        long hours = time % 86400 / 3600;
        if(hours > 0) {
            return hours + "h " + minutes + "m " + sec + "s";
        } else if(hours == 0 && minutes > 0) {
            return minutes + "m " + sec + "s";
        } else {
            return sec+"s";
        }
    }

}