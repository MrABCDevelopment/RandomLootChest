package me.dreamdevs.github.randomlootchest.api.extensions;

import lombok.Getter;
import lombok.Setter;
import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.jar.JarFile;

@Getter @Setter
public abstract class Extension {

    private ExtensionDescription description;
    private FileConfiguration config;
    private File dataFolder;
    private File file;
    private Extension.State state;
    private final String configFileName = "config.yml";

    public abstract void onExtensionEnable();

    public abstract void onExtensionDisable();

    private FileConfiguration loadConfigFile() {
        File yamlFile = new File(dataFolder, configFileName);
        return Optional.of(yamlFile).map(YamlConfiguration::loadConfiguration).orElse(null);
    }

    public void saveConfig() {
        try {
            getConfig().save(new File(dataFolder, configFileName));
        } catch (IOException e) {
            Bukkit.getLogger().severe("Could not save config! " + this.getDescription().getExtensionName() + " " + e.getMessage());
        }
    }

    public void reloadConfig() {
        config = loadConfigFile();
    }

    public void saveDefaultConfig() {
        saveResource(configFileName, false);
        config = loadConfigFile();
        if(config == null)
            Util.sendPluginMessage("&cCouldn't load config.yml");
    }

    public void saveResource(String resourcePath, boolean replace) {
        saveResource(resourcePath, dataFolder, replace, false);
    }

    public File saveResource(String jarResource, File destinationFolder, boolean replace, boolean noPath) {
        return Optional.ofNullable(jarResource).map(s -> s.replace('\\', '/'))
                .map(s -> {
                    try {
                        return new JarFile(file);
                    } catch (IOException e) {
                        return null;
                    }
                }).filter(jarFile -> jarFile.getJarEntry(jarResource) != null).filter(jarFile -> {
                    try {
                        return jarFile.getInputStream(jarFile.getJarEntry(jarResource)) != null;
                    } catch (IOException e) {
                        Util.sendPluginMessage("The embedded resource '" + jarResource + "' cannot be found in " + jarFile.getName());
                        return false;
                    }
                }).map(jarFile -> {
                    File outFile = new File(destinationFolder, jarResource);
                    if (noPath) {
                        outFile = new File(destinationFolder, outFile.getName());
                    }
                    outFile.getParentFile().mkdirs();
                    if (!outFile.exists() || replace) {
                        try {
                            java.nio.file.Files.copy(jarFile.getInputStream(jarFile.getJarEntry(jarResource)), outFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        } catch (IOException e) {
                            Util.sendPluginMessage("Could not save from jar file. From " + jarResource + " to " + destinationFolder.getAbsolutePath());
                        }
                    }
                    return outFile;
                }).orElse(null);
    }

    public YamlConfiguration getYamlFromJar(String jarResource) {
        return Optional.of(jarResource).map(s -> s.replace('\\', '/'))
                .map(s -> {
                    try {
                        return new JarFile(file);
                    } catch (IOException e) {
                        return null;
                    }
                }).filter(jarFile -> jarFile.getJarEntry(jarResource) != null)
                .map(jarFile -> {
                    try {
                        return new InputStreamReader(jarFile.getInputStream(jarFile.getJarEntry(jarResource)));
                    } catch (IOException e) {
                        return null;
                    }
                }).map(inputStreamReader -> {
                    YamlConfiguration yamlConfiguration = new YamlConfiguration();
                    try {
                        yamlConfiguration.load(inputStreamReader);
                        return yamlConfiguration;
                    } catch (IOException | InvalidConfigurationException e) {

                    }
                    return null;
                }).orElse(null);
    }

    public InputStream getResource(final String jarResource) {
        return Optional.of(jarResource).map(s -> s.replace('\\', '/'))
                .map(s -> {
                    try {
                        return new JarFile(file);
                   } catch (IOException e) {}
                    return null;
                }).filter(jarFile -> jarFile.getJarEntry(jarResource) != null)
                .map(jarFile -> {
                    try {
                        return jarFile.getInputStream(jarFile.getJarEntry(jarResource));
                    } catch (IOException e) {
                        Util.sendPluginMessage("&cCannot open from jar file.");
                        return null;
                    }
                }).orElse(null);
    }

    public enum State {
        LOADED,
        ENABLED,
        DISABLED;
    }

    public boolean isEnabled() {
        return state == State.ENABLED;
    }

    public RandomLootChestMain getPlugin() {
        return RandomLootChestMain.getInstance();
    }

}