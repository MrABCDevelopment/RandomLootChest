package me.mrsandking.github.randomlootchest.manager;

import lombok.Getter;
import me.mrsandking.github.randomlootchest.objects.RandomItem;
import me.mrsandking.github.randomlootchest.RandomLootChestMain;
import me.mrsandking.github.randomlootchest.objects.ChestGame;
import me.mrsandking.github.randomlootchest.objects.RandomMoney;
import me.mrsandking.github.randomlootchest.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Getter
public class ChestsManager {

    private HashMap<String, ChestGame> chests;

    public ChestsManager(RandomLootChestMain plugin) {
        chests = new HashMap<>();
        load(plugin);
    }

    public void load(RandomLootChestMain plugin) {
        chests.clear();
        FileConfiguration config = plugin.getConfigManager().getConfig("chests.yml");
        for(String id : config.getConfigurationSection("chests").getKeys(false)) {
            if(chests.size() >= 9) continue;
            ChestGame chestGame = new ChestGame(id);
            chestGame.setTitle(ChatColor.translateAlternateColorCodes('&', config.getString("chests."+id+".Title")));
            chestGame.setTime(config.getInt("chests."+id+".Cooldown"));
            chestGame.setMaxItems(config.getInt("chests."+id+".MaxItems"));
            chestGame.setMaxItemsInTheSameType(config.getInt("chests."+id+".MaxItemsInTheSameType"));
            if(config.get("chests."+id+".Money") != null) {
                String moneyString = config.getString("chests." + id + ".Money");
                String[] strings = moneyString.split(";");
                chestGame.setMoney(new RandomMoney(Integer.parseInt(strings[0]), Integer.parseInt(strings[1])));
            }
            for(String content : config.getConfigurationSection("chests."+id+".Contents").getKeys(false)) {
                try {
                    ItemStack itemStack = null;
                    String material = config.getString("chests."+id+".Contents."+content+".Material");
                    if(material.equalsIgnoreCase("enchanted_golden_apple")) {
                        try {
                            itemStack = new ItemStack(Material.getMaterial(material.toUpperCase()));
                        } catch (NullPointerException exception) {
                            itemStack = new ItemStack(Material.GOLDEN_APPLE, 1, (byte)1);
                        }
                    } else if(material.equalsIgnoreCase("potion")) {
                        String type = config.getString("chests."+id+".Contents."+content+".PotionEffect");
                        itemStack = Util.getPotion(type.toUpperCase(), config.getInt("chests."+id+".Contents."+content+".Tier"), false, 1);
                    } else if(material.equalsIgnoreCase("splash_potion")) {
                        String type = config.getString("chests."+id+".Contents."+content+".PotionEffect");
                        itemStack = Util.getPotion(type.toUpperCase(), config.getInt("chests."+id+".Contents."+content+".Tier"), true, 1);
                    } else {
                        itemStack = new ItemStack(Material.getMaterial(material.toUpperCase()));
                    }
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    if(config.getString("chests."+id+".Contents."+content+".DisplayName") != null)
                        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getString("chests."+id+".Contents."+content+".DisplayName")));
                    if(config.getStringList("chests."+id+".Contents."+content+".DisplayLore") != null)
                        itemMeta.setLore(Util.colouredLore(config.getStringList("chests."+id+".Contents."+content+".DisplayLore")));
                    if(config.get("chests."+id+".Contents."+content+".Glowing") != null && config.getBoolean("chests."+id+".Contents."+content+".Glowing")) {
                        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
                    }
                    itemStack.setItemMeta(itemMeta);
                    if(config.getStringList("chests."+id+".Contents."+content+".Enchantments") != null && !config.getStringList("chests."+id+".Contents."+content+".Enchantments").isEmpty()) {
                        for(String enchantment : config.getStringList("chests."+id+".Contents."+content+".Enchantments")) {
                            String[] splits = enchantment.split(":");
                            itemStack.addUnsafeEnchantment(Enchantment.getByName(splits[0].toUpperCase()), Integer.parseInt(splits[1]));
                        }
                    }
                    if(config.get("chests."+id+".Contents."+content+".Amount") != null)
                        itemStack.setAmount(config.getInt("chests."+id+".Contents."+content+".Amount"));
                    RandomItem randomItem = new RandomItem(itemStack, config.getDouble("chests."+id+".Contents."+content+".Chance"));
                    chestGame.getItems().add(randomItem);
                } catch (NullPointerException e) {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"There is an error with '"+content+"' in config.yml");
                    continue;
                }
            }
            chests.put(id, chestGame);
        }
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN.toString()+chests.size()+" chests loaded.");
    }

    public ChestGame getChestGameByRarity(String id) {
        for(String string : chests.keySet()) {
            if(string.equals(id)) {
                return chests.get(id);
            }
        }
        return null;
    }
    public ChestGame getRandomChest() {
        ArrayList<ChestGame> chestGames = new ArrayList<ChestGame>(chests.values());
        int rN = Util.getRandom().nextInt(chests.size());
        return chestGames.get(rN);
    }

    public ChestGame getChestByLocation(Location location) {
        for(Map.Entry<String, String> map : RandomLootChestMain.getInstance().getLocationManager().getLocations().entrySet()) {
            if(Util.getStringLocation(map.getKey()).equals(location)) {
                return chests.get(map.getValue());
            }
        }
        return null;
    }

}