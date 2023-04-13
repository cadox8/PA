package es.projectalpha.pa.lobby.cosmetics.list;

import es.projectalpha.pa.core.api.PAServer;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.utils.ColorParser;
import es.projectalpha.pa.core.utils.Sounds;
import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.lobby.cosmetics.Cosmetic;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Sheep;
import org.bukkit.scheduler.BukkitTask;
import org.inventivetalent.particle.ParticleEffect;

public class ExplosiveSheep extends Cosmetic {

    public ExplosiveSheep() {
        super("&dOveja Fiestera", Material.WHITE_WOOL, 15);
    }

    private int count = 10;
    private BukkitTask bt;

    @Override
    public void play(PAUser u) {
        if (isInCooldown(u, getName())) return;

        final Sheep o = (Sheep) spawnEntity(u.getLoc(), EntityType.SHEEP);
        o.setAdult();
        o.setColor(DyeColor.values()[r.nextInt(DyeColor.values().length)]);
        o.setCustomName(Utils.colorize("&dOveja Fiestera"));
        o.setCustomNameVisible(true);
        o.setNoDamageTicks(Integer.MAX_VALUE);
        o.setVelocity(u.getPlayer().getLocation().getDirection().multiply(2));

        PAServer.users.forEach(h -> h.sendSound(Sounds.SHEEP_IDLE));

        bt = plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
            ColorParser dc = ColorParser.values()[r.nextInt(ColorParser.values().length)];
            o.setColor(dc.getDyeColor());
            o.setCustomName(Utils.colorize(dc.getChatColor() + "Oveja Fiestera"));

            if (count <= 0) {
                PAServer.users.forEach(h -> h.sendSound(Sounds.EXPLODE));
                ParticleEffect.EXPLOSION_HUGE.send(plugin.getServer().getOnlinePlayers(), o.getLocation(), 0, 0, 0, 1, 20, 50);
                o.remove();
                bt.cancel();
                return;
            }
            count--;
        }, 0, 20);
    }
}
