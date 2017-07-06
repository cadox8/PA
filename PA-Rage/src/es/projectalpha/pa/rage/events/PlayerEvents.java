package es.projectalpha.pa.rage.events;

import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.rage.RageGames;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlayerEvents implements Listener {

    private RageGames plugin;

    public PlayerEvents(RageGames instance) {
        this.plugin = instance;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        e.setJoinMessage(null);

        if (plugin.getGm().isInLobby()) {
            plugin.getServer().getOnlinePlayers().forEach(p -> player.showPlayer(p));
            plugin.getServer().getOnlinePlayers().forEach(p -> p.showPlayer(player));
            player.teleport(plugin.getAm().getRandomSpawn());
            RageGames.getPlayer(player).setLobby();
            Utils.broadcastMsg("&7Ha entrado al juego &e" + player.getDisplayName() + " &3(&b" + plugin.getGm().getPlaying().size() + "&d/&b" + plugin.getAm().getMaxPlayers() + "&3)");
            plugin.getGm().checkStart();
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (!RageGames.getPlayer(e.getPlayer()).isOnRank(PACmd.Grupo.Admin)) e.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (!RageGames.getPlayer(e.getPlayer()).isOnRank(PACmd.Grupo.Admin)) e.setCancelled(true);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onPickUp(PlayerPickupItemEvent e) {
        e.setCancelled(true);
    }
}
