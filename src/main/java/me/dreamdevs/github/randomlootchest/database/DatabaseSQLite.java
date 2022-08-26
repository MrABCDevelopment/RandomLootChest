package me.dreamdevs.github.randomlootchest.database;

import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.api.database.DatabaseConnector;
import me.dreamdevs.github.randomlootchest.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class DatabaseSQLite extends DatabaseConnector {

    private File file;

    @Override
    public void connect() {
        file = new File(RandomLootChestMain.getInstance().getDataFolder(), "database.db");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String URL = "jdbc:sqlite:"+file;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(URL);
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Connected to the SQLite database...");
            createTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Error: couldn't connected to the database.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void saveData() {
        try {
            ConcurrentHashMap<HashMap<UUID, Location>, AtomicInteger> mainMap = RandomLootChestMain.getInstance().getCooldownManager().getChestCooldowns();
            for (Map<UUID, Location> map : mainMap.keySet()) {
                for (UUID uuid : map.keySet()) {
                    if(!RandomLootChestMain.getInstance().getDatabaseManager().getConnector().isAccount(uuid)) {
                        RandomLootChestMain.getInstance().getDatabaseManager().getConnector().insertData(uuid);
                    }
                    HashMap<HashMap<UUID, Location>, AtomicInteger> playerMap = RandomLootChestMain.getInstance().getCooldownManager().getPlayerCooldowns(uuid);
                    String line = "";
                    for (HashMap<UUID, Location> map1 : playerMap.keySet()) {
                        line = line+Util.getLocationString(map1.get(uuid)) + ";" + playerMap.get(map1).get()+"_";
                    }
                    PreparedStatement ps = connection.prepareStatement("UPDATE data SET cooldowns = '"+line+"' WHERE user = '"+Bukkit.getOfflinePlayer(uuid).getName()+"'");
                    ps.executeUpdate();
                }
            }
        } catch (SQLException e ){
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Error while saving data...");
        }
    }

    @Override
    public void loadData() {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("SELECT * FROM data");
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(rs.getString("user"));
                String string = rs.getString("cooldowns");
                String[] splits = string.split("_");
                for(String s : splits) {
                    String[] c = s.split(";");
                    RandomLootChestMain.getInstance().getCooldownManager().setCooldown(offlinePlayer.getUniqueId(), Util.getStringLocation(c[0]), Integer.parseInt(c[1]));
                }
            }
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Loaded data!");
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Error while loading data!");
            e.printStackTrace();
        }
    }

    @Override
    public boolean isAccount(UUID account) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("SELECT user FROM data WHERE user='"+Bukkit.getOfflinePlayer(account).getName()+"'");
            ResultSet rs = ps.executeQuery();
            if(rs.next())
                return true;
        } catch (Exception e) {

        }
        return false;
    }

    @Override
    public void insertData(UUID account) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("INSERT INTO data(user, cooldowns) VALUES(?,?);");
            ps.setObject(1, Bukkit.getOfflinePlayer(account).getName());
            ps.setString(2, "null");
            ps.executeUpdate();
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"New player with UUID: "+account+" - "+Bukkit.getOfflinePlayer(account).getName());
        } catch (SQLException e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Error has occure while inserting data!");
        }
    }

    private void createTable() {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS data(user TEXT PRIMARY KEY, cooldowns varchar(400));");
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Error has occured while creating database table.");
        }
    }
}