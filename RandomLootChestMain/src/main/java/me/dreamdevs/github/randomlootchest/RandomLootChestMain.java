package me.dreamdevs.github.randomlootchest;

import lombok.Getter;
import me.dreamdevs.github.randomlootchest.api.HooksAPI;
import me.dreamdevs.github.randomlootchest.commands.CommandHandler;
import me.dreamdevs.github.randomlootchest.database.Database;
import me.dreamdevs.github.randomlootchest.listeners.*;
import me.dreamdevs.github.randomlootchest.managers.*;
import me.dreamdevs.github.randomlootchest.api.objects.WandItem;
import me.dreamdevs.github.randomlootchest.managers.tasks.CombatTask;
import me.dreamdevs.github.randomlootchest.managers.tasks.LocationTask;
import me.dreamdevs.github.randomlootchest.utils.Settings;
import me.dreamdevs.github.randomlootchest.utils.Util;
import me.dreamdevs.github.randomlootchest.utils.VersionUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class RandomLootChestMain extends JavaPlugin {

    private ConfigManager configManager;
    private CooldownManager cooldownManager;
    private MessagesManager messagesManager;
    private GameManager gameManager;
    private StarterManager starterManager;
    private LocationManager locationManager;
    private Database databaseManager;
    private ChestsManager chestsManager;
    private ItemsManager itemsManager;
    private CombatManager combatManager;
    private ExtensionManager extensionManager;
    private CommandHandler commandHandler;
    private static @Getter RandomLootChestMain instance;

    @Override
    public void onEnable() {
        instance = this;

        new VersionUtil();

        this.configManager = new ConfigManager(this);
        this.configManager.loadConfigFiles("config.yml", "messages.yml", "locations.yml", "starter.yml", "items.yml");
        this.itemsManager = new ItemsManager();

        Settings.loadVars();
        this.cooldownManager = new CooldownManager();
        this.locationManager = new LocationManager();
        this.chestsManager = new ChestsManager(this);
        this.messagesManager = new MessagesManager(this);
        this.gameManager = new GameManager();
        this.starterManager = new StarterManager();
        this.combatManager = new CombatManager();
        this.commandHandler = new CommandHandler(this);
        HooksAPI.hook(this);

        this.extensionManager = new ExtensionManager(this);

        if(Settings.useDatabase) {
            this.databaseManager = new Database();
            databaseManager.connect(Settings.databaseType);
            databaseManager.loadData();
            databaseManager.autoSaveData();
        }

        WandItem.loadVars();

        getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        getServer().getPluginManager().registerEvents(new CombatListener(), this);

        new LocationTask();
        new CombatTask();

        new Metrics(this, 16175);
        if(Settings.updateChecker) {
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
        getExtensionManager().disableExtensions();
        if(Settings.useDatabase) {
            databaseManager.disconnect();
        }
        getLocationManager().save();
    }

}