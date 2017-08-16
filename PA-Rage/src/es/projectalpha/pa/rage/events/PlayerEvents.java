package es.projectalpha.pa.rage.events;

import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.rage.RageGames;
import es.projectalpha.pa.rage.api.RagePlayer;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class PlayerEvents implements Listener {

    private RageGames plugin;

    public PlayerEvents(RageGames instance) {
        this.plugin = instance;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e) {
        RagePlayer u = RageGames.getPlayer(e.getPlayer());
        e.setJoinMessage(null);

        u.getPlayer().setFlySpeed(0.2f);
        u.getPlayer().setWalkSpeed(0.2f);

        if (plugin.getGm().isInLobby()) {
            u.getPlayer().getInventory().clear();
            plugin.getGm().addPlayerToGame(u);
            plugin.getServer().getOnlinePlayers().forEach(p -> u.getPlayer().showPlayer(p));
            plugin.getServer().getOnlinePlayers().forEach(p -> p.showPlayer(u.getPlayer()));
            u.setLobby();
            Utils.broadcastMsg("&7Ha entrado al juego &e" + u.getName() + " &3(&b" + plugin.getGm().getPlaying().size() + "&d/&b" + plugin.getAm().getMaxPlayers() + "&3)");
            plugin.getGm().checkStart();

            plugin.getServer().getScheduler().runTaskLater(plugin, () -> u.sendToLobby(), 20);
        } else {
            u.getPlayer().setGameMode(GameMode.SPECTATOR);
            u.getPlayer().teleport(plugin.getAm().getRandomSpawn());
            plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
                u.sendActionBar("&cEstas en modo espectador, pon &6/lobby &cpara salir");
                if (u.getPlayer().getGameMode() != GameMode.SPECTATOR) u.getPlayer().setGameMode(GameMode.SPECTATOR);
            }, 0, 20);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player p = e.getPlayer();
        RagePlayer u = RageGames.getPlayer(p);
        plugin.getGm().removePlayerFromGame(u);
        RageGames.players.remove(u);
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
        if(!(e.getEntity() instanceof Player)) return;
        Player p = (Player) e.getEntity();

        switch (e.getCause()) {
            case LAVA:
                e.setCancelled(true);
                p.teleport(plugin.getAm().getRandomSpawn());
                RageGames.getPlayer(p).resetPlayer();
                p.setHealth(20d);
                p.setFireTicks(0);
                break;
            case FALL:
                e.setCancelled(true);
                break;
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

    @EventHandler
    public void inventoryClick(InventoryClickEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onFood(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onWeather(WeatherChangeEvent e) {
        e.setCancelled(true);
    }
}
