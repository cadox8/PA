package es.projectalpha.pa.lobby.cmd;

import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.lobby.PALobby;

public class SetSpawnCMD extends PACmd {

    public SetSpawnCMD() {
        super("setspawn", Grupo.Admin, "ss", "spawnset");
    }

    public void run(PAUser user, String label, String... args) {
        if (args.length >= 0) {
            PALobby.getInstance().getConfig().set("spawn", Utils.locationToString(user.getLoc()));
            PALobby.getInstance().saveConfig();
            user.sendMessage("Illo, punto ponio.");
        }
    }

}
