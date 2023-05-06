package me.dreamdevs.github.randomlootchest.database;

import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.api.database.DatabaseConnector;
import me.dreamdevs.github.randomlootchest.utils.Settings;
import me.dreamdevs.github.randomlootchest.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class DatabaseMySQL extends DatabaseConnector {

    private String user = Settings.databaseUser;
    private String pass = Settings.databasePassword;
    private String url = "jdbc:mysql://" + Settings.databaseHost + ":" + Settings.databasePort + "/" + Settings.databaseDatabase+"?useSSL=false";

    @Override
    public void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(url, user, pass);
            createTable();
            Util.sendPluginMessage("&aConnected to the MySQL database!");
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException throwables) {
            throwables.printStackTrace();
            Util.sendPluginMessage("&cError: couldn't connect to the database!");
        }
    }

    @Override
    public void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {}
    }

    @Override
    public void saveData() {
        try {
            Map<HashMap<UUID, Location>, AtomicInteger> mainMap = RandomLootChestMain.getInstance().getCooldownManager().getChestCooldowns();
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
            Util.sendPluginMessage("&cSomething went wrong while saving data...");
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
                    RandomLootChestMain.getInstance().getCooldownManager().setCooldown(offlinePlayer.getUniqueId(), Util.getStringLocation(c[0]), Integer.parseInt(c[1]), false);
                }
            }
            Util.sendPluginMessage("&aLoaded data!");
        } catch (SQLException e) {
            Util.sendPluginMessage("&cError while loading data from database!");
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
            Util.sendPluginMessage("&aNew player with UUID: "+account+" - "+Bukkit.getOfflinePlayer(account).getName());
        } catch (SQLException e) {
            e.printStackTrace();
            Util.sendPluginMessage("&cSomething went wrong while inserting data!");
        }
    }

    private void createTable() {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS data(user varchar(32) PRIMARY KEY, cooldowns varchar(800));");
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            Util.sendPluginMessage("&cSomething went wrong while creating database table!");
            e.printStackTrace();
        }
    }
}