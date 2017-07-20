package es.projectalpha.pa.toa.events;

import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.toa.TOA;
import es.projectalpha.pa.toa.api.TOAUser;
import es.projectalpha.pa.toa.manager.Experience;
import es.projectalpha.pa.toa.mobs.Mob;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.Collections;
import java.util.HashMap;

public class GameEvents implements Listener {

    private TOA plugin;

    public GameEvents(TOA instance) {
        this.plugin = instance;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        TOAUser u;
        if (e.getEntity() instanceof Monster && e.getEntity().getKiller() instanceof Player) {
            u = TOA.getPlayer(e.getEntity().getKiller());
            String name = e.getEntity().getCustomName().split(" ")[1];
            int level = Utils.isInt(name) ? Integer.parseInt(name) : 0;

            new Experience(u).addExp(Mob.getXP(level));

            if (plugin.getSpawnTask().getCount() == 0) return;
            plugin.getSpawnTask().setCount(plugin.getSpawnTask().getCount() - 1);
        }
    }

    @EventHandler
    public void onSpawn(EntitySpawnEvent e) {
        if (e.getEntity() instanceof Animals) e.setCancelled(true);
    }
}
