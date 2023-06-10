package me.dreamdevs.github.randomlootchest.api.menu.extensions;

import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.api.events.ClickInventoryEvent;
import me.dreamdevs.github.randomlootchest.api.extensions.Extension;
import me.dreamdevs.github.randomlootchest.api.menu.MenuItem;
import me.dreamdevs.github.randomlootchest.utils.ColourUtil;

public class ExtensionMenuItem extends MenuItem {

    private final Extension extension;

    public ExtensionMenuItem(Extension extension) {
        super(extension.getDescription().getExtensionMaterial(), ColourUtil.colorize("&b"+extension.getDescription().getExtensionName()), ColourUtil.colouredLore("&7Click to reload its configuration file!"));
        this.extension = extension;
    }

    @Override
    public void performAction(ClickInventoryEvent event) {
        event.getPlayer().closeInventory();
        extension.reloadConfig();
        extension.onExtensionDisable();
        extension.onExtensionEnable();
        event.getPlayer().sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessage("extensions-reload-config").replaceAll("%EXTENSION_NAME%", extension.getDescription().getExtensionName()));
    }
}
