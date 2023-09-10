package menu.extensions;

import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.api.extensions.Extension;
import menu.BasicMenu;
import menu.MenuSize;
import org.bukkit.entity.Player;

public class ExtensionsMenu extends BasicMenu
{

    public ExtensionsMenu(Player player) {
        super(RandomLootChestMain.getInstance().getMessagesManager().getMessage("extensions-menu-title"), MenuSize.SIX_ROWS, true);

        for(Extension extension : RandomLootChestMain.getInstance().getExtensionManager().getEnabledExtensions()) {
            addItem(new ExtensionMenuItem(extension));
        }

        open(player);
    }

}