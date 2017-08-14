package es.projectalpha.pa.core.utils;

import es.projectalpha.pa.core.api.PAData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.ChatColor;

/**
 * Clase "comodín" para contener todos los mensajes de los plugins.
 */

@AllArgsConstructor
public enum Messages {
    UKNOWN("&cERROR DESCONOCIDO &kJKNBDHABSNDNAKSDASASJDBHASD"),
    JOIN("&7El jugador &6%player% &7se ha unido al servidor"),
    LEFT("&7El jugador &6%player% &7ha salido del juego"),
    AFK("&6%player% &7está AFK"),
    NO_AFK("&6%player% &7ya no está AFK"),
    NO_PERMS("&cNo tienes permiso para este comando"),
    INFO("&cEste comando no existe, para ver tus comandos, usa &3/ayuda"),
    NEED_ARGS("&c¡Faltan argumentos!"),
    BUFF_ARGS("&c¡Demasidos argumentos!"),
    SEND("&3Serás enviado al lobby en %t% segundos"),
    TOAHOME("&3Asignada casa &c%home&");


    @Getter
    private String message;

    public static String getMessage(Messages m, PAData pl) {
        return Utils.colorize(pl.getPrefix() + ChatColor.RESET + m.getMessage());
    }

    public static String getMessage(Messages m, PAData pl, String object, String arg) {
        return Utils.colorize(pl.getPrefix() + ChatColor.RESET + m.getMessage()).replace(object, arg);
    }

    public static String getMessage(Messages m, PAData pl, String[] objects, String[] args) {
        String base = pl.getPrefix() + ChatColor.RESET + m.getMessage();
        int id = 0;

        for (String o : objects) {
            base.replace(o, args[id]);
            id++;
        }
        return Utils.colorize(base);
    }
}
