package es.projectalpha.pa.toa.abilities.list;

import es.projectalpha.pa.toa.abilities.Ability;
import es.projectalpha.pa.toa.api.TOAUser;
import es.projectalpha.pa.toa.races.Race;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Guerrillero extends Ability {

    public Guerrillero() {
        super("Guerrillero", Race.RaceType.ARCHER);
    }

    public void play(TOAUser u) {
        if (!canUse(u)) return;

        u.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 200, 0, true, false));
        u.getPlayer().setWalkSpeed(0.3f);
        plugin.getServer().getScheduler().runTaskLater(plugin, ()-> u.getPlayer().setWalkSpeed(0.2f), 200);
    }
}
