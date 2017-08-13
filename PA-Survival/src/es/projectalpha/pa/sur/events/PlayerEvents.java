package es.projectalpha.pa.sur.events;

import es.projectalpha.pa.sur.Files.Files;
import es.projectalpha.pa.sur.PASurvival;
import es.projectalpha.pa.sur.api.SurvivalUser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerEvents implements Listener{

    private Files files = new Files();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        Player p = e.getPlayer();
        SurvivalUser u = PASurvival.getPlayer(p);

        if (!files.getUser().contains(p.getName())) {

            files.getUser().set("Users." + p.getName() + ".pvp", "on");
            files.getUser().set("Users." + p.getName() + ".money", "0");
            files.getUser().createSection("Users." + p.getName() + "homes");
            files.getUser().set("Users." + p.getName() + ".imprec","0");

        }
    }


}
