package es.projectalpha.pa.toa.abilities.archer;

import es.projectalpha.pa.core.utils.Cooldown;
import es.projectalpha.pa.toa.abilities.Ability;
import es.projectalpha.pa.toa.api.TOAUser;
import es.projectalpha.pa.toa.races.Race;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Guerrillero extends Ability {

    public Guerrillero() {
        super("Guerrillero", 18, Race.RaceType.ARCHER);
    }

    public void play(TOAUser u) {
        if (!canUse(u)) return;
        if (isInCooldown(u, getName())) return;

        u.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 200, 0, true, false));
        u.getPlayer().setWalkSpeed(0.4f);
        plugin.getServer().getScheduler().runTaskLater(plugin, ()-> u.getPlayer().setWalkSpeed(0.2f), 200);
        new Cooldown(getCooldown()).setOnCooldown(getName());
    }
}
