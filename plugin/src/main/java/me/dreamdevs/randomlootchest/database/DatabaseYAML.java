package me.dreamdevs.randomlootchest.database;

import me.dreamdevs.randomlootchest.api.database.IDatabase;
import me.dreamdevs.randomlootchest.RandomLootChestMain;
import me.dreamdevs.randomlootchest.database.data.PlayerData;
import me.dreamdevs.randomlootchest.api.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class DatabaseYAML implements IDatabase {

    private File directory;

    @Override
    public void connect() {
        directory = new File(RandomLootChestMain.getInstance().getDataFolder(), "players");
        if(!directory.exists() && !directory.isDirectory()) {
            directory.mkdirs();
        }
    }

    @Override
    public void disconnect() {
        // To do nothing, useless in this type of database
    }

    @Override
    public void loadData() {
        // Get all filtered files that are yml files.
        File[] files = directory.listFiles(((dir, name) -> name.endsWith(".yml")));

        if(Objects.requireNonNull(files).length == 0) {
            return;
        }

        Stream.of(files).map(YamlConfiguration::loadConfiguration).forEach(yamlConfiguration -> {
            UUID uuid = UUID.fromString(Objects.requireNonNull(yamlConfiguration.getString("UUID")));
            PlayerData playerData = new PlayerData(Bukkit.getOfflinePlayer(uuid));
            for (String s : yamlConfiguration.getStringList("ActiveCooldown")) {
                String[] strings = s.split(";");
                playerData.applyCooldown(Util.getStringLocation(strings[0]), Integer.parseInt(strings[1]));
            }
            RandomLootChestMain.getInstance().getCooldownManager().getPlayers().add(playerData);
        });
    }

    @Override
    public void saveData() {
        RandomLootChestMain.getInstance().getCooldownManager().getPlayers().forEach(playerData -> {
            File dataFile = new File(directory,playerData.getPlayer().getUniqueId() + ".yml");
            Util.tryCreateFile(dataFile);
            Map<Location, AtomicInteger> map = playerData.getCooldown();
            List<String> list = new ArrayList<>();
            map.forEach((key, value) -> list.add(Util.getLocationString(key)+";"+value.get()));
            YamlConfiguration config = YamlConfiguration.loadConfiguration(dataFile);
            config.set("UUID", playerData.getPlayer().getUniqueId().toString());
            config.set("ActiveCooldown", list);
            try {
                config.save(dataFile);
            } catch (IOException e) {
                // To do nothing, only message about error will display
                Util.sendPluginMessage("&cSomething went wrong while saving PlayerData: " +
                        playerData.getPlayer().getUniqueId() + " - UUID");
            }
        });
    }
}