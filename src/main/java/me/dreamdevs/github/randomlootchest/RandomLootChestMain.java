package me.dreamdevs.github.randomlootchest;

import lombok.Getter;
import me.dreamdevs.github.randomlootchest.api.HooksAPI;
import me.dreamdevs.github.randomlootchest.commands.CommandHandler;
import me.dreamdevs.github.randomlootchest.database.Database;
import me.dreamdevs.github.randomlootchest.listeners.InventoryListener;
import me.dreamdevs.github.randomlootchest.listeners.PlayerDeathListener;
import me.dreamdevs.github.randomlootchest.listeners.PlayerInteractListener;
import me.dreamdevs.github.randomlootchest.listeners.PlayerJoinListener;
import me.dreamdevs.github.randomlootchest.managers.*;
import me.dreamdevs.github.randomlootchest.objects.WandItem;
import me.dreamdevs.github.randomlootchest.utils.ConsoleUtil;
import me.dreamdevs.github.randomlootchest.utils.Settings;
import me.dreamdevs.github.randomlootchest.utils.VersionUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;

@Getter
public class RandomLootChestMain extends JavaPlugin {

    private ConfigManager configManager;
    private CooldownManager cooldownManager;
    private MessagesManager messagesManager;
    private GameManager gameManager;
    private StarterManager starterManager;
    private LocationManager locationManager;
    private Database databaseManager;
    private Settings settings;
    private ChestsManager chestsManager;
    private static @Getter RandomLootChestMain instance;

    private static HashMap<String, String> versions;

    @Override
    public void onEnable() {
        instance = this;

        new VersionUtil();

        this.configManager = new ConfigManager(this);
        this.configManager.loadConfigFiles("config.yml", "messages.yml", "locations.yml", "starter.yml");

        configManager.getConfigs().forEach((s, configuration) -> {
            if(!isCorrectVersion(s)) {
                File oldDir = new File(getDataFolder(), "old");
                if(!oldDir.exists()) oldDir.mkdirs();
                configuration.getConfigFile().renameTo(new File(oldDir.getPath(), configuration.getConfigFile().getName()));
                this.configManager.loadConfigFile(s);
            }
        });

        this.settings = new Settings(this);
        this.cooldownManager = new CooldownManager();
        this.locationManager = new LocationManager();
        this.chestsManager = new ChestsManager(this);
        this.messagesManager = new MessagesManager(this);
        this.gameManager = new GameManager(this);
        this.starterManager = new StarterManager();
        HooksAPI.hook(this);


        if(Settings.useDatabase) {
            this.databaseManager = new Database();
            databaseManager.connect(Settings.databaseType);
            databaseManager.loadData();
            databaseManager.autoSaveData();
        }

        new WandItem();
        new CommandHandler(this);

        getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);

        new LocationTask();

        new Metrics(this, 16175);
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new Runnable() {
            @Override
            public void run() {
                new UpdateChecker(RandomLootChestMain.getInstance(), 100851).getVersion(version -> {
                   if(getDescription().getVersion().equals(version)) {
                       ConsoleUtil.sendPluginMessage("");
                       ConsoleUtil.sendPluginMessage(ChatColor.GREEN+"There is new RandomLootChest version!");
                       ConsoleUtil.sendPluginMessage(ChatColor.GREEN+"Your version: "+getDescription().getVersion());
                       ConsoleUtil.sendPluginMessage(ChatColor.GREEN+"New version: "+version);
                       ConsoleUtil.sendPluginMessage("");
                   } else {
                       ConsoleUtil.sendPluginMessage("");
                       ConsoleUtil.sendPluginMessage("&aYour version is up to date!");
                       ConsoleUtil.sendPluginMessage("&aYour version: "+getDescription().getVersion());
                       ConsoleUtil.sendPluginMessage("");
                   }
                });
            }
        }, 10L, 20*300);
    }

    @Override
    public void onDisable() {
        if(Settings.useDatabase) {
            databaseManager.disconnect();
        }
        getLocationManager().save();
    }

    public boolean isCorrectVersion(String config) {
        if(getConfigManager().getConfig(config).get("config-version") != null) {
            String versionFromDefaults = getConfigManager().getConfig(config).getString("config-version");
            if(versionFromDefaults.equals(versions.get(config)))
                return true;
            return false;
        }
        return false;
    }

    static {
        versions = new HashMap<>();
        versions.put("config.yml", "1");
        versions.put("messages.yml", "1");
        versions.put("starter.yml", "1");
        versions.put("items.yml", "1");
    }

}