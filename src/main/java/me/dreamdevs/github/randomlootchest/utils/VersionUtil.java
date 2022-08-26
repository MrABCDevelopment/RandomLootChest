package me.dreamdevs.github.randomlootchest.utils;

import lombok.Getter;
import org.bukkit.Bukkit;

public class VersionUtil {

    private @Getter static VersionUtil instance;

    public VersionUtil() {
        instance = this;
        setupVersion();
    }
    public void setupVersion() {
        ConsoleUtil.sendPluginMessage("&aYou are using "+getVersion()+" version.");
        switch (getVersion()) {
            case "v1_7_R1":
            case "v1_7_R2":
            case "v1_7_R3":
            case "v1_7_R4":
            case "v1_8_R1":
                ReflectionUtils.useOldMethods = true;
                ConsoleUtil.sendPluginMessage("&cThis version is the oldest version which RandomLootChest supports!");
                ConsoleUtil.sendPluginMessage("&cMany features are disabled, the other ones may cause the issues,");
                ConsoleUtil.sendPluginMessage("&cbut basic functions works fine!");
                break;
            case "v1_8_R2":
            case "v1_8_R3":
                ConsoleUtil.sendPluginMessage("&cThis Minecraft Server version is not recommended version to use");
                ConsoleUtil.sendPluginMessage("&cRandomLootChest plugin, but it works pretty fine.");
                break;
            case "v1_9_R1":
            case "v1_9_R2":
            case "v1_10_R1":
            case "v1_11_R1":
            case "v1_12_R1":
                ConsoleUtil.sendPluginMessage("&aSome features from newer versions doesn't work on this version.");
                ConsoleUtil.sendPluginMessage("&aBut everything works fine! ;D");
                break;
            case "v1_13_R1":
            case "v1_13_2R":
            case "v1_14_R1":
            case "v1_15_R1":
                ConsoleUtil.sendPluginMessage("&aVersion is ");
                break;
            case "v1_16_R1":
            case "v1_16_R2":
            case "v1_16_R3":
                ConsoleUtil.sendPluginMessage("&a");
                break;
            case "v1_17_R1":
            case "v1_18_R1":
            case "v1_18_R2":
            case "v1_19_R1":

                break;
            default:
                ConsoleUtil.sendPluginMessage("&cUnknown and unsupported minecraft version, but");
                ConsoleUtil.sendPluginMessage("&cRandomLootChest is still working...");
                break;
        }
    }

    public static String getVersion() {
        String packageName = Bukkit.getServer().getClass().getPackage().getName();
        return packageName.substring(packageName.lastIndexOf(".") + 1);
    }

    public static boolean isLegacy() {
        if(getVersion().startsWith("v1_7") || getVersion().startsWith("v1_8") || getVersion().startsWith("v1_9") || getVersion().startsWith("v1_10") || getVersion().startsWith("v1_11") || getVersion().startsWith("v1_12"))
            return true;
        return false;
    }

    public static boolean is1_8_orOlder() {
        if(getVersion().startsWith("v1_7") || getVersion().startsWith("v1_8"))
            return true;
        return false;
    }
    public static boolean is1_16orNewer() {
        if(getVersion().startsWith("v1_16") || getVersion().startsWith("v1_17") || getVersion().startsWith("v1_18") || getVersion().startsWith("v1_19"))
            return true;
        return false;
    }

}