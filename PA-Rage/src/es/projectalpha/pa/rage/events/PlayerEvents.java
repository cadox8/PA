package es.projectalpha.pa.rage.events;

import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.rage.RageGames;
import es.projectalpha.pa.rage.api.RagePlayer;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerEvents implements Listener {

    private RageGames plugin;

    public PlayerEvents(RageGames instance) {
        this.plugin = instance;
    }
    private Location loc = new Location(this.plugin.getServer().getWorld("lm"), 1,57,0,179,-20);
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        RagePlayer u = RageGames.getPlayer(e.getPlayer());
        e.setJoinMessage(null);

        if (plugin.getGm().isInLobby()) {
            u.getPlayer().setWalkSpeed(0.2f);
            u.getPlayer().getInventory().clear();
            plugin.getGm().addPlayerToGame(u);
            plugin.getServer().getOnlinePlayers().forEach(p -> u.getPlayer().showPlayer(p));
            plugin.getServer().getOnlinePlayers().forEach(p -> p.showPlayer(u.getPlayer()));
            u.teleport(loc);
            u.setLobby();
            Utils.broadcastMsg("&7Ha entrado al juego &e" + u.getName() + " &3(&b" + plugin.getGm().getPlaying().size() + "&d/&b" + plugin.getAm().getMaxPlayers() + "&3)");
            plugin.getGm().checkStart();
        } else {
            u.getPlayer().setGameMode(GameMode.SPECTATOR);
            plugin.getServer().getScheduler().runTaskTimer(plugin, () -> u.sendActionBar("&cEstas en modo espectador"), 0, 20);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player p = e.getPlayer();
        plugin.getGm().removePlayerFromGame(RageGames.getPlayer(p));
        Utils.broadcastMsg("&7Ha salido del juego &e" + p.getDisplayName() + " &3(&b" + plugin.getGm().getPlaying().size() + "&d/&b" + plugin.getAm().getMaxPlayers() + "&3)");
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
    public void onDamage(EntityDamageEvent e){
        Player p = (Player) e.getEntity();
        if(e.getCause() == EntityDamageEvent.DamageCause.LAVA){
        p.teleport(plugin.getAm().getRandomSpawn());
        RageGames.getPlayer(p).resetPlayer();
        p.setHealth(20d);
        p.setFireTicks(0);
        }
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
