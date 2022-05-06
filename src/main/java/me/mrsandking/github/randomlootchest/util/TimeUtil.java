package me.mrsandking.github.randomlootchest.util;

public class TimeUtil {

    public static String formattedTime(int time) {
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