package me.mrsandking.github.randomlootchest.listener;

import me.mrsandking.github.randomlootchest.RandomLootChestMain;
import me.mrsandking.github.randomlootchest.objects.WandItem;
import me.mrsandking.github.randomlootchest.objects.ChestGame;
import me.mrsandking.github.randomlootchest.util.Settings;
import me.mrsandking.github.randomlootchest.util.TimeUtil;
import me.mrsandking.github.randomlootchest.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
        if(event.getPlayer().getItemInHand() == null) return;
        if(event.getBlock().getType() != Material.CHEST) return;
        if(!event.getPlayer().getItemInHand().equals(WandItem.WANDITEM))
            return;
        if(Settings.wandItemPermissionToUse && !event.getPlayer().hasPermission("wanditem.permission"))
            return;
        event.setCancelled(true);
        if(plugin.getLocationManager().getLocations().containsKey(Util.getLocationString(event.getBlock().getLocation())))
            return;
        locationHashMap.put(event.getPlayer().getUniqueId(), event.getBlock().getLocation());
        openMenu(event.getPlayer());
    }

    private void openMenu(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9, "Select Chest Type");
        int x = 0;
        for(ChestGame chestGame : plugin.getChestsManager().getChests().values()) {
            ItemStack itemStack = new ItemStack(Material.CHEST);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(chestGame.getTitle());
            itemStack.setItemMeta(itemMeta);
            inventory.setItem(x, itemStack);
            x++;
        }
        inventoryHashMap.put(player.getUniqueId(), inventory);
        player.openInventory(inventory);
    }

    @EventHandler
    public void clickInv(InventoryClickEvent event) {
        if(inventoryHashMap.containsKey(event.getWhoClicked().getUniqueId()) && inventoryHashMap.containsValue(event.getClickedInventory())) {
            event.setResult(Event.Result.DENY);
            ArrayList<ChestGame> list = new ArrayList(plugin.getChestsManager().getChests().values());
            if(event.getRawSlot() < list.size() && event.getRawSlot() >= 0 && event.getRawSlot() <= 8) {
                if(event.getRawSlot() > list.size()) return;

                event.getWhoClicked().sendMessage(plugin.getMessagesManager().getMessages().get("add-chest"));
                plugin.getLocationManager().addLocation(list.get(event.getRawSlot()).getId(), locationHashMap.get(event.getWhoClicked().getUniqueId()));
                event.getWhoClicked().closeInventory();
                locationHashMap.remove(event.getWhoClicked().getUniqueId());
                inventoryHashMap.remove(event.getWhoClicked().getUniqueId());
            }
        }
    }

    @EventHandler
    public void removeBlockEvent(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getPlayer().getItemInHand() == null) return;
        if (event.getClickedBlock().getType() != Material.CHEST) return;
        if (!event.getPlayer().getItemInHand().equals(WandItem.WANDITEM)) return;
        if(Settings.wandItemPermissionToUse && !event.getPlayer().hasPermission("wanditem.permission"))
            return;
        if (plugin.getLocationManager().getLocations().containsKey(Util.getLocationString(event.getClickedBlock().getLocation()))) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(plugin.getMessagesManager().getMessages().get("remove-chest"));
            plugin.getLocationManager().removeLocation(event.getClickedBlock().getLocation());
        }
    }

}