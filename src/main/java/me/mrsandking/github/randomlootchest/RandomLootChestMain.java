package me.mrsandking.github.randomlootchest;

import lombok.Getter;
import me.mrsandking.github.randomlootchest.commands.ChestCommand;
import me.mrsandking.github.randomlootchest.listener.PlayerDeathListener;
import me.mrsandking.github.randomlootchest.listener.PlayerInteractListener;
import me.mrsandking.github.randomlootchest.listener.PlayerJoinListener;
import me.mrsandking.github.randomlootchest.listener.PlayerQuitListener;
import me.mrsandking.github.randomlootchest.manager.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class RandomLootChestMain extends JavaPlugin {

    private @Getter ConfigManager configManager;
    private @Getter CooldownManager cooldownManager;
    private @Getter MessagesManager messagesManager;
    private @Getter GameManager gameManager;
    private @Getter PlayerManager playerManager;
    private @Getter StarterManager starterManager;
    private @Getter LocationManager locationManager;
    private static @Getter RandomLootChestMain instance;

    @Override
    public void onEnable() {
        instance = this;

        this.configManager = new ConfigManager(this);
        this.configManager.loadConfigFiles("config.yml", "messages.yml", "locations.yml");

        File data = new File(getDataFolder(), "users");
        if(!data.exists()) data.mkdirs();

        this.messagesManager = new MessagesManager(this);
        this.gameManager = new GameManager(this);
        this.playerManager = new PlayerManager();
        this.locationManager = new LocationManager(this);
        this.starterManager = new StarterManager();

        this.cooldownManager = new CooldownManager();

        new ChestCommand(this);

        getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
    }

    @Override
    public void onDisable() {
        getLocationManager().save();
    }
}