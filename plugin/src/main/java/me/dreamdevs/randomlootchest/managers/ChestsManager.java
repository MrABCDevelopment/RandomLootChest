package me.dreamdevs.randomlootchest.managers;

import lombok.Getter;
import me.dreamdevs.randomlootchest.api.objects.IChestGame;
import me.dreamdevs.randomlootchest.api.objects.IRandomItem;
import me.dreamdevs.randomlootchest.objects.ChestGame;
import me.dreamdevs.randomlootchest.objects.RandomItem;
import me.dreamdevs.randomlootchest.RandomLootChestMain;
import me.dreamdevs.randomlootchest.utils.ColourUtil;
import me.dreamdevs.randomlootchest.utils.ItemUtil;
import me.dreamdevs.randomlootchest.utils.TimeUtil;
import me.dreamdevs.randomlootchest.utils.Util;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.Type;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;

@Getter
public class ChestsManager {

    private File chestsDirectory;
    private final Map<String, IChestGame> chests;
    private static final String CONTENTS = "Contents";
    private final Map<UUID, Location> currentlyOpened = new HashMap<>();

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

        Optional.ofNullable(chestsDirectory.listFiles(((dir, name) -> name.endsWith(".yml")))).ifPresent(files -> Arrays.stream(files).forEach(chestFile -> {
            FileConfiguration config = YamlConfiguration.loadConfiguration(chestFile);
            ChestGame chestGame = new ChestGame(chestFile.getName().substring(0, chestFile.getName().length()-4));
            chestGame.setTitle(ColourUtil.colorize(Objects.requireNonNull(config.getString("Title"))));
            chestGame.setTime(TimeUtil.convertStringToCooldown(Objects.requireNonNull(config.getString("Cooldown"))));
            chestGame.setMaxItems(config.getInt("MaxItems"));
            chestGame.setMaxItemsInTheSameType(config.getInt("MaxItemsInTheSameType", 4));
            chestGame.setParticleUse(config.getBoolean("Particles.Use", false));
            chestGame.setParticleAmount(config.getInt("Particles.Amount", 1));
            chestGame.setParticleType(Particle.valueOf(config.getString("Particles.Type", "HEART")));
            chestGame.setExp(config.getDouble("Exp", 0));

            if(config.getStringList(CONTENTS+"-Items") != null) config.getStringList(CONTENTS+"-Items").forEach(s -> {
                RandomItem randomItem = RandomLootChestMain.getInstance().getItemsManager().getItems().get(s);
                chestGame.getItemStacks().add(randomItem);
            });

            if(config.getStringList(CONTENTS+"-MMOItems") != null) config.getStringList(CONTENTS+"-MMOItems").forEach(s -> {
                String[] strings = s.split(":");
                MMOItem mmoItem = MMOItems.plugin.getMMOItem(Type.get(strings[0]), strings[1]);

                RandomItem randomItem = new RandomItem(mmoItem.newBuilder().build(), Double.parseDouble(strings[2]));
                chestGame.getItemStacks().add(randomItem);
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
                                    config.getString(CONTENTS+"."+content+".PotionEffect", "AWKWARD"),
                                    config.getBoolean(CONTENTS+"."+content+".Extended", false), config.getBoolean(CONTENTS+"."+content+".Upgraded", false));
                    }

                    RandomItem randomItem = new RandomItem(itemStack, config.getDouble(CONTENTS+"."+content+".Chance"));
                    chestGame.getItemStacks().add(randomItem);
                } catch (NullPointerException e) {
                    Util.sendPluginMessage("&cThere is an error with '"+content+"' in config.yml");
                    continue;
                }
            }

            chests.put(chestGame.getId(), chestGame);
            Util.sendPluginMessage("&aRegistered chest with ID: "+chestGame.getId());
            Util.sendPluginMessage("&aTotal Items in this chest: "+chestGame.getItemStacks().size());
        }));

        Util.sendPluginMessage("&a"+chests.size()+" chests loaded!");
    }

    public IChestGame getChestGameByRarity(@NotNull String id) {
        return chests.keySet().stream().filter(s -> s.equals(id)).map(chests::get).findFirst().orElse(null);
    }

    public IChestGame getChestGameByLocation(@NotNull Location location) {
        return RandomLootChestMain.getInstance().getLocationManager().getLocations().entrySet().stream().filter(stringEntry -> stringEntry.getKey().equals(Util.getLocationString(location))).map(stringEntry -> RandomLootChestMain.getInstance().getChestsManager().getChestGameByRarity(stringEntry.getValue())).findAny().orElse(null);
    }

    public IChestGame getChestGameByTitle(@NotNull String title) {
        return chests.values().stream().filter(chestGame -> chestGame.getTitle().equals(title)).findFirst().orElse(null);
    }

    public IChestGame getRandomChest() {
        ArrayList<IChestGame> chestGames = new ArrayList<>(chests.values());
        int rN = Util.getRandom().nextInt(chests.size());
        return chestGames.get(rN);
    }

    /**
     * Some changes since v1.8.0 due to new menu system.
     */

    public void openChest(final @NotNull Player player, @NotNull String type) {
        IChestGame chestGame = getChestGameByRarity(type);

        Inventory inventory = Bukkit.createInventory(null, 27, chestGame.getTitle());

        int counter = 0;

        //for (int x = 0; x<27; x++) {
        for (IRandomItem randomItem : chestGame.getItemStacks()) {
            if (counter == chestGame.getMaxItems())
                continue;
            if (Util.chance(randomItem.getChance())) {
                int max = chestGame.getMaxItemsInTheSameType();
                if (max > 0) {
                    int i = 0;
                    for (int y = 0; y < inventory.getSize(); y++) {
                        if (inventory.getItem(y) != null
                                && inventory.getItem(y).getType()
                                == randomItem.getItemStack().getType() && i < max) {
                            i++;
                        }
                    }
                    if (i < max) {
                        inventory.setItem(Util.randomSlot(27), randomItem.getItemStack());
                        counter++;
                    }
                } else {
                    inventory.setItem(Util.randomSlot(27), randomItem.getItemStack());
                    counter++;
                }
            }
        }

        player.openInventory(inventory);
        player.setExp((float) (player.getExp()+chestGame.getExp()));
    }


}