package me.dreamdevs.github.randomlootchest.api.extensions;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import lombok.Getter;
import me.dreamdevs.github.randomlootchest.managers.ExtensionManager;
import me.dreamdevs.github.randomlootchest.utils.Util;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.InvalidDescriptionException;

@Getter
public class ExtensionClassLoader extends URLClassLoader {

    private final Map<String, Class<?>> classes = new HashMap<>();
    private final Extension extension;
    private final ExtensionManager loader;

    /**
     * For testing only
     * @param loader Addons Manager
     * @param jarFile Jar File
     * @throws MalformedURLException exception
     */
    protected ExtensionClassLoader(Extension extension, ExtensionManager loader, File jarFile) throws MalformedURLException {
        super(new URL[]{jarFile.toURI().toURL()});
        this.extension = extension;
        this.loader = loader;
    }

    public ExtensionClassLoader(ExtensionManager extensionManager, YamlConfiguration data, File jarFile, ClassLoader parent)
            throws MalformedURLException,
            InvalidDescriptionException,
            InstantiationException,
            IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        super(new URL[]{jarFile.toURI().toURL()}, parent);

        loader = extensionManager;

        Class<?> javaClass = null;
        try {
            String mainClass = data.getString("main");
            if (mainClass == null) {
                Util.sendPluginMessage("&cThere's no main class!");
            }
            javaClass = Class.forName(mainClass, true, this);
        } catch (Exception e) {
            throw new InvalidDescriptionException("Could not load '" + jarFile.getName() + "' in folder '" + jarFile.getParent() + "' - " + e.getMessage());
        }

        Class<? extends Extension> extensionClass = null;
        try {
            extensionClass = javaClass.asSubclass(Extension.class);
        } catch (ClassCastException e) {
            Util.sendPluginMessage("&cMain class does not extend 'Extension'");
        }

        extension = extensionClass.getDeclaredConstructor().newInstance();
        extension.setDescription(asDescription(data));
    }

    public static ExtensionDescription asDescription(YamlConfiguration data) {
        if (!data.contains("main")) {
            Util.sendPluginMessage("&cThis is fatal error...");
        }
        if (!data.contains("name")) {
            Util.sendPluginMessage("&cThis is fatal error...");
        }
        if (!data.contains("version")) {
            Util.sendPluginMessage("&cThis is fatal error...");
        }

        ExtensionDescription extensionDescription = new ExtensionDescription();
        extensionDescription.setExtensionMain(data.getString("main"));
        extensionDescription.setExtensionName(data.getString("name"));
        extensionDescription.setExtensionVersion(data.getString("version"));

        String author = data.getString("author");
        if (author != null) {
            extensionDescription.setExtensionAuthor(author);
        }

        return extensionDescription;
    }

    /* (non-Javadoc)
     * @see java.net.URLClassLoader#findClass(java.lang.String)
     */
    @Override
    protected Class<?> findClass(String name) {
        return findClass(name, true);
    }

    /**
     * This is a custom findClass that enables classes in other addons to be found
     * @param name - class name
     * @param checkGlobal - check globally or not when searching
     * @return Class - class if found
     */
    public Class<?> findClass(String name, boolean checkGlobal) {
        Class<?> result = classes.get(name);
        if (result == null) {
            if (checkGlobal) {
                result = loader.getClassByName(name);
            }

            if (result == null) {
                try {
                    result = super.findClass(name);
                } catch (ClassNotFoundException | NoClassDefFoundError e) {
                    // Do nothing.
                }
                if (result != null) {
                    loader.setClass(name, result);

                }
            }
            classes.put(name, result);
        }
        return result;
    }

    /**
     * @return class list
     */
    public Set<String> getClasses() {
        return classes.keySet();
    }
}