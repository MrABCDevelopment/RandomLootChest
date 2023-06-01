package me.dreamdevs.github.randomlootchest.managers;

import lombok.Getter;
import me.dreamdevs.github.randomlootchest.api.IChestGame;
import me.dreamdevs.github.randomlootchest.api.objects.RandomItem;
import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.api.objects.ChestGame;
import me.dreamdevs.github.randomlootchest.utils.*;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.Type;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.*;

@Getter
public class ChestsManager {

    private File chestsDirectory;
    private final Map<String, ChestGame> chests;
    private static final String CONTENTS = "Contents";

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

        Optional.ofNullable(chestsDirectory.listFiles(((dir, name) -> name.endsWith(".yml")))).ifPresent(files -> {
            Arrays.stream(files).forEach(chestFile -> {
                FileConfiguration config = YamlConfiguration.loadConfiguration(chestFile);
                ChestGame chestGame = new ChestGame(chestFile.getName().substring(0, chestFile.getName().length()-4), true);
                chestGame.setTitle(ColourUtil.colorize(Objects.requireNonNull(config.getString("Title"))));
                chestGame.setTime(TimeUtil.convertStringToCooldown(Objects.requireNonNull(config.getString("Cooldown"))));
                chestGame.setMaxItems(config.getInt("MaxItems"));
                chestGame.setMaxItemsInTheSameType(config.getInt("MaxItemsInTheSameType"));

                if(config.getStringList(CONTENTS+"-Items") != null) config.getStringList(CONTENTS+"-Items").forEach(s -> {
                    RandomItem randomItem = RandomLootChestMain.getInstance().getItemsManager().getItems().get(s);
                    chestGame.getItems().add(randomItem);
                });

                if(config.getStringList(CONTENTS+"-MMOItems") != null) config.getStringList(CONTENTS+"-MMOItems").forEach(s -> {
                    String[] strings = s.split(":");
                    MMOItem mmoItem = MMOItems.plugin.getMMOItem(Type.get(strings[0]), strings[1]);

                    RandomItem randomItem = new RandomItem(mmoItem.newBuilder().build(), Double.parseDouble(strings[2]));
                    chestGame.getItems().add(randomItem);
                });

                for(String content : config.getConfigurationSection(CONTENTS).getKeys(false)) {
                    try {
                        ItemStack itemStack = null;
                        String material = Objects.requireNonNull(config.getString(CONTENTS + "." + content + ".Material")).toUpperCase();
                        String displayName =  config.getString(CONTENTS+"."+content+".DisplayName", null);
                        int amount = config.getInt(CONTENTS+"."+content+".Amount", 1);
                        List<String> lore = new ArrayList<>();
                        if(config.get(CONTENTS+"."+content+".DisplayLore") != null)
                            lore = config.getStringList(CONTENTS+"."+content+".DisplayLore");

                        Map<String, Integer> enchantments = new HashMap<>();
                        if(config.get(CONTENTS+"."+content+".Enchantments") != null) {
                            ConfigurationSection enchantmentSection = config.getConfigurationSection(CONTENTS+"."+content+".Enchantments");
                            for(String key : enchantmentSection.getKeys(false))
                                enchantments.put(key.toUpperCase(), enchantmentSection.getInt(key));
                        }

                        boolean unbreakable = config.getBoolean(CONTENTS+"."+content+".Unbreakable", false);
                        boolean glowing = config.getBoolean(CONTENTS+"."+content+".Glowing", false);
                        itemStack = ItemUtil.parsedItem(material, amount, displayName, lore, enchantments, unbreakable, glowing);

                        if(material.contains("potion".toUpperCase())) {
                            itemStack = ItemUtil.getPotion(material, amount, displayName, lore, enchantments, unbreakable, glowing,
                                        config.getString(CONTENTS+"."+content+".PotionEffect"),
                                        config.getBoolean(CONTENTS+"."+content+".Extended"), config.getBoolean(CONTENTS+"."+content+".Upgraded"));

                        }
                        RandomItem randomItem = new RandomItem(itemStack, config.getDouble(CONTENTS+"."+content+".Chance"));
                        chestGame.getItems().add(randomItem);
                    } catch (NullPointerException e) {
                        Util.sendPluginMessage("&cThere is an error with '"+content+"' in config.yml");
                        continue;
                    }
                }
                chests.put(chestGame.getId(), chestGame);
            });
        });

        Util.sendPluginMessage("&a"+chests.size()+" chests loaded!");
    }

    public ChestGame getChestGameByRarity(String id) {
        return chests.keySet().stream().filter(s -> s.equals(id)).map(chests::get).findFirst().orElse(null);
    }

    public ChestGame getChestGameByLocation(Location location) {
        return RandomLootChestMain.getInstance().getLocationManager().getLocations().keySet().stream().filter(s -> location == Util.getStringLocation(s)).map(this::getChestGameByRarity).findFirst().orElse(null);
    }

    public void registerChest(Class<? extends IChestGame> chestGameClass, boolean createFile) {
        try {
            IChestGame iChestGame = chestGameClass.newInstance();
            ChestGame chestGame = new ChestGame(iChestGame.getId(), createFile);
            chestGame.setTitle(ColourUtil.colorize(iChestGame.getTitle()));
            chestGame.setTime(iChestGame.getTime());
            chestGame.setMaxItems(iChestGame.getMaxItems());
            chestGame.setParticleAmount(iChestGame.getParticleAmount());
            chestGame.setChestFile(iChestGame.getChestFile());
            chestGame.setParticleType(iChestGame.getParticleType());
            chestGame.setParticleUse(iChestGame.isParticleUse());
            chestGame.setItems(iChestGame.getItems());
            chestGame.setMaxItemsInTheSameType(iChestGame.getMaxItemsInTheSameType());
            chests.put(chestGame.getId(), chestGame);
            Util.sendPluginMessage("&aRegistered "+iChestGame.getId()+" chest!");
        } catch (InstantiationException | IllegalAccessException e) {}
    }

    public ChestGame getRandomChest() {
        ArrayList<ChestGame> chestGames = new ArrayList<>(chests.values());
        int rN = Util.getRandom().nextInt(chests.size());
        return chestGames.get(rN);
    }

}