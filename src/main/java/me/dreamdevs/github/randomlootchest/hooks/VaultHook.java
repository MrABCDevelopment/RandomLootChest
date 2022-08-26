/**
 * Vault supports RandomLootChest since update 1.4.0
 * The things you can do with Vault in RandomLootChest:
 * - add or remove money to drop in chests
 */

package me.dreamdevs.github.randomlootchest.hooks;

import lombok.Getter;
import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultHook {

    private RandomLootChestMain plugin;
    private @Getter static Economy economy;

    public VaultHook(RandomLootChestMain randomLootChestMain) {
        this.plugin = randomLootChestMain;
        setupEconomy();
    }

    private boolean setupEconomy() {

        RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Hooking with Vault... (Economy)");
        economy = rsp.getProvider();
        return economy != null;
    }

    public void addMoney(Player player, double amount) {
        economy.depositPlayer(player, amount);
    }

    public void removeMoney(Player player, double amount) {
        String t = String.valueOf(amount);
        String[] split = t.split("-");
        economy.withdrawPlayer(player, Double.valueOf(split[1]));
    }

}