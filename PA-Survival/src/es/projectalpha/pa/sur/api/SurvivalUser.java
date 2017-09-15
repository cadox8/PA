package es.projectalpha.pa.sur.api;

import es.projectalpha.pa.core.PACore;
import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.utils.ScoreboardUtil;
import es.projectalpha.pa.sur.PASurvival;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;

public class SurvivalUser extends PAUser {

    private PASurvival plugin = PASurvival.getInstance();

    @Getter @Setter private boolean score = true;

    public SurvivalUser(String name) {
        super(name);
    }

    public void setInfo() {
        DecimalFormat df = new DecimalFormat("#.#");
        ScoreboardUtil board = new ScoreboardUtil(PAData.SURVIVAL.getOldPrefix(), "sur");
        new BukkitRunnable() {
            @Override
            public void run() {
                if (getPlayer() == null || !score) cancel();
                board.setName(PAData.SURVIVAL.getOldPrefix());
                board.text(6, "§aDinero: §e" + Double.valueOf(df.format(plugin.getVault().getBalance(getOfflinePlayer()))));
                board.text(5, "§e ");
                board.text(4, "§aMundo: §e" + getLoc().getWorld().getName());
                board.text(3, "§aCoordenadas:");
                board.text(2, "§dX: §e" + Double.valueOf(df.format(getLoc().getX())) + " §dY: §e" + Double.valueOf(df.format(getLoc().getY())) + " §dZ: §e" + Double.valueOf(df.format(getLoc().getZ())));
                board.text(1, "§e ");
                board.text(0, PACore.getOLD_IP());
                if (getPlayer() != null && score) board.build(getPlayer());
            }
        }.runTaskTimer(plugin, 0, 1);
    }
}
