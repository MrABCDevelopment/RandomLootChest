package me.dreamdevs.github.randomlootchest.database;

import lombok.Getter;
import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.api.database.DatabaseConnector;
import me.dreamdevs.github.randomlootchest.utils.Settings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitScheduler;

public class Database {

    private @Getter DatabaseConnector connector;
    private Thread thread;

    public void connect(String databaseType) {
        Class<? extends DatabaseConnector> database = null;
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Connecting to database...");
        try {
            database = Class.forName("me.dreamdevs.github.randomlootchest.database.Database" + databaseType).asSubclass(DatabaseConnector.class);
            connector = database.newInstance();
            connector.connect();
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Connected to "+databaseType+" database.");
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Database type '" + databaseType + "' does not exist!");
        }
    }

    public void disconnect() {
        connector.disconnect();
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Disconnected from the database.");
    }

    public void autoSaveData() {
        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.runTaskTimerAsynchronously(RandomLootChestMain.getInstance(), new Runnable() {
            @Override
            public void run() {
                saveData();
            }
        }, 0L, 20 * Settings.autoSaveTime);
    }

    public void saveData() {
        thread = new Thread(() -> {
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Saving data...");
            connector.saveData();
        });
        thread.start();
    }

    public void loadData() {
        thread = new Thread(() -> {
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Loading data...");
            connector.loadData();
        });
        thread.start();
    }

}