package me.dreamdevs.github.randomlootchest.menus;

import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.api.events.ClickInventoryEvent;
import me.dreamdevs.github.randomlootchest.api.inventory.GItem;
import me.dreamdevs.github.randomlootchest.api.inventory.GUI;
import me.dreamdevs.github.randomlootchest.api.inventory.GUISize;
import me.dreamdevs.github.randomlootchest.api.inventory.actions.CloseAction;
import me.dreamdevs.github.randomlootchest.api.inventory.actions.PlaySoundAction;
import me.dreamdevs.github.randomlootchest.api.objects.ChestGame;
import me.dreamdevs.github.randomlootchest.utils.Settings;
import me.dreamdevs.github.randomlootchest.utils.Util;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ChestAddMenu {

    public ChestAddMenu(Player player, Location location) {
        GUI gui = new GUI("Select Chest Type", GUISize.sizeOf(RandomLootChestMain.getInstance().getChestsManager().getChests().size()));
        int x = 0;
        for(ChestGame chestGame : RandomLootChestMain.getInstance().getChestsManager().getChests().values()) {
            GItem gItem = new GItem(Material.CHEST, chestGame.getTitle(), new ArrayList<>());
            gItem.addActions(new CloseAction(), new PlaySoundAction(Sound.valueOf(Settings.soundChestAdd), 2.0f, 0.8f), new me.dreamdevs.github.randomlootchest.api.inventory.Action() {
                @Override
                public void performAction(ClickInventoryEvent event) {
                    RandomLootChestMain.getInstance().getLocationManager().addLocation(chestGame.getId(), location);
                    RandomLootChestMain.getInstance().getLocationManager().save();
                    player.sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessages().get("add-chest").replace("{CHEST}", chestGame.getTitle()).replace("{LOCATION}", Util.getLocationString(location)));
                }
            });
            gui.setItem(x, gItem);
            x++;
        }
        gui.openGUI(player);
    }

}