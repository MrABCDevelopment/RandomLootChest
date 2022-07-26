package me.mrsandking.github.randomlootchest.listener;

import me.mrsandking.github.randomlootchest.RandomLootChestMain;
import me.mrsandking.github.randomlootchest.events.RLCClickInventoryEvent;
import me.mrsandking.github.randomlootchest.events.RLCOpenInventoryEvent;
import me.mrsandking.github.randomlootchest.inventory.GItem;
import me.mrsandking.github.randomlootchest.inventory.GUI;
import me.mrsandking.github.randomlootchest.inventory.GUISize;
import me.mrsandking.github.randomlootchest.inventory.actions.CloseAction;
import me.mrsandking.github.randomlootchest.inventory.actions.PlaySoundAction;
import me.mrsandking.github.randomlootchest.objects.WandItem;
import me.mrsandking.github.randomlootchest.objects.ChestGame;
import me.mrsandking.github.randomlootchest.util.Settings;
import me.mrsandking.github.randomlootchest.util.TimeUtil;
import me.mrsandking.github.randomlootchest.util.Util;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class PlayerInteractListener implements Listener {

    private RandomLootChestMain plugin;
    private HashMap<UUID, Inventory> inventoryHashMap = new HashMap<>();
    private HashMap<UUID, Location> locationHashMap = new HashMap<>();

    public PlayerInteractListener(RandomLootChestMain plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteractChest(PlayerInteractEvent event) {
        if(event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if(!plugin.getLocationManager().getLocations().containsKey(Util.getLocationString(event.getClickedBlock().getLocation()))) return;
        if(event.getClickedBlock().getType() == Material.CHEST) {
            event.setCancelled(true);

            if(plugin.getCooldownManager().isOnCooldown(event.getPlayer().getUniqueId(), event.getClickedBlock().getLocation())) {
                if(plugin.getHolographicDisplaysHook() != null && Settings.hologramsInfo) {
                    if (!plugin.getHolographicDisplaysHook().getMaps().containsKey(event.getPlayer().getUniqueId()))
                        plugin.getHolographicDisplaysHook().createTempHolo(event.getPlayer(), event.getClickedBlock().getLocation());
                } else
                    event.getPlayer().sendMessage(plugin.getMessagesManager().getMessages().get("gamechest-cooldown").replace("[time]", TimeUtil.formattedTime(plugin.getCooldownManager().getPlayerCooldown(event.getPlayer().getUniqueId(), event.getClickedBlock().getLocation()))));
                return;
            }

            if(Settings.randomChests) {
                ChestGame chestGame = plugin.getChestsManager().getRandomChest();
                plugin.getGameManager().openChest(event.getPlayer(), chestGame.getId());
                plugin.getCooldownManager().setCooldown(event.getPlayer().getUniqueId(), event.getClickedBlock().getLocation(), chestGame.getTime());
                return;
            }

            String type = plugin.getLocationManager().getLocations().get(Util.getLocationString(event.getClickedBlock().getLocation()));
            plugin.getGameManager().openChest(event.getPlayer(), type);
            plugin.getCooldownManager().setCooldown(event.getPlayer().getUniqueId(), event.getClickedBlock().getLocation(), plugin.getChestsManager().getChestGameByRarity(type).getTime());
        }
    }

    @EventHandler
    public void addBlockEvent(BlockBreakEvent event) {
        if(event.getPlayer().getInventory().getItemInMainHand() == null) return;
        if(event.getBlock().getType() != Material.CHEST) return;
        if(plugin.getLocationManager().getLocations().containsKey(Util.getLocationString(event.getBlock().getLocation()))) {
            event.setCancelled(true);
        } else {
            if (!event.getPlayer().getInventory().getItemInMainHand().equals(WandItem.WANDITEM))
                return;
            if (Settings.wandItemPermissionToUse && !event.getPlayer().hasPermission("wanditem.permission"))
                return;
            event.setCancelled(true);
            locationHashMap.put(event.getPlayer().getUniqueId(), event.getBlock().getLocation());
            openMenu(event.getPlayer());
        }
    }

    private void openMenu(Player player) {
        GUI gui = new GUI("Select Chest Type", GUISize.sizeOf(plugin.getChestsManager().getChests().size()));
        for(ChestGame chestGame : plugin.getChestsManager().getChests().values()) {
            GItem gItem = new GItem(Material.CHEST, chestGame.getTitle(), new ArrayList<>());
            gItem.addActions(new CloseAction(), new PlaySoundAction(Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0f, 0.8f), new me.mrsandking.github.randomlootchest.inventory.Action() {
                @Override
                public void performAction(RLCClickInventoryEvent event) {
                    if(inventoryHashMap.containsKey(event.getPlayer().getUniqueId()) && inventoryHashMap.containsValue(event.getGui().getInventory())) {
                        ArrayList<ChestGame> list = new ArrayList(plugin.getChestsManager().getChests().values());
                        if(event.getSlot() < list.size() && event.getSlot() >= 0 && event.getSlot() <= 8) {
                            if(event.getSlot() > list.size()) return;

                            event.getPlayer().sendMessage(plugin.getMessagesManager().getMessages().get("add-chest").replace("{CHEST}", chestGame.getTitle()).replace("{LOCATION}", Util.getLocationString(locationHashMap.get(player.getUniqueId()))));
                            plugin.getLocationManager().addLocation(list.get(event.getSlot()).getId(), locationHashMap.get(event.getPlayer().getUniqueId()));
                            locationHashMap.remove(event.getPlayer().getUniqueId());
                            inventoryHashMap.remove(event.getPlayer().getUniqueId());
                        }
                    }
                }
            });
            gui.addItem(gItem);
        }
        inventoryHashMap.put(player.getUniqueId(), gui.getInventory());
        gui.openGUI(player);
    }

    @EventHandler
    public void removeBlockEvent(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getPlayer().getInventory().getItemInMainHand() == null) return;
        if (event.getClickedBlock().getType() != Material.CHEST) return;
        if (!event.getPlayer().getInventory().getItemInMainHand().equals(WandItem.WANDITEM)) return;
        if(Settings.wandItemPermissionToUse && !event.getPlayer().hasPermission("wanditem.permission"))
            return;
        if (plugin.getLocationManager().getLocations().containsKey(Util.getLocationString(event.getClickedBlock().getLocation()))) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(plugin.getMessagesManager().getMessages().get("remove-chest"));
            plugin.getLocationManager().removeLocation(event.getClickedBlock().getLocation());
        }
    }

    @EventHandler
    public void openChestEvent(RLCOpenInventoryEvent event) {
        if(plugin.getVaultHook() != null) {
            if(event.getChestGame().getMoney() != null) {
                int money = event.getChestGame().getMoney().getMoney();
                if (money > 0) {
                    plugin.getVaultHook().addMoney(event.getPlayer(), money);
                    event.getPlayer().sendMessage(plugin.getMessagesManager().getMessages().get("found-money").replace("{MONEY}", String.valueOf(money)));
                } else if (money < 0) {
                    plugin.getVaultHook().removeMoney(event.getPlayer(), money);
                    event.getPlayer().sendMessage(plugin.getMessagesManager().getMessages().get("lost-money").replace("{MONEY}", String.valueOf(money)));
                }
            }
        }
    }

}