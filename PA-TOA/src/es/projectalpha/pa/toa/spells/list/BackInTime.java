package es.projectalpha.pa.toa.spells.list;

import es.projectalpha.pa.toa.api.TOAUser;
import es.projectalpha.pa.toa.spells.Spell;
import org.bukkit.GameMode;
import org.bukkit.Location;


public class BackInTime extends Spell {

    private Location l;

    public BackInTime(int difficulty) {
        super("Distorsión", 100, difficulty);
    }

    public void spell(TOAUser u) {
        l = u.getLoc();

        u.getPlayer().setGameMode(GameMode.SPECTATOR);

        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            u.teleport(l);
            u.getPlayer().setGameMode(GameMode.ADVENTURE);
        }, 100);
    }
}
