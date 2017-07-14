package es.projectalpha.pa.core.utils;

import es.projectalpha.pa.core.PACore;
import es.projectalpha.pa.core.api.PAData;
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
            if (u.isOnRank(PACmd.Grupo.Builder)) {
                u.sendMessage("&0[&2A&0] &3" + user.getName() + "&r: " + Utils.colorize(msg));
                u.sendSound(Sound.ANVIL_BREAK);
            }
        });
    }

    public static void broadcastMsg(String msg) {
        plugin.getServer().getOnlinePlayers().forEach(p -> PAServer.getUser(p).sendMessage(PAData.CORE.getPrefix() + msg));
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
        if (loc == null) return "";
        return loc.getWorld().getName() + "%" + loc.getX() + "%" + loc.getY() + "%" + loc.getZ() + "%" + loc.getYaw() + "%" + loc.getPitch();
    }

    public static Location stringToLocation(String string) {
        if (string == null) return null;
        String[] s = string.split("%");
        return new Location(Bukkit.getWorld(s[0]), Double.parseDouble(s[1]),
                Double.parseDouble(s[2]), Double.parseDouble(s[3]), Float.parseFloat(s[4]), Float.parseFloat(s[5]));
    }

    public static Location cuboidToLocation(String string, int args){
        if (args > 1 || string == null) return null;
        return stringToLocation(string.split(";")[args]);
    }

    public static Location centre(Location loc) {
        Location clon = loc.clone();
        return clon.add(0.5, 0, 0.5);
    }

    //Amount = Número de puntos del círculo
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
