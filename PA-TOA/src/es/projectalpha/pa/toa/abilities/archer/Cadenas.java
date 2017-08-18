package es.projectalpha.pa.toa.abilities.archer;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.utils.Cooldown;
import es.projectalpha.pa.toa.abilities.Ability;
import es.projectalpha.pa.toa.api.TOAUser;
import es.projectalpha.pa.toa.races.Race;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Cadenas extends Ability {

    public Cadenas() {
        super("Cadenas", 9, Race.RaceType.ARCHER);
    }

    public void play(TOAUser u) {
        if (!canUse(u)) return;
        if (isInCooldown(u, getName())) return;

        if (r.nextInt(10) + 1 < 8) {
            u.sendMessage(PAData.TOA.getPrefix() + "&cTu habilidad ha fallado");
            return;
        }

        u.getWorld().getNearbyEntities(u.getLoc(), 5, 3, 5).stream().filter(e -> !e.getType().equals(EntityType.PLAYER)).forEach(e -> {
            if (e instanceof Monster) ((Monster) e).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 2, true, false));
        });
        new Cooldown(getCooldown()).setOnCooldown(getName());
    }
}
