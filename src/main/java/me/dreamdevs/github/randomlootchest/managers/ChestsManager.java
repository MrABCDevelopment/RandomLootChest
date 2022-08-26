package me.dreamdevs.github.randomlootchest.managers;

import lombok.Getter;
import me.dreamdevs.github.randomlootchest.objects.RandomItem;
import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.objects.ChestGame;
import me.dreamdevs.github.randomlootchest.objects.RandomMoney;
import me.dreamdevs.github.randomlootchest.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class ChestsManager {

    private File chestsDirectory;
    private HashMap<String, ChestGame> chests;

    public ChestsManager(RandomLootChestMain plugin) {
        chests = new HashMap<>();
        load(plugin);
    }

    /**
     * Since update v1.5.0, there is new chest system.
     * This has recoded due to easier way to apply more items to chests,
     * more options to chests.
     */

    public void load(RandomLootChestMain plugin) {
        chests.clear();
        chestsDirectory = new File(plugin.getDataFolder(), "chests");
        if(!chestsDirectory.exists() || !chestsDirectory.isDirectory()) {
            chestsDirectory.mkdirs();
            plugin.saveResource("chests/default.yml", false);
        }
        if(chestsDirectory.listFiles().length == 0) return;

        for(File chestFile : chestsDirectory.listFiles()) {
            if(chestFile.getName().endsWith(".yml")) {
                FileConfiguration config = YamlConfiguration.loadConfiguration(chestFile);
                ChestGame chestGame = new ChestGame(chestFile.getName().substring(0, chestFile.getName().length()-4));
                chestGame.setTitle(ColourUtil.hexText(config.getString("Title")));
                chestGame.setTime(config.getInt("Cooldown"));
                chestGame.setMaxItems(config.getInt("MaxItems"));
                chestGame.setMaxItemsInTheSameType(config.getInt("MaxItemsInTheSameType"));
                if(config.get("Money") != null) {
                    String moneyString = config.getString("Money");
                    String[] strings = moneyString.split(";");
                    chestGame.setMoney(new RandomMoney(Integer.parseInt(strings[0]), Integer.parseInt(strings[1])));
                }
                for(String content : config.getConfigurationSection("Contents").getKeys(false)) {
                    try {
                        ItemStack itemStack = null;
                        String material = config.getString("Contents."+content+".Material");
                        String displayName =  config.getString("Contents."+content+".DisplayName", null);
                        int amount = config.getInt("Contents."+content+".Amount", 1);
                        List<String> lore = new ArrayList<>();
                        if(config.get("Contents."+content+".DisplayLore") != null)
                            lore = config.getStringList("Contents."+content+".DisplayLore");
                        List<String> enchantments = new ArrayList<>();
                        if(config.get("Contents."+content+".Enchantments") != null)
                            enchantments = config.getStringList("Contents."+content+".Enchantments");
                        boolean unbreakable = config.getBoolean("Contents."+content+".Unbreakable", false);
                        boolean glowing = config.getBoolean("Contents."+content+".Glowing", false);

                        if(material.contains("potion")) {
                            if(VersionUtil.isLegacy())
                                itemStack = ItemUtil.getPotion(material, amount, displayName, lore, enchantments, unbreakable, glowing,
                                        config.getString("Contents."+content+".PotionEffect"), config.getInt("Contents."+content+".Tier"));
                            else
                                itemStack = ItemUtil.getPotion(material, amount, displayName, lore, enchantments, unbreakable, glowing,
                                        config.getString("Contents."+content+".PotionEffect"),
                                        config.getBoolean("Contents."+content+".Extended"), config.getBoolean("Contents."+content+".Upgraded"));
                        }

                        if(itemStack == null)
                            itemStack = ItemUtil.parsedItem(material, amount, displayName, lore, enchantments, unbreakable, glowing);

                        RandomItem randomItem = new RandomItem(itemStack, config.getDouble("Contents."+content+".Chance"));
                        chestGame.getItems().add(randomItem);
                    } catch (NullPointerException e) {
                        Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"There is an error with '"+content+"' in config.yml");
                        continue;
                    }
                }
                chests.put(chestGame.getId(), chestGame);
            }
        }
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN.toString()+chests.size()+" chests loaded.");
    }

    public void addChest(String id) {
        ChestGame chestGame = new ChestGame(id);
        chestGame.setTime(30);
        chestGame.setMaxItems(4);
        chestGame.setMaxItemsInTheSameType(3);
        chestGame.setParticleAmount(1);
        chestGame.setParticleUse(false);
        chestGame.setParticleType("HEART");
        chestGame.setItems(new ArrayList<>());
        chests.put(chestGame.getId(), chestGame);
    }


    public void removeChest(String id) {
        if(chests.get(id) == null)
            return;
        chests.get(id).delete();
    }

    public ChestGame getChestGameByRarity(String id) {
        for(String string : chests.keySet()) {
            if(string.equals(id)) {
                return chests.get(id);
            }
        }
        return null;
    }

    public ChestGame getChestGameByLocation(Location location) {
        for(Map.Entry<String, String> s : RandomLootChestMain.getInstance().getLocationManager().getLocations().entrySet()) {
            if(location == Util.getStringLocation(s.getKey())) {
                return getChestGameByRarity(s.getValue());
            }
        }
        return null;
    }

    public ChestGame getRandomChest() {
        ArrayList<ChestGame> chestGames = new ArrayList<ChestGame>(chests.values());
        int rN = Util.getRandom().nextInt(chests.size());
        return chestGames.get(rN);
    }

}