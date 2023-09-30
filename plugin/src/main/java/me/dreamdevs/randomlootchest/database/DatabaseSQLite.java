package me.dreamdevs.randomlootchest.database;

import me.dreamdevs.randomlootchest.api.Config;
import me.dreamdevs.randomlootchest.api.database.IDatabase;
import me.dreamdevs.randomlootchest.RandomLootChestMain;
import me.dreamdevs.randomlootchest.database.data.PlayerData;
import me.dreamdevs.randomlootchest.api.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import java.io.File;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class DatabaseSQLite implements IDatabase {

    private final File databaseFile = new File(RandomLootChestMain.getInstance().getDataFolder(), "database.db");
    private final String URL = "jdbc:sqlite:"+databaseFile;
    private Connection connection;

    @Override
    public void connect() {
        Util.tryCreateFile(databaseFile);
        try {
            connection = DriverManager.getConnection(URL);
            createTable();
        } catch (Exception e) {
            Util.sendPluginMessage("&cSomething went wrong while connecting to the SQLite database.");
            Util.sendPluginMessage("&cError message: "+e.getMessage());
        }
    }

    @Override
    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                Util.sendPluginMessage("&aDisconnected from the SQLite database...");
            }
        } catch (SQLException e) {

        }
    }

    @Override
    public void loadData() {
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM data")) {
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(rs.getString("user"));
                String string = rs.getString("activeCooldown");
                if (string.isEmpty() || string.isBlank()) {
                    continue;
                }
                String[] splits = string.split("_");
                Util.sendPluginMessage(Arrays.toString(splits));
                for(String s : splits) {
                    String[] c = s.split(";");
                    Util.sendPluginMessage(Arrays.toString(c));
                    RandomLootChestMain.getInstance().getCooldownManager().setCooldown(offlinePlayer,
                            Util.getStringLocation(c[0]), Integer.parseInt(c[1]), false);
                }
            }
            Util.sendPluginMessage("&aLoaded users data!");
        } catch (SQLException e) {
            Util.sendPluginMessage("&cSomething went wrong while loading users data!");
            e.printStackTrace();
        }
    }

    @Override
    public void saveData() {
        List<PlayerData> players = RandomLootChestMain.getInstance().getCooldownManager().getPlayers();
        players.forEach(playerData -> {
            if (!exists(playerData.getPlayer())) {
                // Insert player to database
                try (PreparedStatement ps = connection.prepareStatement("INSERT INTO data(user, activeCooldown) VALUES(?,?);")) {
                    ps.setObject(1, playerData.getPlayer().getName());
                    ps.setString(2, "null");
                    ps.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (Map.Entry<Location, AtomicInteger> entry : playerData.getCooldown().entrySet()) {
                stringBuilder.append(Util.getLocationString(entry.getKey())).append(";").append(entry.getValue().get()).append("_");
            }
            try (PreparedStatement ps = connection.prepareStatement("UPDATE data SET activeCooldown = '"+stringBuilder.toString()+"' WHERE user = '"+playerData.getPlayer().getName()+"'")) {
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private boolean exists(OfflinePlayer player) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT user FROM data WHERE user='"+player.getName()+"'")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            // To do nothing
        }
        return false;
    }

    private void createTable() {
        try (PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `"
                + Config.DATABASE_NAME.toString()+"`.`data` (`user` VARCHAR(16) NULL DEFAULT NULL , `activeCooldown` VARCHAR(400) NULL DEFAULT NULL , PRIMARY KEY (`user`))")) {
            preparedStatement.execute();
            Util.sendPluginMessage("&aCreated table for users and their active cooldowns!");
        } catch (SQLException e) {
            Util.sendPluginMessage("&cCould not created table for users!");
            e.printStackTrace();
        }
    }

}