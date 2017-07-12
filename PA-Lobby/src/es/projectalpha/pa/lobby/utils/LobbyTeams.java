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
    public static Team tMod;
    public static Team tAdmin;
    public static Team tDev;

    public static void initTeams() {
        board = Bukkit.getScoreboardManager().getMainScoreboard();

        tAdmin = board.getTeam("2Core_Admin") == null ? board.registerNewTeam("2Core_Admin") : board.getTeam("2Core_Admin");
        tDev = board.getTeam("3Core_Dev") == null ? board.registerNewTeam("3Core_Dev") : board.getTeam("3Core_Dev");
        tMod = board.getTeam("4Core_Mod") == null ? board.registerNewTeam("4Core_Mod") : board.getTeam("4Core_Mod");
        tOrigin = board.getTeam("5Core_Origin") == null ? board.registerNewTeam("5Core_Origin") : board.getTeam("5Core_Origin");
        tVip = board.getTeam("7Core_Vip") == null ? board.registerNewTeam("7Core_Vip") : board.getTeam("7Core_Vip");
        tUsuario = board.getTeam("8Core_User") == null ? board.registerNewTeam("8Core_User") : board.getTeam("8Core_User");

        tAdmin.setPrefix("§c[ADM] ");
        tDev.setPrefix("§5[DEV] ");
        tMod.setPrefix("§b[MOD] ");
        tOrigin.setPrefix("§6[OG] ");
        tVip.setPrefix("§a[VIP] ");
        tUsuario.setPrefix("§e");

        tAdmin.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
        tDev.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
        tMod.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
        tOrigin.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
        tVip.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
        tUsuario.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
    }

    //Add on Join
    public static void setScoreboardTeam(PAUser user) {
        removeScoreboardTeam(user);
        switch(user.getUserData().getGrupo()) {
            case Admin:
                tAdmin.addEntry(user.getName()); break;
            case DEV:
                tDev.addEntry(user.getName()); break;
            case Mod:
                tMod.addEntry(user.getName()); break;
            case ORIGIN:
                tOrigin.addEntry(user.getName()); break;
            case VIP:
                tVip.addEntry(user.getName()); break;
            default:
                tUsuario.addEntry(user.getName()); break;
        }
    }

    public static void removeScoreboardTeam(PAUser user) {
        Team t = board.getEntryTeam(user.getName());
        if (t != null) t.removeEntry(user.getName());
    }
}
