package es.projectalpha.pa.sur.cmd;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.core.utils.Messages;
import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.sur.PASurvival;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class MineCMD extends PACmd {

    private PASurvival plugin = PASurvival.getInstance();

    public MineCMD() {
        super("minear", Grupo.Mod);
    }

    public void run(PAUser u, String label, String... args) {
        if (args.length > 1) {
            u.sendMessage(Messages.getMessage(Messages.BUFF_ARGS, PAData.SURVIVAL));
            return;
        }
        if (args.length < 1) {
            u.sendMessage(Messages.getMessage(Messages.NEED_ARGS, PAData.SURVIVAL));
            return;
        }
        if (plugin.getMineTask().isWorking()) {
            u.sendMessage(PAData.SURVIVAL.getPrefix() + "&cYa hay un evento en ejecución");
            return;
        }
        World w = u.getWorld();
        if (!w.getName().toLowerCase().equalsIgnoreCase("eventos")) {
            u.sendMessage(PAData.SURVIVAL.getPrefix() + "&cDebes estar en el mundo eventos");
            return;
        }
        int time = Utils.isInt(args[0]) ? Integer.valueOf(args[0]) : 10;
        plugin.getMineTask().setTime(time);

        w.getEntities().stream().filter(e -> e instanceof Player).forEach(p -> plugin.getMineTask().getBlocks().put(PASurvival.getPlayer((Player) p), 0));
        plugin.getMineTask().runTaskTimer(plugin, 0, 20);
        plugin.getMineTask().setWorking(true);
    }
}
