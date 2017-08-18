package es.projectalpha.pa.sur.cmd;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.core.utils.Messages;
import es.projectalpha.pa.sur.PASurvival;
import es.projectalpha.pa.sur.api.SurvivalUser;
import es.projectalpha.pa.sur.files.Files;
import es.projectalpha.pa.sur.manager.Balance;

public class PayCMD extends PACmd {

    private PASurvival plugin = PASurvival.getInstance();

    public PayCMD() {
        super("pvp", Grupo.Usuario);
    }

    private Files files = plugin.getFiles();
    private Balance balance = new Balance();

    @Override
    public void run(PAUser user, String label, String[] args) {
        switch (args.length) {
            case 0:
            case 1:
                user.sendMessage(PAData.SURVIVAL.getPrefix() + "&4Necesitas definir el jugador y el dinero que vas a pagar. /pay <jugador> <cantidad>");
                break;
            case 2:
                SurvivalUser u = PASurvival.getPlayer(plugin.getServer().getPlayerExact(args[0]));
                if(!u.isOnline() || u == null){
                    user.sendMessage(PAData.SURVIVAL.getPrefix() + "&4El jugador no existe o está desconectado");
                    return;
                }
                balance.addBalace(u, Double.parseDouble(args[1]));
                balance.removeBalance(PASurvival.getPlayer(user.getPlayer()), Double.parseDouble(args[1]));
                balance.saveBalance(u);
                user.sendMessage(PAData.SURVIVAL.getPrefix() + "&Has pagado a " + u.getName() + " " + args[1] + "$");
                break;
            default:
                user.sendMessage(Messages.getMessage(Messages.BUFF_ARGS, PAData.SURVIVAL));
                break;
        }
    }
}
