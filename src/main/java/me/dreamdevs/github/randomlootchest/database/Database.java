package me.dreamdevs.github.randomlootchest.database;

import lombok.Getter;
import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.api.database.DatabaseConnector;
import me.dreamdevs.github.randomlootchest.utils.Settings;
import me.dreamdevs.github.randomlootchest.utils.Util;
import org.bukkit.Bukkit;

public class Database {

    private @Getter DatabaseConnector connector;
    private Thread thread;

    public void connect(String databaseType) {
        Class<? extends DatabaseConnector> database = null;
        Util.sendPluginMessage("&aConnecting to database...");
        try {
            database = Class.forName("me.dreamdevs.github.randomlootchest.database.Database" + databaseType).asSubclass(DatabaseConnector.class);
            connector = database.newInstance();
            connector.connect();
            Util.sendPluginMessage("&aConnected to "+databaseType+" database.");
        } catch (Exception e) {
            Util.sendPluginMessage("&cDatabase type '"+databaseType+"' does not exist!");
        }
    }

    public void disconnect() {
        connector.disconnect();
        Util.sendPluginMessage("&aDisconnected from the database.");
    }

    public void autoSaveData() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(RandomLootChestMain.getInstance(), this::saveData, 0L, 20L * Settings.autoSaveTime);
    }

    public void saveData() {
        thread = new Thread(() -> {
            Util.sendPluginMessage("&aSaving data...");
            connector.saveData();
        });
        thread.start();
    }

    public void loadData() {
        thread = new Thread(() -> {
            Util.sendPluginMessage("&aLoading data...");
            connector.loadData();
        });
        thread.start();
    }

}