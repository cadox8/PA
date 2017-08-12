package es.projectalpha.pa.sur.events;

import es.projectalpha.pa.sur.PASurvival;
import es.projectalpha.pa.sur.api.SurvivalUser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerEvents implements Listener{

    @EventHandler
    public void onJoin(PlayerJoinEvent e){

        Player p = e.getPlayer();
        SurvivalUser u = PASurvival.getPlayer(p);



    }


}
