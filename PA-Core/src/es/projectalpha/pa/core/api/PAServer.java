package es.projectalpha.pa.core.api;

import es.projectalpha.pa.core.PACore;
import lombok.Getter;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class PAServer {

/*    private static final HashMap<UUID, UUID> tp = new HashMap<>();
    private static final HashMap<UUID, UUID> tph = new HashMap<>();*/
    public static ArrayList<PAUser> users = new ArrayList<>();
    public static ArrayList<PAUser> afkMode = new ArrayList<>();
    private static PACore plugin = PACore.getInstance();
    @Getter private static ArrayList<PAUser> adminChatMode = new ArrayList<>();

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

/*    public static HashMap<UUID, UUID> getTeleportRequests() {
        return tp;
    }

    public static HashMap<UUID, UUID> getTeleportHereRequests() {
        return tph;
    }

    public static void addTeleportRequest(UUID u1, UUID u2) {
        tp.put(u1, u2);
    }

    public static void addTeleportHereRequest(UUID u1, UUID u2) {
        tph.put(u1, u2);
    }

    public static void removeTeleportRequest(UUID u) {
        tp.remove(u);
    }

    public static void removeTeleportHereRequest(UUID u) {
        tph.remove(u);
    }*/


}
