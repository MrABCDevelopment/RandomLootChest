package me.mrsandking.randomlootchest.util;

import me.dreamdevs.github.randomlootchest.utils.ReflectionUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.UUID;

public class PlayerUtils {

    public static void createArmorStands(List<Location> locations) {
        for(Location location : locations)
            createArmorStand(location);
    }

    public static void createArmorStand(Location location) {
        try {
            Class<?> entityClass = ReflectionUtils.getNMSClass("EntityArmorStand");
            Constructor<?> entityConstructor = entityClass.getConstructor(new Class[]{ReflectionUtils.getNMSClass("World"), double.class, double.class, double.class});

            Object w = location.getWorld().getClass().getMethod("getHandle", new Class[0]).invoke(location.getWorld(), new Object[0]);
            Object entity = entityConstructor.newInstance(new Object[]{w, location.getX(), location.getY(), location.getZ()});

            Bukkit.broadcastMessage(entity.toString());
        } catch (Exception e) {

        }
    }

    public static void updateArmorStand(Player player, Object armor, String displayName) {
        try {
            Class<?> entityMetaPacket = ReflectionUtils.getNMSClass("PacketPlayOutEntityMetadata");
            Constructor<?> entityMetaConstructor = entityMetaPacket.getConstructor(new Class[] { int.class, ReflectionUtils.getNMSClass("DataWatcher"), boolean.class });
            Object icbc = ReflectionUtils.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { "{\"text\":\"" + displayName + "\"}" });

            Object id = armor.getClass().getMethod("getId", new Class[0]).invoke(armor, new Object[0]);
            Object dataWatcher = armor.getClass().getMethod("getDataWatcher", new Class[0]).invoke(armor, new Object[0]);

            armor.getClass().getMethod("setCustomName", new Class[] { ReflectionUtils.getNMSClass("IChatBaseComponent") }).invoke(armor, new Object[] {icbc});
            armor.getClass().getMethod("setCustomNameVisible", new Class[] {boolean.class}).invoke(armor, new Object[]{true});
            Object packet = entityMetaConstructor.newInstance(new Object[] { id, dataWatcher, true });
            ReflectionUtils.sendPacket(player, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateArmorStand(Player player, Location location, String displayName) {
        try {
            Class<?> entityMetaPacket = ReflectionUtils.getNMSClass("PacketPlayOutEntityMetadata");
            Class<?> entityClass = ReflectionUtils.getNMSClass("EntityArmorStand");

            Constructor<?> entityConstructor = entityClass.getConstructor(new Class[] { ReflectionUtils.getNMSClass("World"), double.class, double.class, double.class });
            Constructor<?> entityMetaConstructor = entityMetaPacket.getConstructor(new Class[] { int.class, ReflectionUtils.getNMSClass("DataWatcher"), boolean.class });

            Object icbc = ReflectionUtils.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { "{\"text\":\"" + displayName + "\"}" });
            Object w = location.getWorld().getClass().getMethod("getHandle", new Class[0]).invoke(location.getWorld(), new Object[0]); //ReflectionUtils.getCraftClass("CraftWorld").getMethod("getHandle", new Class[0]);
            Object entity = entityConstructor.newInstance(new Object[] { w, location.getX(), location.getY(), location.getZ() });

            Object id = entity.getClass().getMethod("getId", new Class[0]).invoke(entity, new Object[0]);
            Object dataWatcher = entity.getClass().getMethod("getDataWatcher", new Class[0]).invoke(entity, new Object[0]);

            entity.getClass().getMethod("setCustomName", new Class[] { ReflectionUtils.getNMSClass("IChatBaseComponent") }).invoke(entity, new Object[] {icbc});
            Object packet = entityMetaConstructor.newInstance(new Object[] { id, dataWatcher, true });
            ReflectionUtils.sendPacket(player, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void spawnArmorStand(Player player, Object armor) {
        try {
            Class<?> spawnEntityPacket = ReflectionUtils.getNMSClass("PacketPlayOutSpawnEntity");
            Constructor<?> spawnEntityConstructor = spawnEntityPacket.getConstructor(new Class[] { ReflectionUtils.getNMSClass("Entity"), int.class });

            Object packet = spawnEntityConstructor.newInstance(new Object[] { armor.getClass().cast(ReflectionUtils.getNMSClass("EntityArmorStand")), 1 });
            ReflectionUtils.sendPacket(player, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void spawnItem(Player player, ItemStack itemStack) {
        try {

            Class<?> inventoryPacket = ReflectionUtils.getNMSClass("PacketPlayOutSpawnEntity");

            Class<?> craftWorldClass = ReflectionUtils.getCraftClass("CraftWorld");
            Class<?> entityClass = ReflectionUtils.getNMSClass("EntityItem");
            Class<?> bukkitItem = ReflectionUtils.getCraftClass("inventory.CraftItemStack");
            Constructor<?> inventoryConstructor = inventoryPacket.getConstructor(new Class[] { ReflectionUtils.getNMSClass("Entity"), int.class });
            Constructor<?> entityConstructor = entityClass.getConstructor(new Class[] { ReflectionUtils.getNMSClass("World"), double.class, double.class, double.class, ReflectionUtils.getNMSClass("ItemStack") });

            Object bukkitFinalItem = bukkitItem.getMethod("asNMSCopy", new Class[] { ItemStack.class }).invoke(null, new Object[] { itemStack });
            Object nmsWorld = craftWorldClass.getMethod("getHandle", new Class[0]);

            Bukkit.broadcastMessage(bukkitFinalItem.toString());
            Bukkit.broadcastMessage(nmsWorld.toString());

            Object finalEntity = entityConstructor.newInstance(new Object[] {nmsWorld, 0, 0, 0, bukkitFinalItem});

            Bukkit.broadcastMessage(bukkitFinalItem.toString());
            Bukkit.broadcastMessage(nmsWorld.toString());

            Object packet = inventoryConstructor.newInstance(new Object[] { finalEntity, 1 });
            ReflectionUtils.sendPacket(player, packet);
            sendMessage(player, "Poszlo ci swietnie!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendMessage(Player player, String message) {
        try {
            Class<?> chatPacket = ReflectionUtils.getNMSClass("PacketPlayOutChat");
            Object icbc = ReflectionUtils.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { "{\"text\":\"" + message + "\"}" });
            Object chatType = ReflectionUtils.getNMSClass("ChatMessageType").getField("CHAT").get(null);
            Constructor<?> constructor = chatPacket.getConstructor(new Class[] { ReflectionUtils.getNMSClass("IChatBaseComponent"), ReflectionUtils.getNMSClass("ChatMessageType"), UUID.class});
            Object packet = constructor.newInstance(new Object[] {icbc, chatType, player.getUniqueId()});
            ReflectionUtils.sendPacket(player, packet);
        } catch (Exception e) {}
    }

}