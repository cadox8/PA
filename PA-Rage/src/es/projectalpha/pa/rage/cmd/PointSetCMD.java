package es.projectalpha.pa.rage.cmd;

import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.rage.files.Files;


import java.util.Arrays;

public class PointSetCMD extends PACmd {

    public PointSetCMD() {
        super("pointset", Grupo.Admin, Arrays.asList("ponerpunto", "ps"));
    }

    @Override
    public void run(PAUser user, String label, String[] args) {
        if (args.length == 0) {
            int p = Files.config.getInt("puntos");
            p++;
            Files.config.set("puntos", p);
            Files.config.set("spawns." + p, Utils.locationToString(user.getLoc()));
            user.sendMessage("&aPunto puesto satisfactoriamente " + "&6" + p);
            Files.saveFiles();
        }
    }
}
