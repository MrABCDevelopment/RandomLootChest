package me.dreamdevs.github.randomlootchest.hooks;

import com.github.sirblobman.combatlogx.api.ICombatLogX;
import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class CombatLogXHook {

    private RandomLootChestMain plugin;

    public CombatLogXHook(RandomLootChestMain plugin) {
        this.plugin = plugin;
    }

    public boolean isEnabled() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        return pluginManager.isPluginEnabled("CombatLogX");
    }

    public ICombatLogX getAPI() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        Plugin plugin = pluginManager.getPlugin("CombatLogX");
        return (ICombatLogX) plugin;
    }

}