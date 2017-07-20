package es.projectalpha.pa.core.events;

import es.projectalpha.pa.core.api.PAServer;
import es.projectalpha.pa.core.utils.Title;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.plugin.Plugin;

public class ResourcePackListener implements Listener {

    private final Plugin plugin;
    private String name; // Nombre del pack sin .zip
    private String host = "http://cadox8.cf/dl/";

    /**
     * Info: Cargar la clase desde cada plugin, junto con su juego.
     */

    public ResourcePackListener(Plugin instance, Games game) {
        plugin = instance;
        this.name = game.getPack();
    }

    //

    @EventHandler(ignoreCancelled = true)
    public void onResourcePlayer(PlayerResourcePackStatusEvent e) {
        Player p = e.getPlayer();

        switch (e.getStatus()) {
            case DECLINED:
            case FAILED_DOWNLOAD:
                Title.sendTitle(p, 1, 7, 1, ChatColor.RED + "Debes aceptar el ResourcePack", "");
                plugin.getServer().getScheduler().runTaskLater(plugin, () -> PAServer.getUser(p).sendToLobby(), 60L);
                break;
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.getPlayer().setResourcePack(host + name + ".zip");
    }

    @AllArgsConstructor
    public enum Games {
        LOBBY("vacio");

        @Getter
        private String pack;
    }
}
