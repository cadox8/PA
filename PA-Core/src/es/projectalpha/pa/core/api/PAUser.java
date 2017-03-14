package es.projectalpha.pa.core.api;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import es.projectalpha.pa.core.PACore;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.core.utils.ReflectionAPI;
import es.projectalpha.pa.core.utils.Utils;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.UUID;

public class PAUser {

    private PACore plugin = PACore.getInstance();

    @Getter private UUID uuid;
    @Getter @Setter private UserData userData;

    public PAUser(OfflinePlayer p) {
        this(p.getUniqueId());
    }

    public PAUser(UUID id) {
        uuid = id;
    }

    public OfflinePlayer getOfflinePlayer() {
        return plugin.getServer().getOfflinePlayer(uuid);
    }
    public Player getPlayer() {
        return plugin.getServer().getPlayer(uuid);
    }
    //

    /**
    * Getters/Setters
    */
    public String getName() {
        return getOfflinePlayer().getName();
    }
    public boolean isOnline() {
        return getOfflinePlayer().isOnline();
    }
    public boolean isOnRank(PACmd.Grupo rank) {
        return rank.getRank() <= getUserData().getGrupo().getRank();
    }

    /**
    * Methods
    */
    public void sendDiv(){
        getPlayer().sendMessage(Utils.colorize("&e====================="));
    }

    public void sendMessage(String str) {
        getPlayer().sendMessage(Utils.colorize(str));
    }
    public void sendMessagePrefix(String str) {
        getPlayer().sendMessage(PACore.getPrefix() + Utils.colorize(str));
    }

    public void sendSound(Sound sound){
        getPlayer().playSound(getPlayer().getLocation(), sound, 1, 1);
    }

    public void teleport(Location location){
        getPlayer().teleport(location, PlayerTeleportEvent.TeleportCause.COMMAND);
    }
    public void teleport(Entity entity){
        getPlayer().teleport(entity, PlayerTeleportEvent.TeleportCause.COMMAND);
    }
    public void teleport(World world){
        teleport(world.getSpawnLocation());
    }

    public void removeItemInHand(){
        getPlayer().getInventory().setItemInMainHand(new ItemStack(Material.AIR));
    }

    public void toggleAdminChat() {
        if (!PAServer.getAdminChatMode().contains(this)) {
            sendMessagePrefix("&2AdminChat Activado");
            PAServer.getAdminChatMode().add(this);
        } else {
            sendMessagePrefix("&2AdminChat Desactivado");
            PAServer.getAdminChatMode().remove(this);
        }
    }

    public void toggleFly(){
        if (getPlayer().isFlying()){
            sendMessagePrefix("&cVuelo desactivado");
            getPlayer().setAllowFlight(false);
        } else {
            sendMessagePrefix("&2Vuelo activado");
            getPlayer().setAllowFlight(true);
        }
    }



    /**
     * Reflection
     */
    public void sendActionBar(String msg) {
        try {
            Constructor<?> constructor = ReflectionAPI.getNmsClass("PacketPlayOutChat").getConstructor(ReflectionAPI.getNmsClass("IChatBaseComponent"), byte.class);
            Object icbc = ReflectionAPI.getNmsClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + Utils.colorize(msg) + "\"}");
            Object packet = constructor.newInstance(icbc, (byte) 2);

            ReflectionAPI.sendPacket(getPlayer(), packet);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void sendHeaderAndFooter(String headerText, String footerText) {
        try {
            Class chatSerializer = ReflectionAPI.getNmsClass("IChatBaseComponent$ChatSerializer");

            Object tabHeader = chatSerializer.getMethod("a", String.class).invoke(chatSerializer, "{'text': '" + Utils.colorize(headerText) + "'}");
            Object tabFooter = chatSerializer.getMethod("a", String.class).invoke(chatSerializer, "{'text': '" + Utils.colorize(footerText) + "'}");
            Object tab = ReflectionAPI.getNmsClass("PacketPlayOutPlayerListHeaderFooter").getConstructor(new Class[]{ReflectionAPI.getNmsClass("IChatBaseComponent")}).newInstance(new Object[]{tabHeader});

            Field f = tab.getClass().getDeclaredField("b");
            f.setAccessible(true);
            f.set(tab, tabFooter);

            ReflectionAPI.sendPacket(getPlayer(), tab);
        } catch (IllegalAccessException | InstantiationException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public int getPing() {
        try {
            Method getHandleMethod = getPlayer().getClass().getDeclaredMethod("getHandle");
            getHandleMethod.setAccessible(true);

            Object entityPlayer = getHandleMethod.invoke(getPlayer());

            Field pingField = entityPlayer.getClass().getDeclaredField("ping");
            pingField.setAccessible(true);

            return pingField.getInt(entityPlayer);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {}
        return -1;
    }

    /**
    * Json
    */
    public void jsonURL(String text, String hover, String url){
        TextComponent message = new TextComponent(Utils.colorize(text));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Utils.colorize(hover)).create()));
        getPlayer().spigot().sendMessage(message);
    }

    public  void jsonMessages(String text, String hover, String command){
        TextComponent message = new TextComponent(Utils.colorize(text));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Utils.colorize(hover)).create()));
        getPlayer().spigot().sendMessage(message);
    }



    /**
     * Bungee
     */
    public void sendToServer(String str) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(str);
        getPlayer().sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
    }

    public void sendToLobby() {
        sendToServer("lobby");
    }





    /**
    * UserData (Cremita para nosotros)
    */
    @Data
    public static class UserData {
        PACmd.Grupo grupo = PACmd.Grupo.Usuario;
        Location lastLocation = null;
        Boolean god = false;
        Long lastConnect = 0L;
        Long timeJoin = 0L;
        Long timePlayed = 0L;
        String nickname = null;
        Integer coins = 0;
        InetSocketAddress ip = null;

        public UserData() {}
    }


    @Override
    public String toString() {
        return "PAUser{name: " + getName() + ", uuid: " + getUuid() + ", group: + " + getUserData().getGrupo() + "}";
    }
}
