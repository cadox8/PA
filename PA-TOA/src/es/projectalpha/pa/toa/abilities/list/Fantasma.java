package es.projectalpha.pa.toa.abilities.list;

import es.projectalpha.pa.toa.abilities.Ability;
import es.projectalpha.pa.toa.api.TOAUser;
import es.projectalpha.pa.toa.races.Race;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Fantasma extends Ability {

    public Fantasma() {
        super("Fantasma", Race.RaceType.PICARO);
    }

    public void play(TOAUser u) {
        if (!canUse(u)) return;

        u.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100, 0, true, false));
        u.getPlayer().setWalkSpeed(0.4f);
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> Race.parseRace(getRace().getId()).addEffects(u), 100);
    }
}
