package me.dreamdevs.github.randomlootchest.api;

import lombok.experimental.UtilityClass;
import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.api.hooks.HolographicDisplaysHook;
import me.dreamdevs.github.randomlootchest.api.hooks.PlaceholderAPIHook;
import me.dreamdevs.github.randomlootchest.utils.Util;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@UtilityClass
public class HooksAPI {

    private static HolographicDisplaysHook holographicDisplaysHook;

    public static void hook(RandomLootChestMain plugin) {
        if (plugin.getServer().getPluginManager().getPlugin("HolographicDisplays") != null) {
            holographicDisplaysHook = new HolographicDisplaysHook(plugin);
            Util.sendPluginMessage("&aHooked with HolographicDisplays");
        }
        if(plugin.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceholderAPIHook(plugin);
            Util.sendPluginMessage("&aHooked with PlaceholderAPI!");
        }
    }

    public static HolographicDisplaysHook getHolographicDisplaysHook() {
        return holographicDisplaysHook;
    }

    public static void createTempHolo(Player player, Location location) {
        getHolographicDisplaysHook().createTempHolo(player, location);
    }

}