package me.mrsandking.randomlootchest.util;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.invoke.MethodHandle;
import java.util.Objects;

@Getter
public class ReflectionUtils {

    public static String nmsVersion;
    public static MethodHandle playerConnection;
    public static String nms = "net.minecraft.server.";
    public static String craftBukkit = "org.bukkit.craftbukkit.";

    static {
        String version = Bukkit.getServer().getClass().getPackage().getName();
        nmsVersion = version.substring(version.lastIndexOf(".")+1);
    }

    public static void packet(Player player, Object packet) {
        try {
            Object connection = getConnection(player);
            connection.getClass().getMethod("sendPacket", new Class[] { getNMSClass("Packet") }).invoke(getConnection(player), new Object[] { packet });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public static Object getHandle(Player player) {
        Objects.requireNonNull(player, "Cannot get handle of null player");
        try {
            return player.getClass().getMethod("getHandle", new Class[0]).invoke(player, new Object[0]);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }

    public static Object getConnection(Player player) {
        Objects.requireNonNull(player, "Cannot get connection of null player");
        try {
            return getHandle(player).getClass().getField("playerConnection").get(getHandle(player));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }

    public static Class<?> getCraftClass(String name) {
        try {
            return Class.forName(craftBukkit + nmsVersion+"."+name);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static Class<?> getNMSClass(String name) {
        try {
            return Class.forName(nms + nmsVersion+"."+name);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }

}