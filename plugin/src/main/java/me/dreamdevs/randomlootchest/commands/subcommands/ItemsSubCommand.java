package me.dreamdevs.randomlootchest.commands.subcommands;

import me.dreamdevs.randomlootchest.RandomLootChestMain;
import me.dreamdevs.randomlootchest.api.Language;
import me.dreamdevs.randomlootchest.api.command.ArgumentCommand;
import me.dreamdevs.randomlootchest.api.util.ColourUtil;
import me.dreamdevs.randomlootchest.menus.ItemsMenu;
import me.dreamdevs.randomlootchest.objects.RandomItem;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class ItemsSubCommand implements ArgumentCommand {

    @Override
    public boolean execute(CommandSender commandSender, String[] args) {
        if(!(commandSender instanceof Player player)) {
            commandSender.sendMessage(Language.GENERAL_NOT_PLAYER.toString());
            return false;
        }

        if (args.length == 1) {
            player.sendMessage(ColourUtil.colorize("&aYou opened items menu!"));
            new ItemsMenu().open(player);
            return true;
        }

        if(args.length >= 2) {
            if (!getArguments().contains(args[1])) {
                player.sendMessage(Language.GENERAL_NO_ARGUMENT.toString());
                return true;
            }

            if(args[1].equalsIgnoreCase("add")) {
                String id = args[2];
                if (id == null) {
                    player.sendMessage(Language.ITEMS_NO_ID.toString());
                    return true;
                }

                Double chance = Double.parseDouble(args[3]);
                if (chance == null) {
                    player.sendMessage(Language.ITEMS_SET_CHANCE.toString());
                    return true;
                }

                if (player.getInventory().getItemInMainHand() == null || player.getInventory().getItemInMainHand().getType() == Material.AIR) {
                    player.sendMessage(Language.ITEMS_NO_ITEM.toString());
                    return true;
                }

                if (RandomLootChestMain.getInstance().getItemsManager().getItems().containsKey(id)) {
                    player.sendMessage(Language.ITEMS_EXISTS.toString());
                    return true;
                }

                RandomItem randomItem = new RandomItem(player.getInventory().getItemInMainHand(), chance, false);
                RandomLootChestMain.getInstance().getItemsManager().getItems().put(id, randomItem);
                RandomLootChestMain.getInstance().getItemsManager().save();
                player.sendMessage(Language.ITEMS_ADDED_ITEM.toString());
                return true;
            } else if(args[1].equalsIgnoreCase("remove")) {
                String id = args[2];
                if (id == null) {
                    player.sendMessage(Language.ITEMS_NO_ID.toString());
                    return true;
                }

                if (!RandomLootChestMain.getInstance().getItemsManager().getItems().containsKey(id)) {
                    player.sendMessage(Language.ITEMS_NOT_EXISTS.toString());
                    return true;
                }

                RandomLootChestMain.getInstance().getItemsManager().getItems().remove(id);
                player.sendMessage(Language.ITEMS_REMOVED_ITEM.toString());
                RandomLootChestMain.getInstance().getItemsManager().save();
                return true;
            } else {
                player.sendMessage(Language.GENERAL_NO_ARGUMENT.toString());
                return true;
            }
        } else {
            player.sendMessage(Language.GENERAL_NO_ARGUMENT.toString());
        }
        return true;
    }

    @Override
    public String getHelpText() {
        return "&6/randomlootchest items [add/remove] [id] [chance] - opens items menu or adds/removes an item with specific id";
    }

    @Override
    public String getPermission() {
        return "randomlootchest.admin.items";
    }

    @Override
    public boolean hasArguments() {
        return true;
    }

    @Override
    public List<String> getArguments() {
        return Arrays.asList("add", "remove");
    }
}