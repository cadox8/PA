package es.projectalpha.pa.lobby.events;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAServer;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.core.utils.ItemMaker;
import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.lobby.PALobby;
import es.projectalpha.pa.lobby.cosmetics.Cosmetic;
import es.projectalpha.pa.lobby.utils.Helpers;
import es.projectalpha.pa.lobby.utils.LobbyMenu;
import es.projectalpha.pa.lobby.utils.LobbyTeams;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import java.util.Random;

public class PlayerEvents implements Listener {

    private PALobby plugin;

    public PlayerEvents(PALobby instance) {
        this.plugin = instance;
    }

    @EventHandler
    public void onLoggin(PlayerLoginEvent e) {
        PAUser u = PAServer.getUser(e.getPlayer());

        if (u.isOnRank(PACmd.Grupo.ORIGIN) && plugin.getServer().getOnlinePlayers().size() == plugin.getServer().getMaxPlayers()) {
            getRandomUser().getPlayer().kickPlayer(Utils.colorize("&cUn jugador con rango Origin o más ha entrado al servidor, por lo que has sido kickeado"));
        }
    }

    private PAUser getRandomUser() {
        PAUser d = PAServer.users.get(new Random().nextInt(PAServer.users.size()));
        if (d.isOnRank(PACmd.Grupo.ORIGIN)) getRandomUser();
        return d;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e) {
        PAUser u = PAServer.getUser(e.getPlayer());
        Player p = e.getPlayer();
        Helpers h = new Helpers(u);

        h.lobbyScoreboard();
        LobbyTeams.setScoreboardTeam(u);
        new Helpers(u).sendToSpawn();
        u.getPlayer().getInventory().clear();
        u.getPlayer().getInventory().setItem(0, new ItemMaker(Material.NETHER_STAR).setDisplayName("&cJuegos").build());
        u.getPlayer().getInventory().setItem(4, new ItemMaker(Material.REDSTONE).setDisplayName("&7Cosmeticos").build());
        u.getPlayer().updateInventory();

        u.sendMessage("&6Actualmente hay &2" + PAServer.users.size() + " &6usuarios en línea");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLeave(PlayerQuitEvent e) {
        PAUser u = PAServer.getUser(e.getPlayer());

        LobbyTeams.removeScoreboardTeam(u);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        PAUser u = PAServer.getUser(e.getPlayer());

        if (!u.isOnRank(PACmd.Grupo.Builder)) e.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        PAUser u = PAServer.getUser(e.getPlayer());

        if (!u.isOnRank(PACmd.Grupo.Builder)) e.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        PAUser u = PAServer.getUser(e.getPlayer());

        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getItem() == null) return;
            e.setCancelled(true);
            if (Cosmetic.useCosmetic(u, e.getItem().getType())) return;

            switch (e.getItem().getType()) {
                case NETHER_STAR:
                    e.setCancelled(true);
                    LobbyMenu.openMenu(u, LobbyMenu.MenuType.SERVERS);
                    break;
                case REDSTONE:
                    e.setCancelled(true);
                    u.sendMessage(PAData.LOBBY.getPrefix() + "&cNo estamos listos aún");
                    //LobbyMenu.openMenu(u, LobbyMenu.MenuType.COSMETICOS);
                    break;
            }

            if (e.getClickedBlock() == null) return;
            if (e.getClickedBlock().getType() == Material.ENCHANTMENT_TABLE) {
                e.setCancelled(true);
                u.sendMessage(PAData.LOBBY.getPrefix() + "&cActualmente estamos trabajando en esto, disculpen las molestias");
            }
        }

        if (!u.isOnRank(PACmd.Grupo.Builder)) {
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
                        || e.getClickedBlock().getType() == Material.STONE_BUTTON || e.getClickedBlock().getType() == Material.WOOD_BUTTON) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
        PAUser u = PAServer.getUser(e.getPlayer());
        Entity en = e.getRightClicked();

        e.setCancelled(true);
        System.out.println("Entidad encontrada");

        if (en instanceof ArmorStand) {
            ArmorStand ar = (ArmorStand) en;
            e.setCancelled(true);

            System.out.println(ar.getItemInHand().getType());

            switch (ar.getItemInHand().getType()) {
                case IRON_AXE:
                    u.sendToServer("toa");
                    break;
                case BOW:

                    break;
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onCombust(EntityCombustEvent e) {
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

    @EventHandler
    public void onSpawn(CreatureSpawnEvent e){
        if (e.getEntity().getCustomName() == null || e.getEntity().getCustomName().equalsIgnoreCase("")) e.setCancelled(true);
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onWeatherChange(WeatherChangeEvent event) {
        if(event.toWeatherState()) event.setCancelled(true);
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onThunderChange(ThunderChangeEvent event) {
        if(event.toThunderState()) event.setCancelled(true);
    }
}
