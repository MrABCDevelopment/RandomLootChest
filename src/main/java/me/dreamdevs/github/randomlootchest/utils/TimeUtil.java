package me.dreamdevs.github.randomlootchest.utils;

import java.util.concurrent.TimeUnit;

public class TimeUtil {

    public static int getTime(String input) {
        String[] strings = input.split(" ");
        for(String s : strings) {
            if(Integer.getInteger(s) != null) {
                int i = Integer.parseInt(s);
            } else if(getUnit(s) != null) {
                TimeUnit unit = TimeUnit.valueOf(s.toUpperCase());

            }
        }
        return 0;
    }

    public static TimeUnit getUnit(String input)
    {
        TimeUnit u;
        switch(input.toLowerCase())
        {
            case "error":
            default:
                return null;
            case "s":
            case "sec":
            case "secs":
            case "second":
            case "seconds":
                u = TimeUnit.SECONDS;
                break;

            case "m":
            case "min":
            case "mins":
            case "minute":
            case "minutes":
                u = TimeUnit.MINUTES;
                break;

            case "h":
            case "hr":
            case "hrs":
            case "hour":
            case "hours":
                u = TimeUnit.HOURS;
                break;

            case "d":
            case "day":
            case "days":
                u = TimeUnit.DAYS;
                break;
        }
        return u;
    }

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