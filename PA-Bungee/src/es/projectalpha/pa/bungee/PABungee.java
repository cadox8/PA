package es.projectalpha.pa.bungee;

import es.projectalpha.pa.bungee.cmd.CommandAlert;
import es.projectalpha.pa.bungee.cmd.CommandLobby;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public class PABungee extends Plugin implements Listener {

    private String pluginChannel = "PA";

    @Getter private static PABungee instance;

    @Override
    public void onEnable() {
        instance = this;
        getProxy().registerChannel(pluginChannel);
        getProxy().getPluginManager().registerListener(this, this);
        getProxy().getPluginManager().registerCommand(this, new CommandAlert());
        getProxy().getPluginManager().registerCommand(this, new CommandLobby());
    }

    @EventHandler
    public void onPlayerJoin(PostLoginEvent e) {
        ProxiedPlayer p = e.getPlayer();
        p.connect(getLogin());
    }

    @EventHandler
    public void onServerKickEvent(ServerKickEvent e) {
        e.setCancelled(true);
        e.setCancelServer(getLobby());
    }

    @EventHandler
    public void onChat(ChatEvent e) {
        ProxiedPlayer p = (ProxiedPlayer)e.getSender();

        if (p.getServer().getInfo().getName().equalsIgnoreCase("login")) e.setCancelled(true);
        if (e.getMessage().startsWith("/bungee")) e.setCancelled(true);
    }

    @EventHandler
    public void onProxyPing(ProxyPingEvent e) {
        ServerPing ping = e.getResponse();
        String motd = "&7&l&k||||&r&9&l ProjectAlpha &r&7&l&k||||&r&7   [&91.8 - 1.12.1&7]  &r&7Alpha\n      Beta: &bRageGames, &2Survival &6y mucho más!           ";
        //String motd = "&7&l&k||||&r&9&l ProjectAlpha &r&7&l&k||||&r&7   [&91.8 - 1.12.1&7]  &r&7Alpha\n      &cMANTENIMIENTO          ";

        ping.setDescriptionComponent(new TextComponent(ChatColor.translateAlternateColorCodes('&', motd)));
    }

    public ServerInfo getLobby() {
        return getProxy().getServerInfo("lobby");
    }
    private ServerInfo getLogin() {
        return getProxy().getServerInfo("login");
    }
}
