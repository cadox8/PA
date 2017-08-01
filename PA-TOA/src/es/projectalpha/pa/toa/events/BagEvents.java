package es.projectalpha.pa.toa.events;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.core.utils.Title;
import es.projectalpha.pa.toa.TOA;
import es.projectalpha.pa.toa.api.TOAUser;
import es.projectalpha.pa.toa.utils.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class BagEvents implements Listener {

    public static HashMap<TOAUser, TOAUser> otherInvs = new HashMap<>();

    private static int itemSlot;

    private TOA plugin;

    public BagEvents(TOA Main) {
        this.plugin = Main;
    }

    public static void saveInv(TOAUser p, Inventory i) {
        FileUtils.getInv().set(p.getName(), null);
        FileUtils.saveInv();
        for (int x = 0; x < getInvSize(p); x++) {
            if (i.getItem(x) != null) {
                FileUtils.getInv().set(p.getName() + ".inv_" + x, i.getItem(x));
            }
        }
        FileUtils.saveInv();
    }

    public static void removeInv(Player p) {
        FileUtils.getInv().set(p.getName(), null);
        FileUtils.saveInv();
    }

    public static void loadInv(TOAUser p) {
        p.getPlayer().openInventory(getInventory(p));
    }

    public static void loadOtherInv(TOAUser p, TOAUser pl) {
        p.getPlayer().openInventory(getInventory(pl));
    }

    public static void removeItem(TOAUser p, ItemStack item) {
        if (getItem(p, item)) {
            int amount = FileUtils.getInv().getItemStack(p.getName() + ".inv_" + itemSlot).getAmount() - item.getAmount();
            FileUtils.getInv().set(p.getName() + ".inv_" + itemSlot, new ItemStack(item.getType(), amount, item.getDurability()));
            if (amount <= 0) {
                FileUtils.getInv().set(p.getName() + ".inv_" + itemSlot, null);
            }
        } else {
            p.sendMessage(PAData.TOA.getPrefix() + "&cNo tienes suficientes items para venderlos");
            return;
        }
        FileUtils.saveInv();
    }

    public static boolean getItem(TOAUser p, ItemStack item) {
        Inventory i = getInventory(p);
        for (int x = 0; x < getInvSize(p); x++) {
            if (i.getItem(x) == null) {
                return false;
            }
            if ((item.getType() == i.getItem(x).getType()) && (item.getAmount() <= i.getItem(x).getAmount()) && (item.getDurability() == i.getItem(x).getDurability())) {
                itemSlot = x;
                return true;
            }
        }
        return false;
    }

    private static void advisePlayer(TOAUser p) {
        int slots = getFreeSlots(p, getInventory(p));
        switch (slots) {
            case 5:
            case 2:
                p.sendMessage(PAData.TOA.getPrefix() + "&cA tu mochila la quedan &e" + slots + " &cespacios libres");
                break;
            case 1:
                p.sendMessage(PAData.TOA.getPrefix() + "&cA tu mochila la quedan &6" + slots + " &cespacios libres");
                Title.sendTitle(p.getPlayer(), 0, 3, 0, "&c¡Atención!", "&6Te queda &c1 &6espacio libre en la mochila");
                break;
            case 0:
                p.sendMessage(PAData.TOA.getPrefix() + "&cAlgo me dice que tu mochila esta llena...");
                Title.sendTitle(p.getPlayer(), 0, 3, 0, ChatColor.RED + "&c¡Atención!", "&6Tu mochila esta llena");
        }
        p.sendSound(Sound.FUSE);
    }

    private static Inventory getInventory(TOAUser p) {
        Inventory i = Bukkit.createInventory(null, getInvSize(p), ChatColor.RED + "Mochila");
        ItemStack[] item = i.getContents();
        if (FileUtils.getInv().contains(p.getName())) {
            for (int x = 0; x < getInvSize(p); x++)
                item[x] = FileUtils.getInv().getItemStack(p.getName() + ".inv_" + x);
            i.setContents(item);
            return i;
        }
        return i;
    }

    private static boolean invNoNull(Inventory i, TOAUser p) {
        if (getInventory(p) != null) {
            return true;
        }
        return false;
    }

    private static int getFreeSlots(TOAUser p, Inventory i) {
        if (invNoNull(i, p)) {
            ItemStack[] item = i.getContents();
            int sl = 0;
            for (int x = 0; x < getInvSize(p); x++) if (item[x] == null) sl++;
            return sl;
        }
        return 0;
    }

    private static void checkItem(TOAUser p, ItemStack item) {
        Inventory i = getInventory(p);
        for (int x = 0; x < getInvSize(p); x++) {
            if (i.getItem(x) != null) {
                if ((i.getItem(x).getType() == item.getType()) && (i.getItem(x).getDurability() == item.getDurability()) &&
                        (i.getItem(x).getAmount() + item.getAmount() <= item.getMaxStackSize())) {
                    i.setItem(x, new ItemStack(item.getType(), item.getAmount() + i.getItem(x).getAmount(), item.getDurability()));
                    saveInv(p, i);
                    break;
                }
            } else if (i.getItem(x) == null) {
                i.setItem(x, item);
                saveInv(p, i);
                break;
            }
        }
        advisePlayer(p);
    }

    private static int getInvSize(TOAUser p) {
        switch (p.getUserData().getGrupo()) {
            case Usuario:
                return 27;
            case VIP:
                return 36;
            case ORIGIN:
            case YT:
            case Builder:
            case Mod:
                return 45;
            case DEV:
            case Admin:
                return 63;
            default:
                return 27;
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        TOAUser p = TOA.getPlayer((Player) e.getPlayer());

        if (e.getInventory().getName().equalsIgnoreCase(ChatColor.RED + "Mochila") && otherInvs.containsKey(p)) {
            p = otherInvs.get(p);
            saveInv(p, e.getInventory());
            otherInvs.remove(p);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPickUp(PlayerPickupItemEvent e) {
        TOAUser p = TOA.getPlayer(e.getPlayer());
        Item it = e.getItem();

        if (p.isOnRank(PACmd.Grupo.Admin)) {
            e.setCancelled(false);
            return;
        }
        if (plugin.getGm().getInTower().contains(p)) {
            e.setCancelled(true);
            addItem(p, it);
        }
    }

    public static void addItem(TOAUser u, Item i) {
        if ((i.getCustomName() != null) && (getFreeSlots(u, getInventory(u)) != 0)) {
            i.getItemStack().removeEnchantment(Enchantment.ARROW_DAMAGE);
            checkItem(u, i.getItemStack());
            i.remove();
        }
    }

    public static void addItem(TOAUser u, ItemStack i) {
        if (getFreeSlots(u, getInventory(u)) != 0) checkItem(u, i);
    }
}
