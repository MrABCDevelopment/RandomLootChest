package me.dreamdevs.randomlootchest.api.utils;

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
            case "v1_13_R1", "v1_13_R2", "v1_14_R1", "v1_15_R1" -> {
                Util.sendPluginMessage("&aSome features from newer versions doesn't work on this version.");
                Util.sendPluginMessage("&aBut everything works fine! ;D");
            }
            case "v1_16_R1", "v1_16_R2", "v1_16_R3", "v1_17_R1", "v1_18_R1", "v1_18_R2", "v1_19_R1", "v1_19_R2", "v1_19_R3", "v1_20_R1" ->
                    Util.sendPluginMessage("&aThis version enabled all features!");
            default -> {
                Util.sendPluginMessage("&cUnknown and unsupported minecraft version, but");
                Util.sendPluginMessage("&cRandomLootChest is still working...");
            }
        }
    }

    public static String getVersion() {
        String packageName = Bukkit.getServer().getClass().getPackage().getName();
        return packageName.substring(packageName.lastIndexOf(".") + 1);
    }

}