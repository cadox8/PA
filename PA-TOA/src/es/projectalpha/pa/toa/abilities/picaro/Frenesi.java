package es.projectalpha.pa.toa.abilities.picaro;

import es.projectalpha.pa.toa.abilities.Ability;
import es.projectalpha.pa.toa.api.TOAUser;
import es.projectalpha.pa.toa.races.Race;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Frenesi extends Ability {

    public Frenesi() {
        super("Frenesi", 8, Race.RaceType.PICARO);
    }

    public void play(TOAUser u) {
        if (!canUse(u)) return;
        if (isInCooldown(u, getName())) return;

        u.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 100, 1, true, false));
        u.getPlayer().setWalkSpeed(0.5f);
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> Race.parseRace(getRace().getId()).addEffects(u), 100);
        cool.setOnCooldown(getName());
    }
}
