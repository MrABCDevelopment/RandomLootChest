package me.dreamdevs.github.randomlootchest.utils;

import lombok.Getter;
import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Settings {

    public static boolean randomChests;
    //public static boolean activeParticles;
    //public static String particleType;
    //public static int particleAmount;
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

    public static boolean oldCooldownParse;
    public static Map<String, Boolean> filledChestsInfo;

   // public static boolean filledChestInfo;

    public static boolean hologramsInfo;
    public static boolean openChestInCombat;
    public static String soundChestAdd;

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
        wandItemDisplayName = ChatColor.translateAlternateColorCodes('&', plugin.getConfigManager().getConfig("config.yml").getString("wand-item.displayName"));
        wandItemPermissionToUse = plugin.getConfigManager().getConfig("config.yml").getBoolean("wand-item.permission-to-use");
        filledChestsInfo = new HashMap<>();
        oldCooldownParse = plugin.getConfigManager().getConfig("config.yml").getBoolean("old-cooldown-parse");
        ConfigurationSection section = plugin.getConfigManager().getConfig("config.yml").getConfigurationSection("filled-chest-info");
        for(String key : section.getKeys(false)) {
            filledChestsInfo.put(key, section.getBoolean(key));
        }
        openChestInCombat = plugin.getConfigManager().getConfig("config.yml").getBoolean("openchest-in-combat");
        hologramsInfo = plugin.getConfigManager().getConfig("config.yml").getBoolean("holograms-on-chests");
        soundChestAdd = plugin.getConfigManager().getConfig("config.yml").getString("chest-add-sound").toUpperCase();
    }

}