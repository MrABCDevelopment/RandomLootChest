package me.dreamdevs.randomlootchest.database;

import me.dreamdevs.randomlootchest.api.database.IDatabase;
import me.dreamdevs.randomlootchest.RandomLootChestMain;
import me.dreamdevs.randomlootchest.database.data.PlayerData;
import me.dreamdevs.randomlootchest.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import javax.sql.DataSource;
import java.io.File;
import java.io.PrintWriter;
import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class DatabaseSQLite implements IDatabase {

    private DataSource dataSource;

    @Override
    public void connect() {
        Util.sendPluginMessage("&aConnecting to the SQLite database...");

        dataSource = new SQLiteDataSource();
        try(Connection connection = dataSource.getConnection()) {
            createTable();
            Util.sendPluginMessage("&aConnected to the SQLite database...");
        } catch (SQLException e) {
            Util.sendPluginMessage("&cSomething went wrong while connecting to the SQLite database.");
            Util.sendPluginMessage("&cError message: "+e.getMessage());
        }
    }

    @Override
    public void disconnect() {
        try(Connection connection = dataSource.getConnection()) {
            Util.sendPluginMessage("&aDisconnected from the SQLite database...");
        } catch (SQLException e) {

        }
    }

    @Override
    public void loadData() {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM data");
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()) {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(rs.getString("user"));
                String string = rs.getString("activeCooldown");
                String[] splits = string.split("_");
                for(String s : splits) {
                    String[] c = s.split(";");
                    RandomLootChestMain.getInstance().getCooldownManager().setCooldown(offlinePlayer.getPlayer(),
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
        try (Connection connection = dataSource.getConnection()) {
            List<PlayerData> players = RandomLootChestMain.getInstance().getCooldownManager().getPlayers();
            players.forEach(playerData -> {
                if (!exists(playerData.getPlayer())) {
                    // Insert player to database
                }
                String line = "";
                for (Map.Entry<Location, AtomicInteger> entry : playerData.getCooldown().entrySet()) {
                    line = line+Util.getLocationString(entry.getKey())+";"+entry.getValue().get()+"_";
                }
                PreparedStatement ps = null;
                try {
                    ps = connection.prepareStatement("UPDATE data SET activeCooldown = '"+line+"' WHERE user = '"+playerData.getPlayer()+"'");
                    ps.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (SQLException e){
            Util.sendPluginMessage("&cSomething went wrong while saving data!");
        }
    }

    private boolean exists(OfflinePlayer player) {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT user FROM data WHERE user='"+player.getName()+"'");
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
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS " +
                    "data(user TEXT PRIMARY KEY, activeCooldown varchar(400));");
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            Util.sendPluginMessage("&aCreated table for users and their active cooldowns!");
        }
    }

    private static class SQLiteDataSource implements DataSource {

        private final Connection connection;
        private final File databaseFile = new File(RandomLootChestMain.getInstance().getDataFolder(), "database.db");
        private final String URL = "jdbc:sqlite:"+databaseFile;

        private Logger logger = Logger.getLogger("Minecraft");

        private int timeOutSeconds;

        public SQLiteDataSource() {
            Util.tryCreateFile(databaseFile);
            try {
                connection = DriverManager.getConnection(URL);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public Connection getConnection() throws SQLException {
            return connection;
        }

        @Override
        public Connection getConnection(String username, String password) throws SQLException {
            return connection;
        }

        @Override
        public PrintWriter getLogWriter() throws SQLException {
            return null;
        }

        @Override
        public void setLogWriter(PrintWriter out) throws SQLException {
            // To do nothing I guess?
        }

        @Override
        public void setLoginTimeout(int seconds) throws SQLException {
            this.timeOutSeconds = seconds;
        }

        @Override
        public int getLoginTimeout() throws SQLException {
            return timeOutSeconds;
        }

        @Override
        public Logger getParentLogger() throws SQLFeatureNotSupportedException {
            return logger;
        }

        @Override
        public <T> T unwrap(Class<T> iface) throws SQLException {
            return null;
        }

        @Override
        public boolean isWrapperFor(Class<?> iface) throws SQLException {
            return false;
        }
    }
}