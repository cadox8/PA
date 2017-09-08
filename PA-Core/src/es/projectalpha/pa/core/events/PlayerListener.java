package es.projectalpha.pa.core.events;

import es.projectalpha.pa.core.PACore;
import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAServer;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.core.utils.Messages;
import es.projectalpha.pa.core.utils.Utils;
import org.apache.commons.lang.WordUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.*;
import org.bukkit.permissions.PermissionAttachment;

public class PlayerListener implements Listener {

    private final PACore plugin;
    private boolean death;

    public PlayerListener(PACore instance) {
        plugin = instance;
        death = false;
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

        if(u.isOnRank(PACmd.Grupo.Mod)){
            PermissionAttachment attachment = p.addAttachment(plugin);
            plugin.getPerms().put(p, attachment);
            PermissionAttachment pperms = plugin.getPerms().get(p);
            pperms.setPermission("bm.*", true);
        }

        //u.sendHeaderAndFooter("&cProject&6Alpha", "");

        u.getUserData().setLastConnect(System.currentTimeMillis());
        u.getUserData().setTimeJoin(System.currentTimeMillis());
        u.getUserData().setIp(u.getPlayer().getAddress());
        u.save();
        u.getPlayer().setFlySpeed(0.2f);
        u.getPlayer().setWalkSpeed(0.2f);
        if (u.getUserData().getNickname() != null) {
            u.getPlayer().setDisplayName(u.getUserData().getNickname());
        } else {
            u.getPlayer().setDisplayName(u.getName());
        }

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
        PAUser u = PAServer.getUser(e.getPlayer());

        //AdminChat
        if (PAServer.getAdminChatMode().contains(u)) {
            Utils.sendAdminMsg(u.getName(), e.getMessage());
            e.setCancelled(true);
        }

        //Format
        String tag = "[&" + PACmd.Grupo.groupColor(u.getUserData().getGrupo()) + WordUtils.capitalizeFully(u.getUserData().getGrupo().toString().toLowerCase()) + "&r] &" + PACmd.Grupo.groupColor(u.getUserData().getGrupo()) + u.getName() + "&r: ";
        if (u.isOnRank(PACmd.Grupo.ORIGIN)) e.setMessage(Utils.colorize(e.getMessage()));

        e.setFormat(Utils.colorize(tag) + e.getMessage().replace("%", ""));
    }

    /*
     * Prevenir ver plugins
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommand(PlayerCommandPreprocessEvent e) {
        PAUser p = PAServer.getUser(e.getPlayer());

        if(e.getMessage().startsWith("/plot")) return;

        if (e.getMessage().startsWith("/?") || e.getMessage().startsWith("/bukkit:") || e.getMessage().startsWith("/pl") || e.getMessage().startsWith("/plugins") || e.getMessage().startsWith("/minecraft:") || e.getMessage().startsWith("/spigot:")) {
            if (p.isOnRank(PACmd.Grupo.Builder)) return;
            p.sendMessage(PAData.CORE.getPrefix() + "&3Buenas, estas intentando acceder a unos plugins que &cCadox8 &3y &cWikijito7 &3han desarrollado" +
                    " por lo que te va a ser imposible. :D");
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSignChange(SignChangeEvent e) {
        String[] line = e.getLines();
        for (int i = 0; i < line.length; i++) e.setLine(i, Utils.colorize(line[i]));
    }
}
