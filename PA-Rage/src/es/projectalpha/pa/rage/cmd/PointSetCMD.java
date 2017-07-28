package es.projectalpha.pa.rage.cmd;

import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.rage.RageGames;

import java.util.Arrays;

public class PointSetCMD extends PACmd {

    public PointSetCMD() {
        super("pointset", Grupo.Admin, Arrays.asList("ponerpunto", "ps"));
    }

    private RageGames main = RageGames.getInstance();
    int p = 0;
    @Override
    public void run(PAUser user, String label, String[] args) {
        if (args.length == 0) {
            /*int p = this.plugin.getFiles().getConfig().getInt("puntos") + 1;
            this.plugin.getFiles().getConfig().set("puntos", p);
            this.plugin.getFiles().getConfig().set("spawns." + p, Utils.locationToString(user.getLoc()));
            this.plugin.getFiles().saveFiles();*/
            p++;
            main.getFiles().getConfig().set("puntos", p);
            main.getFiles().getConfig().set("spawns." + p, Utils.locationToString(user.getLoc()));
            user.sendMessage("&aPunto puesto satisfactoriamente " + "&6" + p);
            main.getFiles().saveFiles();
            System.out.println(main.getFiles().getConfig().getString("puntos" + " " + p));
        }
    }
}
