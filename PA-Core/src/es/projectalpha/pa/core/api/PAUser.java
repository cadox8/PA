package es.projectalpha.pa.core.api;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import es.projectalpha.pa.core.PACore;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.core.utils.ReflectionAPI;
import es.projectalpha.pa.core.utils.Sounds;
import es.projectalpha.pa.core.utils.Utils;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.ArrayList;

public class PAUser {

    private PACore plugin = PACore.getInstance();

    @Getter private String name;
    @Getter @Setter private UserData userData;

    public PAUser(OfflinePlayer p) {
        this(p.getName());
    }

    public PAUser(String name) {
        this.name = name;
        setUserData(plugin.getMysql().loadUserData(name));
    }

    public OfflinePlayer getOfflinePlayer() {
        return plugin.getServer().getOfflinePlayer(name);
    }

    public Player getPlayer() {
        return plugin.getServer().getPlayer(name);
    }


    public void save() {
        plugin.getMysql().saveUser(this);
        PAServer.users.remove(this);
        plugin.getMysql().loadUserData(name);
        PAServer.users.add(this);
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
    public Location getLoc() {
        return getPlayer().getLocation();
    }
    public World getWorld() {
        return getPlayer().getWorld();
    }

    /**
     * Methods
     */
    public void sendDiv() {
        getPlayer().sendMessage(Utils.colorize("&e====================="));
    }
    public void sendMessage(String str) {
        getPlayer().sendMessage(Utils.colorize(str));
    }
    public void sendSound(Sounds sound) {
        getPlayer().playSound(getPlayer().getLocation(), sound.toSound(), 4, 4);
    }
    public void teleport(Location location) {
        getPlayer().teleport(location);
    }
    public void teleport(Entity entity) {
        getPlayer().teleport(entity);
    }
    public void teleport(World world) {
        teleport(world.getSpawnLocation());
    }
    public void removeItemInHand() {
        getPlayer().getInventory().setItemInHand(new ItemStack(Material.AIR));
    }

    public void heal() {
        getPlayer().setHealth(getPlayer().getMaxHealth());
        getPlayer().setFoodLevel(20);
        getPlayer().getActivePotionEffects().forEach(p -> getPlayer().removePotionEffect(p.getType()));
    }

    public void toggleAdminChat() {
        if (!PAServer.getAdminChatMode().contains(this)) {
            sendMessage(PAData.CORE.getPrefix() + "&2AdminChat Activado");
            PAServer.getAdminChatMode().add(this);
        } else {
            sendMessage(PAData.CORE.getPrefix() + "&2AdminChat Desactivado");
            PAServer.getAdminChatMode().remove(this);
        }
    }

    public void toggleFly() {
        if (getPlayer().isFlying()) {
            sendMessage(PAData.CORE.getPrefix() + "&cVuelo desactivado");
            getPlayer().setAllowFlight(false);
        } else {
            sendMessage(PAData.CORE.getPrefix() + "&2Vuelo activado");
            getPlayer().setAllowFlight(true);
        }
    }


    /**
     * Reflection
     */
    public void sendActionBar(String msg) {
        try {
            Constructor<?> constructor = ReflectionAPI.getNMSClass("PacketPlayOutChat").getConstructor(ReflectionAPI.getNMSClass("IChatBaseComponent"), byte.class);
            Object icbc = ReflectionAPI.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + Utils.colorize(msg) + "\"}");
            Object packet = constructor.newInstance(icbc, (byte) 2);

            ReflectionAPI.sendPacket(getPlayer(), packet);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void sendHeaderAndFooter(String headerText, String footerText) {
        try {
            Class chatSerializer = ReflectionAPI.getNMSClass("IChatBaseComponent$ChatSerializer");

            Object tabHeader = chatSerializer.getMethod("a", String.class).invoke(chatSerializer, "{'text': '" + Utils.colorize(headerText) + "'}");
            Object tabFooter = chatSerializer.getMethod("a", String.class).invoke(chatSerializer, "{'text': '" + Utils.colorize(footerText) + "'}");
            Object tab = ReflectionAPI.getNMSClass("PacketPlayOutPlayerListHeaderFooter").getConstructor(new Class[]{ReflectionAPI.getNMSClass("IChatBaseComponent")}).newInstance(new Object[]{tabHeader});

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
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
        }
        return -1;
    }


    /**
     * Json
     */
    public void jsonURL(String text, String hover, String url) {
        TextComponent message = new TextComponent(Utils.colorize(text));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Utils.colorize(hover)).create()));
        getPlayer().spigot().sendMessage(message);
    }

    public void jsonMessages(String text, String hover, String command) {
        TextComponent message = new TextComponent(Utils.colorize(text));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Utils.colorize(hover)).create()));
        getPlayer().spigot().sendMessage(message);
    }


    /**
     * Bungee
     */
    public void sendToServer(String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server);
        getPlayer().sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
        sendMessage(PAData.BUNGEE.getPrefix() + "&cConectando a &6" + server);
    }

    public void sendToLobby() {
        sendToServer("lobby");
    }

    @Override
    public String toString() {
        return "PAUser{name: " + getName() + ",  group: " + getUserData().getGrupo() + "}";
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

        //Logros
        ArrayList<Integer> logros = new ArrayList<>();

        //TOA
        Integer maxPiso = 0;

        Integer exp = 0;
        Integer lvl = 1;

        Integer zeny = 0;

        Integer kills = 0;
        Integer deaths = 0;

        Integer kit = -1;

        public UserData() {
        }
    }
}
