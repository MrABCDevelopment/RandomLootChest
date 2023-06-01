package me.dreamdevs.github.randomlootchest.api.menu.place;

import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.api.events.ClickInventoryEvent;
import me.dreamdevs.github.randomlootchest.api.menu.MenuItem;
import me.dreamdevs.github.randomlootchest.api.objects.ChestGame;
import me.dreamdevs.github.randomlootchest.utils.Util;
import org.bukkit.Location;
import org.bukkit.Material;

public class ChestToPlaceMenuItem extends MenuItem
{

    private final ChestGame chestGame;
    private final Location location;

    public ChestToPlaceMenuItem(ChestGame chestGame, Location location) {
        super(Material.CHEST, chestGame.getTitle());
        this.chestGame = chestGame;
        this.location = location;
    }

    @Override
    public void performAction(ClickInventoryEvent event) {
        event.getPlayer().closeInventory();
        RandomLootChestMain.getInstance().getLocationManager().addLocation(chestGame.getId(), location);
        RandomLootChestMain.getInstance().getLocationManager().save();
        event.getPlayer().sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessages().get("chest-place-on-map").replaceAll("%TYPE%", chestGame.getTitle()).replaceAll("%LOCATION%", Util.getLocationString(location)));
    }
}