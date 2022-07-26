package me.mrsandking.github.randomlootchest.util;

import lombok.Getter;
import me.mrsandking.github.randomlootchest.RandomLootChestMain;
import org.bukkit.ChatColor;

@Getter
public class Settings {

    public static boolean randomChests;
    public static boolean activeParticles;
    public static String particleType;
    public static int particleAmount;
    public static String wandItemDisplayName;
    public static int autoSaveTime;
    public static boolean wandItemPermissionToUse;
    public static boolean useDatabase;
    public static String databaseType;
    public static String databaseUser;
    public static String databaseHost;
    public static String databasePassword;
    public static String databaseDatabase;
    public static int databasePort;

    public static boolean filledChestInfo;

    public static boolean hologramsInfo;
    public Settings(RandomLootChestMain plugin) {
        useDatabase = plugin.getConfigManager().getConfig("config.yml").getBoolean("database.use");
        databaseType = plugin.getConfigManager().getConfig("config.yml").getString("database.type");
        databaseHost = plugin.getConfigManager().getConfig("config.yml").getString("database.host");
        databasePort = plugin.getConfigManager().getConfig("config.yml").getInt("database.port");
        databasePassword = plugin.getConfigManager().getConfig("config.yml").getString("database.password");
        databaseUser = plugin.getConfigManager().getConfig("config.yml").getString("database.user");
        databaseDatabase = plugin.getConfigManager().getConfig("config.yml").getString("database.database");
        autoSaveTime = plugin.getConfigManager().getConfig("config.yml").getInt("database.auto-save-time");
        randomChests = plugin.getConfigManager().getConfig("config.yml").getBoolean("random-chests");
        activeParticles = plugin.getConfigManager().getConfig("config.yml").getBoolean("particles.active");
        particleType = plugin.getConfigManager().getConfig("config.yml").getString("particles.type").toUpperCase();
        particleAmount = plugin.getConfigManager().getConfig("config.yml").getInt("particles.amount");
        wandItemDisplayName = ChatColor.translateAlternateColorCodes('&', plugin.getConfigManager().getConfig("config.yml").getString("wand-item.displayName"));
        wandItemPermissionToUse = plugin.getConfigManager().getConfig("config.yml").getBoolean("wand-item.permission-to-use");
        filledChestInfo = plugin.getConfigManager().getConfig("config.yml").getBoolean("filled-chest-info");
        hologramsInfo = plugin.getConfigManager().getConfig("config.yml").getBoolean("holograms-on-chests");
    }

}