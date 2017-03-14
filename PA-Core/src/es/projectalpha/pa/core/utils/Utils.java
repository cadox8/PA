package es.projectalpha.pa.core.utils;

import es.projectalpha.pa.core.PACore;
import es.projectalpha.pa.core.api.PAServer;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import org.bukkit.*;

import java.util.ArrayList;

public class Utils {

    private static PACore plugin;

    public Utils(PACore instance) {
        plugin = instance;
    }

    public static String colorize(String s) {
        if (s == null) return "";
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static void sendAdminMsg(PAUser user, String msg){
        plugin.getServer().getOnlinePlayers().forEach(p -> {
            PAUser u = PAServer.getUser(p);
            if (u.isOnRank(PACmd.Grupo.BUILDER)) {
                u.sendMessage("&0[&2A&0] &3" + user.getName() + "&r: " + msg);
                u.sendSound(Sound.BLOCK_ANVIL_HIT);
            }
        });
    }

    public static void broadcastMsg(String msg) {
        plugin.getServer().getOnlinePlayers().forEach(p -> {
            PAUser u = PAServer.getUser(p);
            u.sendMessagePrefix(msg);
        });
    }

    public static String buildString(String[] args) {
        return buildString(args, 0);
    }

    public static String buildString(String[] args, int empiece) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = empiece; i < args.length; i++) {
            if (i > empiece) {
                stringBuilder.append(" ");
            }
            stringBuilder.append(args[i]);
        }
        return stringBuilder.toString();
    }

    public static boolean isInt(String number){
        try {
            Integer.parseInt(number);
        } catch(NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }

    public static String locationToString(Location loc) {
        return loc.getWorld().getName() + "%" + loc.getX() + "%" + loc.getY() + "%" + loc.getZ() + "%" + loc.getYaw() + "%" + loc.getPitch();
    }

    public static Location stringToLocation(String string) {
        if (string == null) return null;
        String[] s = string.split("%");
        Location loc = new Location(Bukkit.getWorld(s[0]), Double.parseDouble(s[1]),
                Double.parseDouble(s[2]), Double.parseDouble(s[3]), Float.parseFloat(s[4]), Float.parseFloat(s[5]));
        return loc;
    }

    public static Location centre(Location loc) {
        Location clon = loc.clone();
        return clon.add(0.5, 0, 0.5);
    }

    //Amount = Número de puntos del círculo, recomiendo 20 (?)
    public static ArrayList<Location> getCircle(Location center, double radius, int amount) {
        World world = center.getWorld();
        double increment = (2 * Math.PI) / amount;
        ArrayList<Location> locations = new ArrayList<>();

        for(int i = 0; i < amount; i++) {
            double angle = i * increment;
            double x = center.getX() + (radius * Math.cos(angle));
            double z = center.getZ() + (radius * Math.sin(angle));
            locations.add(new Location(world, x, center.getY(), z));
        }
        return locations;
    }

    public static World getWorld(String name){
        return plugin.getServer().getWorld(name);
    }
}
