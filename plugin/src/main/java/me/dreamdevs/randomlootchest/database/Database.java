package me.dreamdevs.randomlootchest.database;

import lombok.Getter;
import me.dreamdevs.randomlootchest.api.Config;
import me.dreamdevs.randomlootchest.api.database.IDatabase;
import me.dreamdevs.randomlootchest.RandomLootChestMain;
import me.dreamdevs.randomlootchest.api.utils.Util;
import org.bukkit.Bukkit;

public class Database {

    private @Getter IDatabase database;

    public void connect(String databaseType) {
        Util.sendPluginMessage("&aConnecting to database...");
        try {
            Class<? extends IDatabase> clazz = Class.forName("me.dreamdevs.randomlootchest.database.Database" + databaseType).asSubclass(IDatabase.class);
            database = clazz.getConstructor().newInstance();
            database.connect();
            Util.sendPluginMessage("&aConnected to "+databaseType+" database.");
        } catch (Exception e) {
            Util.sendPluginMessage("&cDatabase type '"+databaseType+"' does not exist!");
        }
    }

    public void disconnect() {
        database.disconnect();
        Util.sendPluginMessage("&aDisconnected from the database.");
    }

    public void autoSaveData() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(RandomLootChestMain.getInstance(), this::saveData, 0L, 20L * Config.DATABASE_AUTO_SAVE_TIME.toInt());
    }

    public void saveData() {
        Util.sendPluginMessage("&aSaving data...");
        database.saveData();
    }

    public void loadData() {
        Util.sendPluginMessage("&aLoading data...");
        database.loadData();
    }

}