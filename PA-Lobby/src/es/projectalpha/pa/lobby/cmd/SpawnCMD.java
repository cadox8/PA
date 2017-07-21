package es.projectalpha.pa.lobby.cmd;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.core.utils.Messages;
import es.projectalpha.pa.lobby.utils.Helpers;

public class SpawnCMD extends PACmd {

    public SpawnCMD() {
        super("spawn", Grupo.Usuario);
    }

    public void run(PAUser user, String label, String[] args) {
        if (args.length == 0) {
            new Helpers(user).sendToSpawn();
            return;
        }
        user.sendMessage(Messages.getMessage(Messages.BUFF_ARGS, PAData.CORE));
    }
}
