package me.dreamdevs.github.randomlootchest.menus;

import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.api.events.ChestDeleteEvent;
import me.dreamdevs.github.randomlootchest.api.events.ClickInventoryEvent;
import me.dreamdevs.github.randomlootchest.api.inventory.GItem;
import me.dreamdevs.github.randomlootchest.api.inventory.GUI;
import me.dreamdevs.github.randomlootchest.api.inventory.GUISize;
import me.dreamdevs.github.randomlootchest.api.inventory.actions.CloseAction;
import me.dreamdevs.github.randomlootchest.api.inventory.actions.PlaySoundAction;
import me.dreamdevs.github.randomlootchest.objects.ChestGame;
import me.dreamdevs.github.randomlootchest.utils.Settings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ChestRemoveMenu {

    public ChestRemoveMenu(Player player) {
        GUI gui = new GUI("Select Chest to Delete", GUISize.sizeOf(RandomLootChestMain.getInstance().getChestsManager().getChests().size()));
        for(ChestGame chestGame : RandomLootChestMain.getInstance().getChestsManager().getChests().values()) {
            GItem gItem = new GItem(Material.CHEST, chestGame.getTitle(), new ArrayList<>());
            gItem.addActions(new CloseAction(), new PlaySoundAction(Sound.valueOf(Settings.soundChestAdd), 2.0f, 0.8f), new me.dreamdevs.github.randomlootchest.api.inventory.Action() {
                @Override
                public void performAction(ClickInventoryEvent event) {
                    chestGame.delete();
                    player.sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessages().get("delete-chest").replace("{CHEST}", chestGame.getTitle()));
                    ChestDeleteEvent chestDeleteEvent = new ChestDeleteEvent(event.getPlayer().getUniqueId(), chestGame);
                    Bukkit.getPluginManager().callEvent(chestDeleteEvent);
                }
            });
            gui.addItem(gItem);
        }
        gui.openGUI(player);
    }

}