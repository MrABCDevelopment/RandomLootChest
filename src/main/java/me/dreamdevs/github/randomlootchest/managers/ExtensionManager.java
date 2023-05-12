package me.dreamdevs.github.randomlootchest.managers;

import lombok.NonNull;
import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.api.events.ExtensionDisableEvent;
import me.dreamdevs.github.randomlootchest.api.extensions.Extension;
import me.dreamdevs.github.randomlootchest.api.extensions.ExtensionClassLoader;
import me.dreamdevs.github.randomlootchest.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;

import java.io.*;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class ExtensionManager {

    private File directory;
    private List<Extension> extensions;
    private final Map<String, Class<?>> classes;
    private final Map<@NonNull Extension, ExtensionClassLoader> loaders;
    private RandomLootChestMain plugin;

    public ExtensionManager(RandomLootChestMain plugin) {
        this.plugin = plugin;
        this.extensions = new ArrayList<>();
        this.loaders = new HashMap<>();
        this.classes = new HashMap<>();
        this.directory = new File(plugin.getDataFolder(), "extensions");
        if(!directory.exists() || !directory.isDirectory()) directory.mkdirs();
    }

    public void loadExtensions() {

        Optional.ofNullable(directory.listFiles(((dir, name) -> name.endsWith(".jar")))).ifPresent(files -> {
            for(File file : files)
                loadExtension(file);
        });
    }

    public void enableExtensions() {
        getLoadedExtensions().forEach(this::enableExtension);

        Util.sendPluginMessage("&aLoaded "+getEnabledExtensions().size()+" extensions!");
    }

    private void setDataFolder(Extension extension) {
        File dataFolder = new File(RandomLootChestMain.getInstance().getDataFolder(), extension.getDescription().getExtensionName());
        if(!dataFolder.exists() || !dataFolder.isDirectory()) dataFolder.mkdirs();
        extension.setDataFolder(dataFolder);
    }

    public void loadExtension(@NonNull File file) {
        Extension extension = null;
        ExtensionClassLoader extensionClassLoader = null;
        try(JarFile jarFile = new JarFile(file)) {
            YamlConfiguration extensionFile = configuration(jarFile);
            extensionClassLoader = new ExtensionClassLoader(this, extensionFile, file, this.getClass().getClassLoader());

            extension = extensionClassLoader.getExtension();

        } catch (Exception e) {
            Util.sendPluginMessage("&cProblem tkwi tu");
            return;
        }

        setDataFolder(extension);
        extension.setFile(file);

        extensions.add(extension);

        loaders.put(extension, extensionClassLoader);

        try {
            extension.onExtensionLoad();
            extension.setState(Extension.State.LOADED);
        } catch (NoClassDefFoundError | NoSuchMethodError | NoSuchFieldError e) {
            Util.sendPluginMessage("&cBrak klasy...");
        } catch (Exception e) {
            Util.sendPluginMessage("&cBrak idk");
            e.printStackTrace();
        }
    }

    public void enableExtension(Extension extension) {
        extension.onExtensionEnable();
    }

    public void disableExtensions() {
        extensions.forEach(extension -> {
            extension.onExtensionDisable();
            ExtensionDisableEvent event = new ExtensionDisableEvent(extension);
            Bukkit.getPluginManager().callEvent(event);
        });
    }

    public List<Extension> getEnabledExtensions() {
        return extensions.stream().filter(Extension::isEnabled).collect(Collectors.toList());
    }

    public List<Extension> getLoadedExtensions() {
        return extensions.stream().filter(Extension::isLoaded).collect(Collectors.toList());
    }

    public void registerListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, plugin);
    }

    @NonNull
    private YamlConfiguration configuration(@NonNull JarFile jar) throws IOException, InvalidConfigurationException {
        JarEntry entry = jar.getJarEntry("extension.yml");
        if (entry == null) {
            Util.sendPluginMessage("&cExtension does not contain 'extension.yml'");
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(jar.getInputStream(entry)));
        YamlConfiguration data = new YamlConfiguration();
        data.load(reader);
        reader.close();
        return data;
    }

    /**
     * Finds a class by name that has been loaded by this loader
     * @param name name of the class, not null
     * @return Class the class or null if not found
     */

    public Class<?> getClassByName(@NonNull final String name) {
        try {
            return classes.getOrDefault(name, loaders.values().stream().filter(Objects::nonNull).map(l -> l.findClass(name, false)).filter(Objects::nonNull).findFirst().orElse(null));
        } catch (Exception ignored) {
            // Ignored.
        }
        return null;
    }

    /**
     * Sets a class that this loader should know about
     *
     * @param name name of the class, not null
     * @param clazz the class, not null
     */
    public void setClass(@NonNull final String name, @NonNull final Class<?> clazz) {
        classes.putIfAbsent(name, clazz);
    }


}