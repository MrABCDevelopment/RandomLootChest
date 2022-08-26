package me.dreamdevs.github.randomlootchest.menus;

import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.api.events.ClickInventoryEvent;
import me.dreamdevs.github.randomlootchest.api.inventory.Action;
import me.dreamdevs.github.randomlootchest.api.inventory.GItem;
import me.dreamdevs.github.randomlootchest.api.inventory.GUI;
import me.dreamdevs.github.randomlootchest.api.inventory.GUISize;
import me.dreamdevs.github.randomlootchest.api.inventory.actions.CloseAction;
import me.dreamdevs.github.randomlootchest.utils.Settings;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ChestReloadMenu {

    public ChestReloadMenu(Player player) {
        GUI gui = new GUI("Reload Section", GUISize.ONE_ROW);

        GItem chestsReload = new GItem(Material.CHEST, ChatColor.GOLD+"Reload Chests", new ArrayList<>());
        chestsReload.addActions(new CloseAction(), new Action() {
            @Override
            public void performAction(ClickInventoryEvent event) {
                RandomLootChestMain.getInstance().getChestsManager().load(RandomLootChestMain.getInstance());
                event.getPlayer().sendMessage(ChatColor.GREEN+"Reloaded all chests!");
            }
        });

        GItem item = new GItem(Material.BOOK, ChatColor.GOLD+"Reload config.yml", new ArrayList<>());
        item.addActions(new CloseAction(), new Action() {
            @Override
            public void performAction(ClickInventoryEvent event) {
                new Settings(RandomLootChestMain.getInstance());
                event.getPlayer().sendMessage(ChatColor.GREEN+"Reloaded config.yml!");
            }
        });

        GItem messages = new GItem(Material.PAPER, ChatColor.GOLD+"Reload messages.yml", new ArrayList<>());
        messages.addActions(new CloseAction(), new Action() {
            @Override
            public void performAction(ClickInventoryEvent event) {
                RandomLootChestMain.getInstance().getConfigManager().reload("messages.yml");
                event.getPlayer().sendMessage(ChatColor.GREEN+"Reloaded messages.yml!");
            }
        });

        gui.addItem(item);
        gui.addItem(chestsReload);
        gui.addItem(messages);
        gui.openGUI(player);
    }

}