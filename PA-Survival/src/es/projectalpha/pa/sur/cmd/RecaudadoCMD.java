package es.projectalpha.pa.sur.cmd;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.core.utils.Log;
import es.projectalpha.pa.core.utils.Messages;
import es.projectalpha.pa.sur.PASurvival;
import es.projectalpha.pa.sur.files.Files;
import es.projectalpha.pa.sur.manager.Balance;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Array;
import java.util.Arrays;

public class RecaudadoCMD extends PACmd {

    private PASurvival plugin = PASurvival.getInstance();

    public RecaudadoCMD() {
        super("recaudado", Grupo.Usuario, Arrays.asList("impuestos", "rec", "imp"));

    }

    private Files files = plugin.getFiles();
    private Balance balance = new Balance();
    private Economy eco = plugin.getVault();

    @Override
    public void run(PAUser user, String label, String[] args) {
        if (args.length == 0) {
            user.sendMessage(PAData.SURVIVAL.getPrefix() + "&cDinero recaudado: &6" + files.getUser().getInt("recaudado"));
        }
        if (args.length >= 1) {
            user.sendMessage(Messages.getMessage(Messages.BUFF_ARGS, PAData.SURVIVAL));
        }
    }

}
