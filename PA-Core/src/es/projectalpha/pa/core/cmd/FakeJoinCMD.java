package es.projectalpha.pa.core.cmd;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.utils.Messages;
import es.projectalpha.pa.core.utils.Utils;

public class FakeJoinCMD extends PACmd {

    public FakeJoinCMD() {
        super("fakejoin", Grupo.Admin, "fj");
    }

    public void run(PAUser user, String label, String[] args) {
        if (args.length == 0) {
            user.sendMessage(Messages.getMessage(Messages.NEED_ARGS, PAData.CORE));
            return;
        }
        if (args.length == 1) {
            Utils.broadcastMsg(Messages.getMessage(Messages.JOIN, PAData.CORE, "%player%", args[0]));
            return;
        }
        if (args.length >= 2) user.sendMessage(Messages.getMessage(Messages.BUFF_ARGS, PAData.CORE));
    }
}
