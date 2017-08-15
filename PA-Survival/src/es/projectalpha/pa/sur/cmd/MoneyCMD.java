package es.projectalpha.pa.sur.cmd;

import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.sur.PASurvival;
import es.projectalpha.pa.sur.files.Files;
import es.projectalpha.pa.sur.manager.Balance;
import net.milkbowl.vault.economy.Economy;

public class MoneyCMD extends PACmd {

    private PASurvival plugin = PASurvival.getInstance();

    public MoneyCMD() {
        super("dinero", PACmd.Grupo.Admin, "balance", "money");
    }

    private Files files = plugin.getFiles();
    private Balance balance = new Balance();
    private Economy eco = plugin.getVault();

    @Override
    public void run(PAUser user, String label, String[] args) {
        if (args.length == 0) {
            user.sendMessage("&cDinero: &6" + eco.getBalance(user.getOfflinePlayer()));
        }
        if (args.length == 1) {

        }
    }

}
