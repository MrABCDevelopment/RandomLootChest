package me.dreamdevs.github.randomlootchest.database;

import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.api.database.DatabaseConnector;
import me.dreamdevs.github.randomlootchest.utils.Util;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class DatabaseYAML extends DatabaseConnector {

    private File data;

    @Override
    public void connect() {
        data = new File(RandomLootChestMain.getInstance().getDataFolder(), "users");
        if (!data.exists() || !data.isDirectory()) data.mkdirs();
    }

    @Override
    public void disconnect() {}

    @Override
    public void saveData() {
        Map<HashMap<UUID, Location>, AtomicInteger> mainMap = RandomLootChestMain.getInstance().getCooldownManager().getChestCooldowns();
        for(Map<UUID, Location> map : mainMap.keySet()) {
            for(UUID uuid : map.keySet()) {
                File playerFile = new File(data, uuid + ".yml");
                if (!playerFile.exists()) {
                    try {
                        playerFile.createNewFile();
                    } catch (Exception e) {
                    }
                }
                HashMap<HashMap<UUID, Location>, AtomicInteger> playerMap = RandomLootChestMain.getInstance().getCooldownManager().getPlayerCooldowns(uuid);
                ArrayList<String> strings = new ArrayList<>();
                for(HashMap<UUID, Location> map1 : playerMap.keySet()) {
                    String line = Util.getLocationString(map1.get(uuid))+";"+playerMap.get(map1).get();
                    strings.add(line);
                }
                YamlConfiguration configuration = YamlConfiguration.loadConfiguration(playerFile);
                configuration.set("playerUUID", uuid.toString());
                configuration.set("cooldowns", strings);
                try {
                    configuration.save(playerFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void loadData() {
        if(data.listFiles().length == 0) return;
        for(File file : data.listFiles()) {
            if(file.getName().endsWith(".yml")) {
                YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
                UUID uuid = UUID.fromString(config.getString("playerUUID"));
                for(String line : config.getStringList("cooldowns")) {
                    String[] splits = line.split(";");
                    RandomLootChestMain.getInstance().getCooldownManager().setCooldown(uuid, Util.getStringLocation(splits[0]), Integer.parseInt(splits[1]), false);
                }
            }
        }
    }

    @Override
    public boolean isAccount(UUID account) {
        return false;
    }

    @Override
    public void insertData(UUID account) {}
}
