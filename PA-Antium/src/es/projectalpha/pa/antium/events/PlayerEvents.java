package es.projectalpha.pa.antium.events;

import es.projectalpha.pa.antium.PAAntium;
import es.projectalpha.pa.core.api.PAUser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

public class PlayerEvents implements Listener {

    private PAAntium plugin;

    public PlayerEvents(PAAntium instance){
        this.plugin = instance;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        PAUser u = PAAntium.getUser(e.getPlayer());

        if (plugin.getMysql().isRegistered(u)) {
            u.sendMessagePrefix("&3Por favor, escribe &c/login <contraseña> &3para acceder al servidor");
            return;
        }
        u.sendMessagePrefix("&3Por favor, escribe &c/register <contraseña> <contraseña> &3para acceder al servidor");
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        PAUser u = PAAntium.getUser(e.getPlayer());

        if (!plugin.getPassManager().getLogged().contains(u)) plugin.getPassManager().removeLogged(u);
    }


    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        PAUser u = PAAntium.getUser(e.getPlayer());

        if (!plugin.getPassManager().getLogged().contains(u)) e.setCancelled(true);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        PAUser u = PAAntium.getUser(e.getPlayer());

        if (!plugin.getPassManager().getLogged().contains(u)) e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onIteract(PlayerInteractEvent e){
        PAUser u = PAAntium.getUser(e.getPlayer());

        if (!plugin.getPassManager().getLogged().contains(u)) e.setCancelled(true);
    }
}
