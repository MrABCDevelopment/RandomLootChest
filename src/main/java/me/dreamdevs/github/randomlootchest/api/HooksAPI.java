package me.dreamdevs.github.randomlootchest.api;

import lombok.experimental.UtilityClass;
import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.api.hooks.HolographicDisplaysHook;
import me.dreamdevs.github.randomlootchest.api.hooks.VaultHook;
import me.dreamdevs.github.randomlootchest.utils.Util;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@UtilityClass
public class HooksAPI {

    private static VaultHook vaultHook;
    private static HolographicDisplaysHook holographicDisplaysHook;

    public static void hook(RandomLootChestMain plugin) {
        if (plugin.getServer().getPluginManager().getPlugin("Vault") != null) {
            vaultHook = new VaultHook(plugin);
            Util.sendPluginMessage("&aHooked with Vault!");
        }
        if (plugin.getServer().getPluginManager().getPlugin("HolographicDisplays") != null) {
            holographicDisplaysHook = new HolographicDisplaysHook(plugin);
            Util.sendPluginMessage("&aHooked with HolographicDisplays");
        }
    }

    public static HolographicDisplaysHook getHolographicDisplaysHook() {
        return holographicDisplaysHook;
    }

    public static VaultHook getVaultHook() {
        return vaultHook;
    }

    public static void createTempHolo(Player player, Location location) {
        getHolographicDisplaysHook().createTempHolo(player, location);
    }

    public static void addMoney(Player player, double money) {
        getVaultHook().addMoney(player, money);
    }

    public static void removeMoney(Player player, double money) {
        getVaultHook().removeMoney(player, money);
    }

}