package me.dreamdevs.randomlootchest.managers;

import lombok.Getter;
import me.dreamdevs.randomlootchest.RandomLootChestMain;
import me.dreamdevs.randomlootchest.api.events.ExtensionDisableEvent;
import me.dreamdevs.randomlootchest.api.events.ExtensionEnableEvent;
import me.dreamdevs.randomlootchest.api.extensions.Extension;
import me.dreamdevs.randomlootchest.api.extensions.ExtensionDescription;
import me.dreamdevs.randomlootchest.api.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipFile;

public class ExtensionManager {

    private final File directory;
    private @Getter final List<Extension> extensions;
    private final Map<Class<?>, ExtensionDescription> classes = new HashMap<>();

    public ExtensionManager(RandomLootChestMain plugin) {
        this.extensions = new ArrayList<>();
        this.directory = new File(plugin.getDataFolder(), "extensions");
        if(!directory.exists() || !directory.isDirectory()) {
            directory.mkdirs();
        }
    }

    public void loadExtensions() {
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".jar"));
        if(Objects.requireNonNull(files).length == 0)
            return;
        for(File file : files) {
            try(ZipFile zipFile = new ZipFile(file)) {
                String mainClass = null;
                InputStream is = zipFile.getInputStream(zipFile.getEntry("extension.yml"));

                YamlConfiguration config = YamlConfiguration.loadConfiguration(new InputStreamReader(is));
                mainClass = config.getString("main");
                ExtensionDescription extensionDescription = new ExtensionDescription();
                try {
                    extensionDescription.setExtensionMain(mainClass);
                    extensionDescription.setExtensionName(config.getString("name"));
                    extensionDescription.setExtensionVersion(config.getString("version"));
                } catch (Exception e) {
                    Util.sendPluginMessage("&cThe "+file.getName()+" extension is missing 'extension.yml' content!");
                    continue;
                }

                if(config.getString("author") != null)
                    extensionDescription.setExtensionAuthor(config.getString("author"));
                if(config.getString("icon") != null)
                    extensionDescription.setExtensionMaterial(Material.getMaterial(Objects.requireNonNull(config.getString("icon"))));
                else
                    extensionDescription.setExtensionMaterial(Material.PAPER);

                ClassLoader l = URLClassLoader.newInstance(new URL[]{file.toURI().toURL()}, getClass().getClassLoader());

                Class<?> clazz = l.loadClass(mainClass);
                classes.put(clazz, extensionDescription);

                Extension extension = (Extension) clazz.getConstructor().newInstance();
                extension.setState(Extension.State.LOADED);
                extension.setDescription(extensionDescription);

                setDataFolder(extension);

                extension.setFile(file);

                try {
                    extension.onExtensionEnable();
                    extensions.add(extension);
                    ExtensionEnableEvent event = new ExtensionEnableEvent(extension);
                    Bukkit.getPluginManager().callEvent(event);
                } catch (Exception e) {
                    extension.setState(Extension.State.DISABLED);
                    Util.sendPluginMessage("&cSomething strange has happened. Couldn't load the extension.");
                    Util.sendPluginMessage("&cError: "+ e.getMessage());
                    e.printStackTrace();
                }

                if(extension.isLoaded()) {
                    extension.setState(Extension.State.ENABLED);
                    Util.sendPluginMessage("&aExtension "+extension.getDescription().getExtensionName()+" v"+extension.getDescription().getExtensionVersion()+" enabled!");
                }
            } catch (Exception e) {
                Util.sendPluginMessage("&cSomething strange has happened. Couldn't load the extension.");
                Util.sendPluginMessage("&cError: "+ e.getMessage());
            }
        }
        Util.sendPluginMessage("&aLoaded "+getEnabledExtensions().size()+" extensions!");
    }

    private void setDataFolder(Extension extension) {
        File dataFolder = new File(RandomLootChestMain.getInstance().getDataFolder(), extension.getDescription().getExtensionName());
        if(!dataFolder.exists() || !dataFolder.isDirectory()) dataFolder.mkdirs();
        extension.setDataFolder(dataFolder);
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

    public Extension getExtensionByName(String extensionName) {
        return extensions.stream().filter(extension -> extension.getDescription().getExtensionName().equalsIgnoreCase(extensionName)).findFirst().orElse(null);
    }

    public void registerListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, RandomLootChestMain.getInstance());
    }

}