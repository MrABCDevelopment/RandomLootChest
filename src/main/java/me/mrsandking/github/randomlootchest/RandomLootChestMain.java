package me.mrsandking.github.randomlootchest;

import lombok.Getter;
import me.mrsandking.github.randomlootchest.commands.CommandHandler;
import me.mrsandking.github.randomlootchest.database.Database;
import me.mrsandking.github.randomlootchest.hooks.HolographicDisplaysHook;
import me.mrsandking.github.randomlootchest.hooks.VaultHook;
import me.mrsandking.github.randomlootchest.listener.InventoryListener;
import me.mrsandking.github.randomlootchest.listener.PlayerDeathListener;
import me.mrsandking.github.randomlootchest.listener.PlayerInteractListener;
import me.mrsandking.github.randomlootchest.listener.PlayerJoinListener;
import me.mrsandking.github.randomlootchest.manager.*;
import me.mrsandking.github.randomlootchest.objects.WandItem;
import me.mrsandking.github.randomlootchest.util.Settings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class RandomLootChestMain extends JavaPlugin {

    private @Getter ConfigManager configManager;
    private @Getter CooldownManager cooldownManager;
    private @Getter MessagesManager messagesManager;
    private @Getter GameManager gameManager;
    private @Getter StarterManager starterManager;
    private @Getter LocationManager locationManager;
    private @Getter Database databaseManager;
    private @Getter Settings settings;
    private @Getter ChestsManager chestsManager;
    private static @Getter RandomLootChestMain instance;

    private @Getter VaultHook vaultHook;

    private @Getter HolographicDisplaysHook holographicDisplaysHook;

    @Override
    public void onEnable() {
        instance = this;

        this.configManager = new ConfigManager(this);
        this.configManager.loadConfigFiles("config.yml", "messages.yml", "locations.yml", "chests.yml");
        this.settings = new Settings(this);
        this.cooldownManager = new CooldownManager();
        this.locationManager = new LocationManager(this);
        this.chestsManager = new ChestsManager(this);
        this.messagesManager = new MessagesManager(this);
        this.gameManager = new GameManager(this);
        this.starterManager = new StarterManager();
        if (getServer().getPluginManager().getPlugin("Vault") != null) {
            this.vaultHook = new VaultHook(this);
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Hooked with Vault!");
        }
        if (getServer().getPluginManager().getPlugin("HolographicDisplays") != null) {
            this.holographicDisplaysHook = new HolographicDisplaysHook(this);
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Hooked with HolographicDisplays!");
        }

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
    }

    @Override
    public void onDisable() {
        if(getConfigManager().getConfig("config.yml").getBoolean("use-database")) {
            databaseManager.disconnect();
        }
        getLocationManager().save();
    }
}