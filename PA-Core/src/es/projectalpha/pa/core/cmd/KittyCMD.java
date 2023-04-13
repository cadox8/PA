package es.projectalpha.pa.core.cmd;

import es.projectalpha.pa.core.api.PAServer;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.utils.Sounds;
import es.projectalpha.pa.core.utils.Utils;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Tameable;
import org.inventivetalent.particle.ParticleEffect;

public class KittyCMD extends PACmd {

    public KittyCMD() {
        super("kitty", Grupo.DEV, "kittycannon", "kc");
    }

    public void run(PAUser user, String label, String... args) {
        final Ocelot o = (Ocelot) user.getLoc().getWorld().spawnEntity(user.getLoc(), EntityType.OCELOT);
        o.setCatType(Ocelot.Type.values()[r.nextInt(Ocelot.Type.values().length)]);
        //o.setSitting(true);
        o.setTrusting(true);
        o.setAge(r.nextInt(2));
        o.setCustomName(Utils.colorize("&dGateteeeeeee"));
        o.setCustomNameVisible(true);
        o.setNoDamageTicks(Integer.MAX_VALUE);
        o.setVelocity(user.getPlayer().getLocation().getDirection().multiply(2));

        PAServer.users.forEach(u -> u.sendSound(Sounds.CAT_MEOW));

        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            PAServer.users.forEach(u -> u.sendSound(Sounds.EXPLODE));
            ParticleEffect.EXPLOSION_HUGE.send(plugin.getServer().getOnlinePlayers(), o.getLocation(), 0, 0, 0, 1, 20, 50);
            o.remove();
        }, 60);
    }
}
