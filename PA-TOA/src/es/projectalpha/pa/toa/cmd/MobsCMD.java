package es.projectalpha.pa.toa.cmd;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.core.utils.Messages;
import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.toa.TOA;

public class MobsCMD extends PACmd {

    public MobsCMD() {
        super("mobs", Grupo.Admin);
    }

    public void run(PAUser u, String label, String... args) {
        if (args.length < 2 || args.length >= 3) {
            u.sendMessage(Messages.getMessage(Messages.BUFF_ARGS, PAData.TOA));
            return;
        }
        int id = TOA.getInstance().getConfig().getInt("id");

        TOA.getInstance().getConfig().set("Mobs." + id + ".loc", Utils.locationToString(u.getLoc()));
        TOA.getInstance().getConfig().set("Mobs." + id + ".type", Utils.isInt(args[0]) ? Integer.parseInt(args[0]) : 1);
        TOA.getInstance().getConfig().set("Mobs." + id + ".level", Utils.isInt(args[1]) ? Integer.parseInt(args[1]) : 1);

        id++;
        TOA.getInstance().getConfig().set("id", id);
        TOA.getInstance().saveConfig();

        u.sendMessage("&cPunto de spawn guardado &7(&d" + id-- + "&7)");
    }
}
