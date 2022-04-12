package me.mrsandking.github.randomlootchest.util;

import lombok.Getter;
import me.mrsandking.github.randomlootchest.RandomLootChestMain;
import org.bukkit.ChatColor;

@Getter
public class Settings {

    public static boolean randomChests;
    public static boolean activeParticles;
    public static String particleType;
    public static int particleAmount;
    public static String wandItemDisplayName;
    public static boolean wandItemPermissionToUse;

    public Settings(RandomLootChestMain plugin) {
        randomChests = plugin.getConfigManager().getConfig("config.yml").getBoolean("random-chests");
        activeParticles = plugin.getConfigManager().getConfig("config.yml").getBoolean("particles.active");
        particleType = plugin.getConfigManager().getConfig("config.yml").getString("particles.type").toUpperCase();
        particleAmount = plugin.getConfigManager().getConfig("config.yml").getInt("particles.amount");
        wandItemDisplayName = ChatColor.translateAlternateColorCodes('&', plugin.getConfigManager().getConfig("config.yml").getString("wand-item.displayName"));
        wandItemPermissionToUse = plugin.getConfigManager().getConfig("config.yml").getBoolean("wand-item.permission-to-use");
    }

}