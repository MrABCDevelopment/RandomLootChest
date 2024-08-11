package me.dreamdevs.randomlootchest.api;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.util.List;

public enum Config {

	// General settings, update checker, random chests
	USE_UPDATE_CHECKER("GeneralSettings.Use-Update-Checker", true),
	USE_RANDOM_CHESTS("GeneralSettings.Use-Random-Chests", false),
	USE_PERSONAL_COOLDOWN("GeneralSettings.Use-Personal-Cooldown", true),
	USE_ROUNDED_COOLDOWN_FORMAT("GeneralSettings.Use-Rounded-Cooldown-Format", false),

	// Combat settings
	USE_COMBAT_BLOCKER("GeneralSettings.Use-Combat-Blocker", true),
	USE_COMBAT_ACTION_BAR("GeneralSettings.Use-Combat-Action-Bar", true),
	COMBAT_BLOCKER_TIMER("GeneralSettings.Combat-Blocker-Timer", 20),

	USE_RANDOM_SPAWN_CHESTS("GeneralSettings.Use-Random-Spawn-Chests", true),
	RANDOM_SPAWN_CHESTS_RANDOMIZE("GeneralSettings.Randomize-Chests", true),
	RANDOM_SPAWN_CHESTS_LIST("GeneralSettings.List-Chests", List.of("default")),
	RANDOM_SPAWN_CHESTS_WORLDS("GeneralSettings.Worlds", List.of("world")),
	RANDOM_SPAWN_CHESTS_MIN_X("GeneralSettings.World.MinX", -1000),
	RANDOM_SPAWN_CHESTS_MAX_X("GeneralSettings.World.MaxX", 1000),
	RANDOM_SPAWN_CHESTS_MIN_Z("GeneralSettings.World.MinZ", -1000),
	RANDOM_SPAWN_CHESTS_MAX_Z("GeneralSettings.World.MaxZ", 1000),

	// Database section
	USE_DATABASE("Database.Use-Database", false),
	DATABASE_AUTO_SAVE_TIME("Database.Auto-Save-Time",300),
	DATABASE_TYPE("Database.Type", "YAML"),
	DATABASE_USER("Database.User", "user"),
	DATABASE_PASSWORD("Database.Password", "password"),
	DATABASE_HOST("Database.Host", "localhost"),
	DATABASE_PORT("Database.Port", 3306),
	DATABASE_NAME("Database.Database-Name", "randomlootchest"),

	// Wand item section
	WAND_ITEM_DISPLAY_NAME("Wand-Item.DisplayName", "&6Chest Wand"),
	USE_WAND_ITEM_PERMISSION("Wand-Item.Use-Permission", true);


	private final String path;
	private final Object def;
	private static YamlConfiguration CONFIG;

	/**
	 * Lang enum constructor
	 */
	Config(String path, Object start) {
		this.path = path;
		this.def = start;
	}

	/**
	 * Set the {@code YamlConfiguration} to use.
	 *
	 * @param config
	 * The config to set.
	 */
	public static void setFile(YamlConfiguration config) {
		CONFIG = config;
	}

	public static void reloadFile() {
		try {
			CONFIG.load("config.yml");
		} catch (IOException | InvalidConfigurationException e) {

		}
	}

	@Override
	public String toString() {
		return CONFIG.getString(getPath());
	}

	public boolean toBoolean() {
		return CONFIG.getBoolean(getPath());
	}

	public int toInt() {
		return CONFIG.getInt(getPath());
	}

	public List<String> toList() {
		return CONFIG.getStringList(getPath());
	}

	/**
	 * Get the default value of the path.
	 *
	 * @return The default value of the path.
	 */
	public Object getDefault() {
		return this.def;
	}

	/**
	 * Get the path to the string.
	 *
	 * @return The path to the string.
	 */
	public String getPath() {
		return this.path;
	}
}
