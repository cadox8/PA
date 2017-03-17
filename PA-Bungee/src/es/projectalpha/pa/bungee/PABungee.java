package es.projectalpha.pa.bungee;

import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public class PABungee extends Plugin implements Listener {

    private String pluginChannel = "PA";

    @Override
    public void onEnable() {
        getProxy().registerChannel(pluginChannel);
        getProxy().getPluginManager().registerListener(this, this);
        getProxy().getPluginManager().registerCommand(this, new CommandAlert());
    }

    @EventHandler
    public void onPlayerJoin(PostLoginEvent e) {
        ProxiedPlayer p = e.getPlayer();
        p.connect(getLobby());
    }

    @EventHandler
    public void onServerKickEvent(ServerKickEvent e) {
        e.setCancelled(true);
        e.setCancelServer(getLobby());
    }

    @EventHandler
    public void onProxyPing(ProxyPingEvent e){
        ServerPing.Protocol version = e.getResponse().getVersion();
        version.setName("ProjectAlpha.es");
        version.setProtocol(316);
        e.getResponse().setVersion(version);
    }

    private ServerInfo getLobby() {
        return getProxy().getServerInfo("lobby");
    }
}
