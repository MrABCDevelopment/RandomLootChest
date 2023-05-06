package me.dreamdevs.github.randomlootchest.managers;

import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.api.events.ExtensionDisableEvent;
import me.dreamdevs.github.randomlootchest.api.events.ExtensionEnableEvent;
import me.dreamdevs.github.randomlootchest.api.extensions.Extension;
import me.dreamdevs.github.randomlootchest.api.extensions.ExtensionDescription;
import me.dreamdevs.github.randomlootchest.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipFile;

public class ExtensionManager {

    private File directory;
    private List<Extension> extensions;
    private Map<Class<?>, ExtensionDescription> classes = new HashMap<>();

    public ExtensionManager(RandomLootChestMain plugin) {
        this.extensions = new ArrayList<>();
        this.directory = new File(plugin.getDataFolder(), "extensions");
        if(!directory.exists() || !directory.isDirectory()) directory.mkdirs();
        loadExtensions();
    }

    public void loadExtensions() {
        if(directory.listFiles().length == 0) return;
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".jar"));
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

                ClassLoader l = URLClassLoader.newInstance(new URL[]{file.toURI().toURL()}, getClass().getClassLoader());

                Class<?> clazz = l.loadClass(mainClass);
                classes.put(clazz, extensionDescription);

                Extension extension = (Extension) clazz.newInstance();
                extension.setState(Extension.State.LOADED);
                extension.setDescription(extensionDescription);

                setDataFolder(extension);

                extension.setFile(file);

                try {
                    extension.onExtensionEnable();
                    extension.setState(Extension.State.ENABLED);
                    extensions.add(extension);
                    ExtensionEnableEvent event = new ExtensionEnableEvent(extension);
                    Bukkit.getPluginManager().callEvent(event);
                } catch (Exception e) {
                    extension.setState(Extension.State.DISABLED);
                    Util.sendPluginMessage("&cSomething strange has happened. Couldn't load the extension.");
                    Util.sendPluginMessage("&cError: "+ e.getMessage());
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

}