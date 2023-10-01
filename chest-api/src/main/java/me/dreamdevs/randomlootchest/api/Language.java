package me.dreamdevs.randomlootchest.api;

import me.dreamdevs.randomlootchest.api.utils.ColourUtil;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Objects;

public enum Language {

	// Chest messages
	CHEST_COOLDOWN_MESSAGE("ChestMessages.Cooldown-Message","&cHey! You have to wait %TIME% until you open this chest!"),
	CHEST_PLACE_ON_MAP_MESSAGE("ChestMessages.Place-On-Map-Message","&aYou placed %TYPE% Chest &aat location: %LOCATION%"),
	CHEST_REMOVE_FROM_MAP_MESSAGE("ChestMessages.Remove-From-Map-Message","&aYou removed %TYPE% Chest &afrom map!"),

	// General messages like combat messages, commands information
	GENERAL_COMBAT_MESSAGE("GeneralMessages.Combat-Message","&cYou are in combat for %TIME%!"),
	GENERAL_COMBAT_EXPIRED("GeneralMessages.Combat-Expired","&aYou are no longer in combat!"),
	GENERAL_LOADED_MESSAGES("GeneralMessages.Loaded-Messages","&aLoaded %AMOUNT% messages!"),
	GENERAL_NOT_PLAYER("GeneralMessages.Not-Player","&cConsole cannot perform this command!"),
	GENERAL_LOCATIONS_SAVED("GeneralMessages.Locations-Saved","&aLocations saved!"),
	GENERAL_NO_PERMISSION("GeneralMessages.No-Permission","&cYou do not have permission to perform this command!"),
	GENERAL_NO_ARGUMENT("GeneralMessages.No-Argument","&cThis argument does not exist!"),
	GENERAL_NO_ARGUMENTS("GeneralMessages.No-Arguments","&cThere are no arguments for this sub command!"),
	GENERAL_NO_CHEST("GeneralMessages.No-Chest","&cCouldn't find any chest!"),
	GENERAL_NO_EXTENSION("GeneralMessages.No-Extension","&cCouldn't find extension with that name."),
	GENERAL_EXTENSION_CONFIG_RELOADED("GeneralMessages.Extension-Config-Reloaded","&aReloaded %EXTENSION_NAME% configuration file!"),

	ITEMS_NO_ID("ItemsMessages.No-Id","&cNo Id for new item..."),
	ITEMS_SET_CHANCE("ItemsMessages.Set-Chance","&cYou must set chance!"),
	ITEMS_NO_ITEM("ItemsMessages.No-Item","&cYou cannot add air to the items..."),
	ITEMS_EXISTS("ItemsMessages.Item-Exists","&cItem with this Id exist!"),
	ITEMS_NOT_EXISTS("ItemsMessages.Item-Not-Exists","&cItem with this Id does not exist!"),
	ITEMS_ADDED_ITEM("ItemsMessages.Added-Item","&aYou added new item to items!"),
	ITEMS_REMOVED_ITEM("ItemsMessages.Removed-Item","&aYou removed item from items!"),

	COMMAND_CORRECT_USAGE("Commands.Correct-Usage","&cCorrect usage /randomlootchest <subcommand>"),
	COMMAND_RELOAD_FILES("Commands.Reload-Files","&aReloaded all configuration files!"),
	COMMAND_RELOAD_MESSAGES("Commands.Reload-Messages","&aReloaded messages!"),
	COMMAND_RELOAD_ITEMS("Commands.Reload-Items","&aReloaded items (items.yml)!"),
	COMMAND_RELOAD_CHESTS("Commands.Reload-Chests","&aReloaded all chests!"),
	COMMAND_RELOAD_CONFIG("Commands.Reload-Config","&aReloaded main configuration file (config.yml)!"),

	MENU_CHEST_RELOAD_TITLE("Menu.Chest-Reload.Title","Reload Menu"),
	MENU_CHEST_RELOAD_ALL_CHESTS("Menu.Chest-Reload.Reload-All-Chests-Name","&aReload Chests"),
	MENU_CHEST_RELOAD_CONFIG("Menu.Chest-Reload.Reload-Config-Name","&aReload config.yml"),
	MENU_CHEST_RELOAD_MESSAGES("Menu.Chest-Reload.Reload-Messages-Name","&aReload messages.yml"),
	MENU_CHEST_RELOAD_ITEMS("Menu.Chest-Reload.Reload-Items-Name","&aReload items.yml"),
	MENU_CHEST_RELOAD_ALL("Menu.Chest-Reload.Reload-All-Name","&aReload All"),

	MENU_EXTENSIONS_TITLE("Menu.Extensions.Title","Extensions Menu"),

	MENU_PLACE_CHEST_TITLE("Menu.Place-Chest.Title","Place Chest"),

	MENU_CHESTS_TITLE("Menu.Chests.Title","Chests Menu"),

	MENU_ITEMS_TITLE("Menu.Items.Title","Items Menu");

	private final String path;
	private final Object def;
	private static YamlConfiguration CONFIG;

	/**
	 * Lang enum constructor
	 */
	Language(String path, Object start) {
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

	public static void reloadLanguage() {
		try {
			CONFIG.load("language.yml");
		} catch (Exception e) {

		}
	}

	@Override
	public String toString() {
		return ColourUtil.colorize(Objects.requireNonNull(CONFIG.getString(getPath())));
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
