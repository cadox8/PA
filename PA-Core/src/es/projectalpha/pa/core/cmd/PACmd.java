package es.projectalpha.pa.core.cmd;

import es.projectalpha.pa.core.PACore;
import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAServer;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class PACmd {

    protected static transient PACore plugin = PACore.getInstance();
    protected static transient PAServer server = new PAServer();
    protected static transient Utils utils = PACore.getInstance().getUtils();
    @Getter
    private final transient String name;
    @Getter
    private final transient List<String> aliases;
    @Getter
    private transient Grupo group = Grupo.Usuario;

    public PACmd(final String name, final Grupo grupo, final List<String> aliases) {
        this.name = name.toLowerCase();
        this.group = grupo;
        this.aliases = aliases;
    }

    public PACmd(final String name, final Grupo grupo, final String... aliases) {
        this(name, grupo, Arrays.asList(aliases));
    }

    public PACmd(final String name, final Grupo grupo) {
        this(name, grupo, "");
    }

    public void run(ConsoleCommandSender sender, String label, String[] args) {
        run((CommandSender) sender, label, args);
    }

    public void run(PAUser user, String label, String[] args) {
        run(user.getPlayer(), label, args);
    }

    public void run(CommandSender sender, String label, String[] args) {
        sender.sendMessage(Utils.colorize("&cEste comando no está funcional para este sender"));
    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args, String curs, Integer curn) {
        return new ArrayList<>();
    }

    public String formatedCMD(String... args) {
        return "&e/" + Utils.colorize(Utils.buildString(args));
    }

    public void userNotOnline(PAUser user) {
        user.sendMessage(PAData.CORE.getPrefix() + "&cEL jugador debe estar conectado");
    }

    @Getter
    @AllArgsConstructor
    public enum Grupo {
        Usuario(0),
        VIP(1),
        ORIGIN(2),
        YT(3),
        Builder(4),
        Mod(5),
        Admin(6),
        DEV(7);

        private final int rank;

        public static char groupColor(Grupo grupo) {
            switch (grupo) {
                case DEV:
                    return 'd';
                case Admin:
                    return 'c';
                case Mod:
                    return '3';
                case Builder:
                    return '6';
                case YT:
                    return 'a';
                case ORIGIN:
                    return '2';
                case VIP:
                    return 'e';
                default:
                    return '7';
            }
        }
    }
}
