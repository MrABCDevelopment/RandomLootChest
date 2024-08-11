package me.dreamdevs.randomlootchest.database;

import me.dreamdevs.randomlootchest.api.Config;
import me.dreamdevs.randomlootchest.api.database.IDatabase;
import me.dreamdevs.randomlootchest.RandomLootChestMain;
import me.dreamdevs.randomlootchest.api.util.Util;
import me.dreamdevs.randomlootchest.database.data.PlayerData;
import org.bukkit.Location;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class DatabaseMySQL implements IDatabase {

	private final String URL = "jdbc:mysql://" + Config.DATABASE_HOST.toString() + ":" + Config.DATABASE_PORT.toInt() + "/" + Config.DATABASE_NAME.toString()+"?useSSL=false";
	private final String user = Config.DATABASE_USER.toString();
	private final String password = Config.DATABASE_PASSWORD.toString();
	private Connection connection;

	@Override
	public void connect() {
		try {
			connection = DriverManager.getConnection(URL, user, password);
			createTable();
		} catch (Exception e) {
			Util.sendPluginMessage("&cSomething went wrong while connecting to the MySQL database.");
			Util.sendPluginMessage("&cError message: "+e.getMessage());
		}
	}

	@Override
	public void disconnect() {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
				Util.sendPluginMessage("&aDisconnected from the MySQL database...");
			}
		} catch (SQLException e) {
			Util.sendPluginMessage("&cCouldn't close connection!");
			Util.sendPluginMessage("&cException message: "+e.getMessage());
		}
	}

	@Override
	public void loadData() {
		try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM data")) {
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				UUID uuid = UUID.fromString(rs.getString("UserUuid"));
				String string = rs.getString("activeCooldown");
				if (string.isEmpty() || string.isBlank()) {
					continue;
				}
				String[] splits = string.split("_");
				for(String s : splits) {
					String[] c = s.split(";");
					RandomLootChestMain.getInstance().getCooldownManager().setCooldown(uuid,
							Util.getStringLocation(c[0]), Integer.parseInt(c[1]), false);
				}
			}
			Util.sendPluginMessage("&aLoaded users data!");
		} catch (SQLException e) {
			Util.sendPluginMessage("&cSomething went wrong while loading users data!");
			Util.sendPluginMessage("&cException message: "+e.getMessage());
		}
	}

	@Override
	public void saveData() {
		List<PlayerData> players = RandomLootChestMain.getInstance().getCooldownManager().getPlayers();
		players.forEach(playerData -> {
			if (!exists(playerData.getUuid())) {
				// Insert player to database
				try (PreparedStatement ps = connection.prepareStatement("INSERT INTO data(UserUuid, activeCooldown) VALUES(?,?);")) {
					ps.setObject(1, playerData.getUuid());
					ps.setString(2, "null");
					ps.executeUpdate();
				} catch (SQLException e) {
					Util.sendPluginMessage("&cSomething went wrong while creating user account in database!");
					Util.sendPluginMessage("&cException message: "+e.getMessage());
				}
			}
			StringBuilder stringBuilder = new StringBuilder();
			for (Map.Entry<Location, AtomicInteger> entry : playerData.getCooldown().entrySet()) {
				stringBuilder.append(Util.getLocationString(entry.getKey())).append(";").append(entry.getValue().get()).append("_");
			}
			try (PreparedStatement ps = connection.prepareStatement("UPDATE data SET activeCooldown = '"+stringBuilder.toString()+"' WHERE UserUuid = '"+playerData.getUuid()+"'")) {
				ps.executeUpdate();
			} catch (SQLException e) {
				Util.sendPluginMessage("&cSomething went wrong while saving/updating data in database!");
				Util.sendPluginMessage("&cException message: "+e.getMessage());
			}
		});
	}

	private boolean exists(UUID uuid) {
		try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT UserUuid FROM data WHERE UserUuid='"+uuid+"'")) {
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
		try (PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS data(`UserUuid` VARCHAR(36) NULL DEFAULT NULL , `activeCooldown` VARCHAR(400) NULL DEFAULT NULL , PRIMARY KEY (`UserUuid`))")) {
			preparedStatement.execute();
			Util.sendPluginMessage("&aCreated table for users and their active cooldowns!");
		} catch (SQLException e) {
			Util.sendPluginMessage("&cCould not created table for users!");
			Util.sendPluginMessage("&cException message: "+e.getMessage());
		}
	}

}