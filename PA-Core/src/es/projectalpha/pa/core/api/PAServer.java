package es.projectalpha.pa.core.api;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import es.projectalpha.pa.core.PACore;
import lombok.Getter;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.HashMap;

public class PAServer {

    private static final HashMap<String, String> tp = new HashMap<>();
    private static final HashMap<String, String> tph = new HashMap<>();

    public static ArrayList<PAUser> users = new ArrayList<>();
    public static ArrayList<PAUser> afkMode = new ArrayList<>();
    @Getter private static ArrayList<PAUser> adminChatMode = new ArrayList<>();

    private static PACore plugin = PACore.getInstance();

    public static PAUser getUser(String id) {
        for (PAUser pl : users) {
            if (pl.getName() == null) continue;
            if (pl.getName().equalsIgnoreCase(id)) return pl;
        }
        PAUser us = new PAUser(id);
        if (us.isOnline()) users.add(us);
        return us;
    }

    public static PAUser getUser(OfflinePlayer p) {
        return getUser(p.getName());
    }

    public static HashMap<String, String> getTeleportRequests() {
        return tp;
    }

    public static HashMap<String, String> getTeleportHereRequests() {
        return tph;
    }

    public static void addTeleportRequest(String u1, String u2) {
        tp.put(u1, u2);
    }

    public static void addTeleportHereRequest(String u1, String u2) {
        tph.put(u1, u2);
    }

    public static void removeTeleportRequest(String u) {
        tp.remove(u);
    }

    public static void removeTeleportHereRequest(String u) {
        tph.remove(u);
    }

    public static String serverName(PAUser u) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("GetServer");
        ByteArrayDataInput in = ByteStreams.newDataInput(out.toByteArray());
        u.getPlayer().sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
        return in.readUTF();
    }
}
