package es.projectalpha.pa.core.utils;

import es.projectalpha.pa.core.PACore;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class Log {

    private static PACore plugin = PACore.getInstance();

    public static void log(Level level, String msg) {
        plugin.getServer().getConsoleSender().sendMessage(Utils.colorize(level.getPrefix() + " &" + level.getPrefix() + msg));
    }

    public static void debugLog(String msg) {
        if (!plugin.isDebug()) return;
        log(Level.DEBUG, msg);
    }

    @Getter
    @AllArgsConstructor
    public enum Level {

        INFO("[INFO]", 'r'),
        WARNING("[&cAVISO&r]", 'c'),
        SEVERE("[&4IMPORTANTE&r]", '4'),
        DEBUG("[&3DEBUG&r]", '3');

        private String prefix;
        private char color;
    }
}
