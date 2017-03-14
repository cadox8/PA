package es.projectalpha.pa.core.cmd;

import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.utils.Utils;
import org.bukkit.command.ConsoleCommandSender;

import java.util.Arrays;

public class BroadcastCMD extends PACmd {

    public BroadcastCMD() {
        super("broadcast", Grupo.Mod, Arrays.asList("bcast", "bc", "decir"));
    }

    @Override
    public void run(PAUser user, String lbl, String[] args) {
        String msg = Utils.buildString(args);
        Utils.broadcastMsg(msg);
    }

    @Override
    public void run(ConsoleCommandSender sender, String lbl, String[] args) {
        String msg = Utils.buildString(args);
        Utils.broadcastMsg(msg);
    }
}
