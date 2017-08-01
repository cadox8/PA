package es.projectalpha.pa.toa.abilities.list;

import es.projectalpha.pa.toa.abilities.Ability;
import es.projectalpha.pa.toa.api.TOAUser;
import es.projectalpha.pa.toa.races.Race;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Cadenas extends Ability {

    public Cadenas() {
        super("Cadenas", Race.RaceType.ARCHER);
    }

    public void play(TOAUser u) {
        if (!canUse(u)) return;

        u.getWorld().getNearbyEntities(u.getLoc(), 5, 3, 5).stream().filter(e -> !e.getType().equals(EntityType.PLAYER))
                .forEach(e -> {
                    if (e instanceof Monster) ((Monster) e).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 1, true, false));
                });
    }
}
