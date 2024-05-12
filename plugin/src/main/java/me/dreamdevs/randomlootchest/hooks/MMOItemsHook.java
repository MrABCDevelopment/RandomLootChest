package me.dreamdevs.randomlootchest.hooks;

import me.dreamdevs.randomlootchest.api.util.Util;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.Type;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.concurrent.Callable;

public class MMOItemsHook {

	public static MMOItemsHook INSTANCE;

	public MMOItemsHook() {
		INSTANCE = this;
		Util.sendPluginMessage("&aHooked into MMOItems!");
	}

	public Callable<ItemStack> getItemStack(@NotNull String type, @NotNull String itemId) {
		MMOItem mmoItem = MMOItems.plugin.getMMOItem(Type.get(type), itemId);
		if (mmoItem == null)
			return () -> null;
		return () -> mmoItem.newBuilder().build();
	}
}