package me.dreamdevs.randomlootchest;

import lombok.Getter;
import lombok.Setter;
import me.dreamdevs.randomlootchest.api.Config;
import me.dreamdevs.randomlootchest.api.Language;
import me.dreamdevs.randomlootchest.api.RandomLootChestApi;
import me.dreamdevs.randomlootchest.commands.CommandHandler;
import me.dreamdevs.randomlootchest.hooks.*;
import me.dreamdevs.randomlootchest.listeners.PlayerListeners;
import me.dreamdevs.randomlootchest.objects.WandItem;
import me.dreamdevs.randomlootchest.database.Database;
import me.dreamdevs.randomlootchest.listeners.CombatListener;
import me.dreamdevs.randomlootchest.listeners.PlayerInteractListener;
import me.dreamdevs.randomlootchest.managers.*;
import me.dreamdevs.randomlootchest.managers.tasks.CombatTask;
import me.dreamdevs.randomlootchest.managers.tasks.LocationTask;
import me.dreamdevs.randomlootchest.api.utils.Util;
import me.dreamdevs.randomlootchest.api.utils.VersionUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Stream;

@Getter
public class RandomLootChestMain extends JavaPlugin {

    private CooldownManager cooldownManager;
    private LocationManager locationManager;
    private Database databaseManager;
    private ChestsManager chestsManager;
    private ItemsManager itemsManager;
    private CombatManager combatManager;
    private ExtensionManager extensionManager;
    private CommandHandler commandHandler;
    private static @Getter @Setter RandomLootChestMain instance;

    // Files
    private final File itemsFile = new File(getDataFolder(), "items.yml");
    private final File locationsFile = new File(getDataFolder(),"locations.yml");

    @Override
    public void onEnable() {
        setInstance(this);
        RandomLootChestApi.registerApi(this);

        new VersionUtil();

        loadConfig();
        loadLanguage();

        if (!itemsFile.exists()) {
            this.saveResource("items.yml", true);
        }

        if (!locationsFile.exists()) {
            this.saveResource("locations.yml", true);
        }

        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceholderAPIHook().register();
        }

        if (getServer().getPluginManager().getPlugin("MMOItems") != null) {
            new MMOItemsHook();
        }

        if (getServer().getPluginManager().getPlugin("MythicMobs") != null) {
            new MythicMobsHook();
        }

        this.itemsManager = new ItemsManager();

        this.cooldownManager = new CooldownManager();
        this.locationManager = new LocationManager();
        this.chestsManager = new ChestsManager(this);
        this.combatManager = new CombatManager();
        this.commandHandler = new CommandHandler(this);

        this.extensionManager = new ExtensionManager(this);
        this.extensionManager.loadExtensions();

        if (Config.USE_DATABASE.toBoolean()) {
            this.databaseManager = new Database();
            databaseManager.connect(Config.DATABASE_TYPE.toString());
            databaseManager.loadData();
            databaseManager.autoSaveData();
        }

        WandItem.loadVars();

        getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerListeners(), this);

        if (Config.USE_COMBAT_BLOCKER.toBoolean()) {
            getServer().getPluginManager().registerEvents(new CombatListener(), this);
            new CombatTask();
        }

        new LocationTask();

        Metrics metrics = new Metrics(this, 16175);
        metrics.addCustomChart(new Metrics.SimplePie("servers_using_extensions", () -> String.valueOf(getExtensionManager().getEnabledExtensions().size())));

        if (Config.USE_UPDATE_CHECKER.toBoolean()) {
            Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> new UpdateChecker(RandomLootChestMain.getInstance(), 100851).getVersion(version -> {
                if (getDescription().getVersion().equals(version)) {
                    Util.sendPluginMessage("");
                    Util.sendPluginMessage("&aYour version is up to date!");
                    Util.sendPluginMessage("&aYour version: " + getDescription().getVersion());
                    Util.sendPluginMessage("");
                } else {
                    Util.sendPluginMessage("");
                    Util.sendPluginMessage("&aThere is new RandomLootChest version!");
                    Util.sendPluginMessage("&aYour version: " + getDescription().getVersion());
                    Util.sendPluginMessage("&aNew version: " + version);
                    Util.sendPluginMessage("");
                }
            }), 10L, 20L * 300);
        }
    }

    @Override
    public void onDisable() {
        getCooldownManager().getLocations().keySet().forEach(location -> location.getBlock().setType(Material.CHEST));

        getExtensionManager().disableExtensions();
        if(Config.USE_DATABASE.toBoolean()) {
            databaseManager.saveData();
            databaseManager.disconnect();
        }
        getLocationManager().save();
    }

    public void loadConfig() {
        File config = new File(getDataFolder(), "config.yml");
        Util.tryCreateFile(config);

        YamlConfiguration conf = YamlConfiguration.loadConfiguration(config);
        Stream.of(Config.values()).filter(setting -> conf.getString(setting.getPath()) == null)
                .forEach(setting -> conf.set(setting.getPath(), setting.getDefault()));

        Config.setFile(conf);
        try {
            conf.save(config);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadLanguage() {
        File config = new File(getDataFolder(), "language.yml");
        Util.tryCreateFile(config);

        YamlConfiguration lang = YamlConfiguration.loadConfiguration(config);
        Stream.of(Language.values()).filter(setting -> lang.getString(setting.getPath()) == null)
                .forEach(setting -> lang.set(setting.getPath(), setting.getDefault()));

        Language.setFile(lang);
        try {
            lang.save(config);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}