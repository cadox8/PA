package es.projectalpha.pa.core.cmd;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAUser;

public class CoreCMD extends PACmd {

    public CoreCMD() {
        super("pacore", Grupo.Usuario, "core");
    }

    @Override
    public void run(PAUser user, String label, String[] args) {
        if (args.length == 1) {
            if (!user.isOnRank(Grupo.DEV)) {
                def(user);
                return;
            }

            switch (args[0].toLowerCase()) {
                case "reload":
                    reloadConfig(user);
                    break;
            }
            return;
        }
        def(user);
    }

    private void def(PAUser user) {
        user.sendMessage(PAData.CORE.getPrefix() + "&aFunciona con &cPACore " + "&7v" + plugin.getDescription().getVersion());
    }

    private void reloadConfig(PAUser user) {
        plugin.reloadConfig();
        user.sendMessage("&eConfiguración recargada");
    }
}
