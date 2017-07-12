package es.projectalpha.pa.lobby.events;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAServer;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.lobby.api.LobbyPlayer;
import es.projectalpha.pa.lobby.utils.LobbyTeams;
import es.projectalpha.pa.lobby.PALobby;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.*;

import java.util.Random;
import java.util.UUID;

public class PlayerEvents implements Listener {

    private PALobby plugin;

    public PlayerEvents(PALobby instance){
        this.plugin = instance;
    }

    @EventHandler
    public void onLoggin(PlayerLoginEvent e){
        LobbyPlayer u = PALobby.getPlayer(e.getPlayer());

        if (u.isOnRank(PACmd.Grupo.ORIGIN) && plugin.getServer().getOnlinePlayers().size() == plugin.getServer().getMaxPlayers()){
            getRandomUser().getPlayer().kickPlayer(Utils.colorize("&cUn jugador con rango Origin o más ha entrado al servidor, por lo que has sido kickeado"));
        }
    }

    private PAUser getRandomUser(){
        UUID uuid = plugin.getServer().getOnlinePlayers().toArray(new Player[plugin.getServer().getOnlinePlayers().size()])[new Random().nextInt(plugin.getServer().getOnlinePlayers().size())].getUniqueId();
        LobbyPlayer d = new LobbyPlayer(uuid);
        if (d.isOnRank(PACmd.Grupo.ORIGIN)) getRandomUser();
        return d;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        LobbyPlayer u = PALobby.getPlayer(e.getPlayer());

        u.lobbyScoreboard();
        LobbyTeams.setScoreboardTeam(u);

        if (plugin.getConfig().getString("spawn").equalsIgnoreCase("NONE")) {
            if (u.isOnRank(PACmd.Grupo.Admin)) {
                u.sendMessage(PAData.PAPlugins.CORE.getPrefix() + "&7El spawn no está definido. Puedes hacerlo poniendo /forcespawn set en las coordenadas que quieras");
                return;
            }
        }
        u.sendToSpawn();
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        LobbyPlayer u = PALobby.getPlayer(e.getPlayer());

        if (!u.isOnRank(PACmd.Grupo.Admin)) e.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        LobbyPlayer u = PALobby.getPlayer(e.getPlayer());

        if (!u.isOnRank(PACmd.Grupo.Admin)) e.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        LobbyPlayer u = PALobby.getPlayer(e.getPlayer());

        if (!u.isOnRank(PACmd.Grupo.Admin)){
            if (e.getClickedBlock() != null) {
                if (e.getClickedBlock().getType().equals(Material.TRAP_DOOR) || e.getClickedBlock().getType().equals(Material.IRON_TRAPDOOR)
                        || e.getClickedBlock().getType().equals(Material.FENCE_GATE) || e.getClickedBlock().getType().equals(Material.FIRE)
                        || e.getClickedBlock().getType().equals(Material.CAULDRON) || e.getClickedBlock().getRelative(BlockFace.UP).getType().equals(Material.FIRE)
                        || e.getClickedBlock().getType() == Material.CHEST || e.getClickedBlock().getType() == Material.TRAPPED_CHEST
                        || e.getClickedBlock().getType() == Material.DROPPER || e.getClickedBlock().getType() == Material.DISPENSER
                        || e.getClickedBlock().getType() == Material.BED_BLOCK || e.getClickedBlock().getType() == Material.BED
                        || e.getClickedBlock().getType() == Material.WORKBENCH || e.getClickedBlock().getType() == Material.BREWING_STAND
                        || e.getClickedBlock().getType() == Material.ANVIL || e.getClickedBlock().getType() == Material.DARK_OAK_FENCE_GATE
                        || e.getClickedBlock().getType() == Material.SPRUCE_FENCE_GATE || e.getClickedBlock().getType() == Material.FURNACE
                        || e.getClickedBlock().getType() == Material.BURNING_FURNACE || e.getClickedBlock().getType() == Material.HOPPER
                        || e.getClickedBlock().getType() == Material.ENCHANTMENT_TABLE || e.getClickedBlock().getType() == Material.STONE_BUTTON
                        || e.getClickedBlock().getType() == Material.WOOD_BUTTON) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void onCombust(EntityCombustEvent e){
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onInventoryMoveItemEvent(InventoryMoveItemEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPlayerDrop(PlayerDropItemEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onBlockDamage(BlockDamageEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPlayerPickupItem(PlayerPickupItemEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onEntityTarget(EntityTargetEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onEntityDamage(EntityDamageEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(PlayerDeathEvent e) {
        e.setDeathMessage("");
    }
}
