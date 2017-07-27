package es.projectalpha.pa.rage.cmd;

import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.rage.Files.Files;
import es.projectalpha.pa.rage.RageGames;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Arrays;

public class PointSetCMD extends PACmd {

    public PointSetCMD() {
        super("pointset", Grupo.Admin, Arrays.asList("ponerpunto", "ps"));
    }

    private RageGames plugin;
    int p;

    @Override
    public void run(PAUser user, String label, String[] args) {
        if (args.length == 0) {
            p = Integer.parseInt(plugin.getConfig().getString("Rage.puntos")) + 1;
            plugin.getConfig().set("Rage.puntos", p);
            plugin.getConfig().set("Rage.spawns." + p, Utils.locationToString(user.getLoc()));
            user.sendMessage("&aPunto puesto satisfactoriamente " + "&6" + p);
        }
    }
}
