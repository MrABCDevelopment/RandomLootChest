package me.dreamdevs.github.randomlootchest.api;

import com.github.sirblobman.combatlogx.api.ICombatLogX;
import lombok.experimental.UtilityClass;
import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.hooks.CombatLogXHook;
import me.dreamdevs.github.randomlootchest.hooks.HolographicDisplaysHook;
import me.dreamdevs.github.randomlootchest.hooks.VaultHook;
import me.dreamdevs.github.randomlootchest.utils.ConsoleUtil;
import me.dreamdevs.github.randomlootchest.utils.Settings;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@UtilityClass
public class HooksAPI {

    private static VaultHook vaultHook;
    private static HolographicDisplaysHook holographicDisplaysHook;
    private static CombatLogXHook combatLogXHook;

    public static void hook(RandomLootChestMain plugin) {
        if (plugin.getServer().getPluginManager().getPlugin("Vault") != null) {
            vaultHook = new VaultHook(plugin);
            ConsoleUtil.sendPluginMessage("&aHooked with Vault!");
        }
        if (plugin.getServer().getPluginManager().getPlugin("HolographicDisplays") != null) {
            holographicDisplaysHook = new HolographicDisplaysHook(plugin);
            ConsoleUtil.sendPluginMessage("&aHooked with HolographicDisplays");
        }
        if(plugin.getServer().getPluginManager().getPlugin("CombatLogX") != null) {
            combatLogXHook = new CombatLogXHook(plugin);
            ConsoleUtil.sendPluginMessage("&aHooked with CombatLogX");
        }
    }

    public static ICombatLogX getAPI() {
        return combatLogXHook.getAPI();
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

    public static boolean isInCombat(Player player) {
        if (combatLogXHook == null) return false;
        return getAPI().getCombatManager().isInCombat(player) && Settings.openChestInCombat;
    }

}