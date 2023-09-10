package me.dreamdevs.randomlootchest;

import lombok.Getter;
import lombok.Setter;
import me.dreamdevs.randomlootchest.api.RandomLootChestApi;
import me.dreamdevs.randomlootchest.commands.CommandHandler;
import me.dreamdevs.randomlootchest.listeners.PlayerListeners;
import me.dreamdevs.randomlootchest.objects.WandItem;
import me.dreamdevs.randomlootchest.database.Database;
import me.dreamdevs.randomlootchest.listeners.CombatListener;
import me.dreamdevs.randomlootchest.listeners.PlayerInteractListener;
import me.dreamdevs.randomlootchest.managers.*;
import me.dreamdevs.randomlootchest.managers.tasks.CombatTask;
import me.dreamdevs.randomlootchest.managers.tasks.LocationTask;
import me.dreamdevs.randomlootchest.utils.Settings;
import me.dreamdevs.randomlootchest.utils.Util;
import me.dreamdevs.randomlootchest.utils.VersionUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class RandomLootChestMain extends JavaPlugin {

    private ConfigManager configManager;
    private CooldownManager cooldownManager;
    private MessagesManager messagesManager;
    private LocationManager locationManager;
    private Database databaseManager;
    private ChestsManager chestsManager;
    private ItemsManager itemsManager;
    private CombatManager combatManager;
    private ExtensionManager extensionManager;
    private CommandHandler commandHandler;
    private static @Getter @Setter RandomLootChestMain instance;

    @Override
    public void onEnable() {
        setInstance(this);
        RandomLootChestApi.registerApi(this);

        new VersionUtil();

        this.configManager = new ConfigManager(this);
        this.configManager.loadConfigFiles("config.yml", "messages.yml", "locations.yml", "items.yml");
        this.itemsManager = new ItemsManager();

        Settings.loadVars();
        this.cooldownManager = new CooldownManager();
        this.locationManager = new LocationManager();
        this.chestsManager = new ChestsManager(this);
        this.messagesManager = new MessagesManager(this);
        this.combatManager = new CombatManager();
        this.commandHandler = new CommandHandler(this);

        this.extensionManager = new ExtensionManager(this);
        this.extensionManager.loadExtensions();

        if(Settings.useDatabase) {
            this.databaseManager = new Database();
            databaseManager.connect(Settings.databaseType);
            databaseManager.loadData();
            databaseManager.autoSaveData();
        }

        WandItem.loadVars();

        getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerListeners(), this);

        if(Settings.combatEnabled) {
            getServer().getPluginManager().registerEvents(new CombatListener(), this);
            new CombatTask();
        }

        new LocationTask();

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
            databaseManager.saveData();
            databaseManager.disconnect();
        }
        getLocationManager().save();
    }

}