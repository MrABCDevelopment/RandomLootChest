package me.dreamdevs.github.randomlootchest.utils;

import lombok.Getter;
import me.dreamdevs.github.randomlootchest.RandomLootChestMain;

@Getter
public class Settings {

    public static boolean randomChests;
    public static boolean updateChecker;
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

    public static boolean combatEnabled;
    public static int combatTime;
    public static boolean openChestInCombat;
    public static boolean hologramsInfo;
    public static String soundChestAdd;

    public static void loadVars() {
        useDatabase = RandomLootChestMain.getInstance().getConfigManager().getConfig("config.yml").getBoolean("database.use");
        databaseType = RandomLootChestMain.getInstance().getConfigManager().getConfig("config.yml").getString("database.type");
        databaseHost = RandomLootChestMain.getInstance().getConfigManager().getConfig("config.yml").getString("database.host");
        databasePort = RandomLootChestMain.getInstance().getConfigManager().getConfig("config.yml").getInt("database.port");
        databasePassword = RandomLootChestMain.getInstance().getConfigManager().getConfig("config.yml").getString("database.password");
        databaseUser = RandomLootChestMain.getInstance().getConfigManager().getConfig("config.yml").getString("database.user");
        databaseDatabase = RandomLootChestMain.getInstance().getConfigManager().getConfig("config.yml").getString("database.database");
        autoSaveTime = RandomLootChestMain.getInstance().getConfigManager().getConfig("config.yml").getInt("database.auto-save-time");
        randomChests = RandomLootChestMain.getInstance().getConfigManager().getConfig("config.yml").getBoolean("random-chests");
        wandItemDisplayName = ColourUtil.colorize(RandomLootChestMain.getInstance().getConfigManager().getConfig("config.yml").getString("wand-item.displayName"));
        wandItemPermissionToUse = RandomLootChestMain.getInstance().getConfigManager().getConfig("config.yml").getBoolean("wand-item.permission-to-use");
        updateChecker = RandomLootChestMain.getInstance().getConfigManager().getConfig("config.yml").getBoolean("update-checker");
        hologramsInfo = RandomLootChestMain.getInstance().getConfigManager().getConfig("config.yml").getBoolean("holograms-on-chests");
        soundChestAdd = RandomLootChestMain.getInstance().getConfigManager().getConfig("config.yml").getString("chest-add-sound").toUpperCase();
        combatEnabled = RandomLootChestMain.getInstance().getConfigManager().getConfig("config.yml").getBoolean("combat.enable");
        combatTime = RandomLootChestMain.getInstance().getConfigManager().getConfig("config.yml").getInt("combat.time");
    }

}