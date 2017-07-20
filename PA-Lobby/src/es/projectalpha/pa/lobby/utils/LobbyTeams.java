package es.projectalpha.pa.lobby.utils;

import es.projectalpha.pa.core.api.PAUser;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class LobbyTeams {

    public static Scoreboard board;
    public static Team tUsuario;
    public static Team tVip;
    public static Team tOrigin;
    public static Team tBuilder;
    public static Team tMod;
    public static Team tAdmin;
    public static Team tDev;
    private static Team t;

    public static void initTeams() {
        board = Bukkit.getScoreboardManager().getMainScoreboard();

        tAdmin = board.getTeam("1Core_Admin") == null ? board.registerNewTeam("1Core_Admin") : board.getTeam("1Core_Admin");
        tDev = board.getTeam("2Core_Dev") == null ? board.registerNewTeam("2Core_Dev") : board.getTeam("2Core_Dev");
        tMod = board.getTeam("3Core_Mod") == null ? board.registerNewTeam("3Core_Mod") : board.getTeam("3Core_Mod");
        tBuilder = board.getTeam("4Core_Builder") == null ? board.registerNewTeam("4Core_Builder") : board.getTeam("4Core_Builder");
        tOrigin = board.getTeam("6Core_Origin") == null ? board.registerNewTeam("6Core_Origin") : board.getTeam("6Core_Origin");
        tVip = board.getTeam("7Core_Vip") == null ? board.registerNewTeam("7Core_Vip") : board.getTeam("7Core_Vip");
        tUsuario = board.getTeam("8Core_User") == null ? board.registerNewTeam("8Core_User") : board.getTeam("8Core_User");

        tAdmin.setPrefix("§c[A] ");
        tDev.setPrefix("§d[D] ");
        tMod.setPrefix("§3[M] ");
        tBuilder.setPrefix("§6[B] ");
        tOrigin.setPrefix("§2[O] ");
        tVip.setPrefix("§e[V] ");
        tUsuario.setPrefix("§7");
    }

    public static void setScoreboardTeam(PAUser user) {
        removeScoreboardTeam(user);
        switch (user.getUserData().getGrupo()) {
            case Admin:
                tAdmin.addEntry(user.getName());
                break;
            case Mod:
                tMod.addEntry(user.getName());
                break;
            case DEV:
                tDev.addEntry(user.getName());
                break;
            case Builder:
                tBuilder.addEntry(user.getName());
                break;
            case ORIGIN:
                tOrigin.addEntry(user.getName());
                break;
            case VIP:
                tVip.addEntry(user.getName());
                break;
            default:
                tUsuario.addEntry(user.getName());
                break;
        }
    }

    public static void removeScoreboardTeam(PAUser user) {
        board.getTeams().forEach(team -> {
            if (team.getEntries().contains(user.getName())) t = team;
        });
        if (t != null) t.removeEntry(user.getName());
    }
}
