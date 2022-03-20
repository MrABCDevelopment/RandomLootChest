package me.mrsandking.github.randomlootchest.manager;

import me.mrsandking.github.randomlootchest.RandomItem;
import me.mrsandking.github.randomlootchest.RandomLootChestMain;
import me.mrsandking.github.randomlootchest.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GameManager {

    private List<RandomItem> itemStacks;
    private RandomLootChestMain plugin;

    public GameManager(RandomLootChestMain plugin) {
        this.plugin = plugin;
        load();
    }

    public void load() {
        itemStacks = new ArrayList<>();
        for(String key : plugin.getConfigManager().getConfig("config.yml").getStringList("gamechest-items")) {
            String[] params = key.split(":");
            if(params[0].contains("potion")) {
                String[] strings = params[0].split(",");
                RandomItem randomItem = new RandomItem(Util.getPotion(strings[1].toUpperCase(), Integer.parseInt(strings[2]), false, Integer.parseInt(params[1])), Double.parseDouble(params[2]));
                itemStacks.add(randomItem);
            } else if(params[0].equalsIgnoreCase("enchanted_golden_apple")) {
                ItemStack itemStack = null;
                try {
                    itemStack = new ItemStack(Material.GOLDEN_APPLE, 1, (byte) 1);
                } catch (Exception e) {
                    itemStack = new ItemStack(Material.getMaterial(params[0].toUpperCase()));
                }
                itemStack.setAmount(Integer.parseInt(params[1]));
                RandomItem randomItem = new RandomItem(itemStack, Double.parseDouble(params[2]));
                itemStacks.add(randomItem);
            } else if(params[0].contains("splash_potion")) {
                String[] strings = params[0].split(",");
                RandomItem randomItem = new RandomItem(Util.getPotion(strings[0], Integer.parseInt(strings[1]), true, Integer.parseInt(params[1])), Double.parseDouble(params[2]));
                itemStacks.add(randomItem);
            } else {
                ItemStack itemStack = new ItemStack(Material.getMaterial(params[0].toUpperCase()));
                itemStack.setAmount(Integer.parseInt(params[1]));
                if (params.length == 4) {
                    String[] enchantments = params[3].split(";");
                    for (String enchantment : enchantments) {
                        String[] p = enchantment.split(",");
                        itemStack.addUnsafeEnchantment(Enchantment.getByName(p[0].toUpperCase()), Integer.parseInt(p[1]));
                    }
                }
                RandomItem randomItem = new RandomItem(itemStack, Double.parseDouble(params[2]));
                itemStacks.add(randomItem);
            }
        }
    }

    public void openChest(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 27, plugin.getMessagesManager().getMessages().get("gamechest-inv-title"));
        int counter = 0;
        for(RandomItem randomItem : getItems()) {
            if(counter == plugin.getConfigManager().getConfig("config.yml").getInt("max-items")) continue;
            if(Util.chance(randomItem.getChance())) {
                inventory.setItem(Util.randomSlot(inventory.getSize()), randomItem.getItemStack());
                counter++;
            }
        }
        player.openInventory(inventory);
        //player.playSound(player.getLocation(), Sound.CHEST_OPEN, 2f, 2f);
    }

    public List<RandomItem> getItems() {
        return itemStacks;
    }
}