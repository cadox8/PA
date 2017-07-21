package es.projectalpha.pa.core.events;

import es.projectalpha.pa.core.PACore;
import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAServer;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.core.utils.Messages;
import es.projectalpha.pa.core.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

public class PlayerListener implements Listener {

    private final PACore plugin;

    public PlayerListener(PACore instance) {
        plugin = instance;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onLogin(PlayerLoginEvent e) {
        Player p = e.getPlayer();

        if (e.getResult() == PlayerLoginEvent.Result.ALLOWED) plugin.getMysql().setupTable(p);
    }


    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        PAUser u = PAServer.getUser(p);

        u.getUserData().setLastConnect(System.currentTimeMillis());
        u.getUserData().setTimeJoin(System.currentTimeMillis());
        u.getUserData().setIp(u.getPlayer().getAddress());
        u.save();
        u.getPlayer().setFlySpeed(0.2f);
        u.getPlayer().setWalkSpeed(0.2f);

        e.setJoinMessage(Messages.getMessage(Messages.JOIN, PAData.CORE, "%player%", e.getPlayer().getName()));
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerQuit(PlayerQuitEvent e) {
        PAUser u = PAServer.getUser(e.getPlayer());

        e.setQuitMessage(Messages.getMessage(Messages.LEFT, PAData.CORE, "%player%", e.getPlayer().getName()));
        u.save();
        if (PAServer.users.contains(u)) PAServer.users.remove(u);
    }

    /*
     * Capturar eventos del adminchat antes de nada
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        PAUser user = PAServer.getUser(e.getPlayer());

        //AdminChat
        if (PAServer.getAdminChatMode().contains(user)) {
            Utils.sendAdminMsg(user, e.getMessage());
            e.setCancelled(true);
        }

        //Format
        String tag = user.getUserData().getGrupo() == PACmd.Grupo.Usuario ? "&7" : "[&" + PACmd.Grupo.groupColor(user.getUserData().getGrupo()) + user.getUserData().getGrupo().toString().toUpperCase() + "&r]";
        String msg = user.isOnRank(PACmd.Grupo.ORIGIN) ? Utils.colorize(e.getMessage()) : e.getMessage();
        e.setFormat(Utils.colorize(tag + " " + user.getName() + ": ") + msg);
    }

    /*
     * Prevenir ver plugins
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommand(PlayerCommandPreprocessEvent e) {
        PAUser p = PAServer.getUser(e.getPlayer());


        if (e.getMessage().startsWith("/?") || e.getMessage().startsWith("/bukkit:") || e.getMessage().startsWith("/pl") || e.getMessage().startsWith("/plugins") || e.getMessage().startsWith("/minecraft:")) {
            if (!p.isOnRank(PACmd.Grupo.Builder)) return;
            p.sendMessage(PAData.CORE.getPrefix() + "&cLos plugins de este servidor ha sido creados por los desarrolladores del mismo, es por eso por lo que no tenemos" +
                    "ningún problema en decirlos: &6PA-Core. &cAhora, te invito a que los crees tu mismo, puesto que el código " +
                    "de los plugins sólo lo tenemos nosotros :D");
            e.setCancelled(true);
        }
    }
}
