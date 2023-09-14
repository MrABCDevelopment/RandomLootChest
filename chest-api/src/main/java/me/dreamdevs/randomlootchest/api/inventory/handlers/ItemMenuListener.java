package me.dreamdevs.randomlootchest.api.inventory.handlers;

import lombok.Getter;
import me.dreamdevs.randomlootchest.api.inventory.ItemMenu;
import me.dreamdevs.randomlootchest.api.inventory.ItemMenuHolder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.java.JavaPlugin;

public class ItemMenuListener implements Listener {

	private Plugin plugin = null;
	private @Getter static final ItemMenuListener instance = new ItemMenuListener();

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onInventoryClick(InventoryClickEvent event)
	{
		if (event.getWhoClicked() instanceof Player && event.getInventory().getHolder() != null && event.getInventory().getHolder() instanceof ItemMenuHolder)
		{
			ItemMenu menu = ((ItemMenuHolder)event.getInventory().getHolder()).getMenu();
			event.setCancelled(true);
			((ItemMenuHolder) event.getInventory().getHolder()).getMenu().onInventoryClick(event);
		}
	}

	public void register(JavaPlugin plugin)
	{
		if (!isRegistered(plugin))
		{
			plugin.getServer().getPluginManager()
					.registerEvents(instance, plugin);
			this.plugin = plugin;
		}
	}

	public boolean isRegistered(JavaPlugin plugin)
	{
		if (plugin.equals(this.plugin))
		{
			for (RegisteredListener listener : HandlerList
					.getRegisteredListeners(plugin))
			{
				if (listener.getListener().equals(instance))
				{
					return true;
				}
			}
		}
		return false;
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPluginDisable(PluginDisableEvent event)
	{
		if (event.getPlugin().equals(plugin))
		{
			closeOpenMenus();
			plugin = null;
		}
	}

	public static void closeOpenMenus()
	{
		for (Player player : Bukkit.getOnlinePlayers())
		{
			if (player.getOpenInventory() != null)
			{
				Inventory inventory = player.getOpenInventory()
						.getTopInventory();
				if (inventory.getHolder() instanceof ItemMenuHolder)
				{
					player.closeInventory();
				}
			}
		}
	}
}