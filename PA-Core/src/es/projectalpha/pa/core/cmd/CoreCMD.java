package es.projectalpha.pa.core.cmd;

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
                case "debug":
                    toggleDebug(user);
                    break;
                case "reload":
                    reloadConfig(user);
                    break;
                case "mantenimiento":
                    toggleMaintenance(user);
                    break;
                case "pruebas":
                    togglePruebas(user);
                    break;
            }
            return;
        }
        def(user);
    }

    private void def(PAUser user) {
        user.sendMessagePrefix("&aFunciona con PACore " + "&7v" + plugin.getDescription().getVersion());
    }

    private void toggleDebug(PAUser user) {
        plugin.getConfig().set("debug", !plugin.isDebug());
        plugin.saveConfig();

        String debug = (plugin.isDebug()) ? "&aActivado" : "&cDesactivado";
        user.sendMessage("&eHas cambiado el modo debug del PACore a: " + debug);
    }

    private void reloadConfig(PAUser user) {
        plugin.reloadConfig();
        user.sendMessage("&eConfiguración recargada");
    }

    private void toggleMaintenance(PAUser user){
        plugin.getConfig().set("maintenance", !plugin.isMaintenance());
        plugin.saveConfig();

        String main = (plugin.isMaintenance()) ? "&aActivado" : "&cDesactivado";
        user.sendMessage("&eHas cambiado el modo Mantenimiento del PACore a: " + main);
    }

    private void togglePruebas(PAUser user){
        plugin.getConfig().set("pruebas", !plugin.isPruebas());
        plugin.saveConfig();

        String pruebas = (plugin.isPruebas()) ? "&aActivado" : "&cDesactivado";
        user.sendMessage("&eHas cambiado el modo Mantenimiento del PACore a: " + pruebas);
    }
}
