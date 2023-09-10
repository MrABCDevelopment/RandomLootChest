package me.dreamdevs.randomlootchest.utils;

import lombok.Getter;
import org.bukkit.Bukkit;

public class VersionUtil {

    private @Getter static VersionUtil instance;

    public VersionUtil() {
        instance = this;
        setupVersion();
    }

    public void setupVersion() {
        Util.sendPluginMessage("&aYou are using "+getVersion()+" version.");
        switch (getVersion()) {
            case "v1_13_R1":
            case "v1_13_2R":
            case "v1_14_R1":
            case "v1_15_R1":
                Util.sendPluginMessage("&aSome features from newer versions doesn't work on this version.");
                Util.sendPluginMessage("&aBut everything works fine! ;D");
                break;
            case "v1_16_R1":
            case "v1_16_R2":
            case "v1_16_R3":
            case "v1_17_R1":
            case "v1_18_R1":
            case "v1_18_R2":
            case "v1_19_R1":
            case "v1_19_R2":
            case "v1_19_R3":
            case "v1_20_R1":
                Util.sendPluginMessage("&aThis version enabled all features!");
                break;
            default:
                Util.sendPluginMessage("&cUnknown and unsupported minecraft version, but");
                Util.sendPluginMessage("&cRandomLootChest is still working...");
                break;
        }
    }

    public static String getVersion() {
        String packageName = Bukkit.getServer().getClass().getPackage().getName();
        return packageName.substring(packageName.lastIndexOf(".") + 1);
    }

}