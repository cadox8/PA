package es.projectalpha.pa.toa.events;

import es.projectalpha.pa.toa.TOA;
import es.projectalpha.pa.toa.manager.Experience;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class GameEvents implements Listener {

    private TOA plugin;

    public GameEvents(TOA instance) {
        this.plugin = instance;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if (e.getEntity() instanceof Monster) {
            if (plugin.getSpawnTask().getCount() == 0) return;
            plugin.getSpawnTask().setCount(plugin.getSpawnTask().getCount() - 1);
        }
    }
}
