package me.dreamdevs.github.randomlootchest.managers;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.api.objects.ChestGame;
import me.dreamdevs.github.randomlootchest.utils.ColourUtil;
import me.dreamdevs.github.randomlootchest.utils.TimeUtil;
import org.bukkit.OfflinePlayer;

import java.util.Objects;

public class PlaceholderAPIManager extends PlaceholderExpansion {

    @Override
    public String getIdentifier() {
        return "randomlootchest";
    }

    @Override
    public String getAuthor() {
        return "DreamDevs";
    }

    @Override
    public String getVersion() {
        return RandomLootChestMain.getInstance().getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean register() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if(params.equals("chests_amount")) {
            return String.valueOf(RandomLootChestMain.getInstance().getChestsManager().getChests().size());
        }
        if(params.equals("combat_status")) {
            return (RandomLootChestMain.getInstance().getCombatManager().isInCombat(Objects.requireNonNull(player.getPlayer()))) ? ColourUtil.colorize("&aIn Combat") : ColourUtil.colorize("&cNot in Combat");
        }
        if(params.equals("total_placed_chests")) {
            return String.valueOf(RandomLootChestMain.getInstance().getLocationManager().getLocations().size());
        }
        String[] strings = params.split("_");
        if(strings.length > 2) {
            if (RandomLootChestMain.getInstance().getChestsManager().getChests().containsKey(strings[1])) {
                ChestGame chestGame = RandomLootChestMain.getInstance().getChestsManager().getChestGameByRarity(strings[1]);
                if(strings[2].equals("title")) {
                    return chestGame.getTitle();
                }
                if(strings[2].equals("cooldown")) {
                    return TimeUtil.formattedTime(chestGame.getTime());
                }
                if(strings[2].equals("items_amount")) {
                    return String.valueOf(chestGame.getItems().size());
                }
            }
        }
        return super.onRequest(player, params);
    }
}