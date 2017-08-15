package es.projectalpha.pa.sur.cmd;

import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.sur.files.Files;
import es.projectalpha.pa.sur.manager.Balance;
import net.milkbowl.vault.economy.Economy;

import java.util.Arrays;

public class MoneyCMD extends PACmd {

    public MoneyCMD() {
        super("pvp", PACmd.Grupo.Admin, Arrays.asList("balance", "dinero"));
    }
    private Files files = new Files();
    private Balance balance;
    private Economy eco;

    @Override
    public void run(PAUser user, String label, String[] args) {
        if (args.length == 0) {
            user.sendMessage("&cDinero: &6" + eco.getBalance(user.getOfflinePlayer()));
        }
        if (args.length == 1) {

        }
    }

}
