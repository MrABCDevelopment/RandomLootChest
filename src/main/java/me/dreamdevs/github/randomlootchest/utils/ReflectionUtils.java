package me.dreamdevs.github.randomlootchest.utils;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

@Getter
public class ReflectionUtils {

    public static String nmsVersion = VersionUtil.getVersion();
    public static MethodHandle playerConnection;
    public static String nms = "net.minecraft.server.";
    public static String craftBukkit = "org.bukkit.craftbukkit.";
    public static boolean useOldMethods = false;

    public static void sendActionBar(Player player, String message) {
        try {
            Object packet = null;
            Class<?> packetPlayOutChatClass = Class.forName("net.minecraft.server." + VersionUtil.getVersion() + ".PacketPlayOutChat");
            if (useOldMethods) {
                Class<?> chatSerializerClass = Class.forName("net.minecraft.server." + VersionUtil.getVersion()+ ".ChatSerializer");
                Class<?> iChatBaseComponentClass = Class.forName("net.minecraft.server." + VersionUtil.getVersion() + ".IChatBaseComponent");
                Method m3 = chatSerializerClass.getDeclaredMethod("a", String.class);
                Object cbc = iChatBaseComponentClass.cast(m3.invoke(chatSerializerClass, "{\"text\": \"" + message + "\"}"));
                packet = packetPlayOutChatClass.getConstructor(new Class<?>[]{iChatBaseComponentClass, byte.class}).newInstance(cbc, (byte) 2);
            } else {
                Class<?> chatComponentTextClass = Class.forName("net.minecraft.server." + VersionUtil.getVersion() + ".ChatComponentText");
                Class<?> iChatBaseComponentClass = Class.forName("net.minecraft.server." + VersionUtil.getVersion() + ".IChatBaseComponent");
                try {
                    Class<?> chatMessageTypeClass = Class.forName("net.minecraft.server." + VersionUtil.getVersion()+ ".ChatMessageType");
                    Object chatMessageType = chatMessageTypeClass.getDeclaredField("GAME_INFO").get(null);
                    Object chatCompontentText = chatComponentTextClass.getConstructor(new Class<?>[]{String.class}).newInstance(message);
                    packet = packetPlayOutChatClass.getConstructor(new Class<?>[]{iChatBaseComponentClass, chatMessageTypeClass}).newInstance(chatCompontentText, chatMessageType);
                } catch (ClassNotFoundException cnfe) {
                    Object chatCompontentText = chatComponentTextClass.getConstructor(new Class<?>[]{String.class}).newInstance(message);
                    packet = packetPlayOutChatClass.getConstructor(new Class<?>[]{iChatBaseComponentClass, byte.class}).newInstance(chatCompontentText, (byte) 2);
                } catch (NoSuchFieldException e) {

                }
            }
            sendPacket(player, packet);
        } catch (NoSuchMethodException | IllegalArgumentException | InvocationTargetException | InstantiationException |
                 IllegalAccessException | ClassNotFoundException exception) {
            player.sendMessage(ChatColor.RED+"Error while sending an action bar!");
        }
    }

    public static void sendPacket(Player player, Object packet) {
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