package me.dreamdevs.github.randomlootchest.api.menu.extensions;

import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.api.extensions.Extension;
import me.dreamdevs.github.randomlootchest.api.menu.Menu;
import me.dreamdevs.github.randomlootchest.api.menu.MenuSize;
import org.bukkit.entity.Player;

public class ExtensionsMenu extends Menu
{

    public ExtensionsMenu(Player player) {
        super(RandomLootChestMain.getInstance().getMessagesManager().getMessage("extensions-menu-title"), MenuSize.SIX_ROWS);

        for(Extension extension : RandomLootChestMain.getInstance().getExtensionManager().getEnabledExtensions()) {
            addItem(new ExtensionMenuItem(extension));
        }

        open(player);
    }

}