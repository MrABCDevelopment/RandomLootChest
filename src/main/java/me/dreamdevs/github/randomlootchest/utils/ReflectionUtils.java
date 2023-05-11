package me.dreamdevs.github.randomlootchest.utils;

import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

@UtilityClass
public class ReflectionUtils {

    public static final String VERSION = VersionUtil.getVersion();
    public static MethodHandle playerConnection;
    public static final String NMS = "net.minecraft.server.";
    public static final String CRAFTBUKKIT = "org.bukkit.craftbukkit.";

    public static void sendActionBar(Player player, String message) {
        try {
            Object packet = null;
            Class<?> packetPlayOutChatClass = Class.forName(NMS + VERSION + ".PacketPlayOutChat");

            Class<?> chatComponentTextClass = getNMSClass("ChatComponentText");
            Class<?> iChatBaseComponentClass = getNMSClass("IChatBaseComponent");
            try {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ColourUtil.colorize(message)));
                return;
            } catch (Exception e) {
                Object chatCompontentText = chatComponentTextClass.getConstructor(new Class<?>[]{String.class}).newInstance(message);
                packet = packetPlayOutChatClass.getConstructor(new Class<?>[]{iChatBaseComponentClass, byte.class}).newInstance(chatCompontentText, (byte) 2);
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
            return Class.forName(CRAFTBUKKIT + VERSION +"."+name);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static Class<?> getNMSClass(String name) {
        try {
            return Class.forName(NMS + VERSION +"."+name);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }

}