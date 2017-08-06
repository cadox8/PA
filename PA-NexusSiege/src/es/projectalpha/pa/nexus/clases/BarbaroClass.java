package es.projectalpha.pa.nexus.clases;

import es.projectalpha.pa.core.utils.ItemMaker;
import es.projectalpha.pa.nexus.api.NexusPlayer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public final class BarbaroClass {

    private ItemStack item1 = new ItemMaker(Material.IRON_AXE).setDisplayName("Hacha de Barbaro").build();
    private ItemStack armor1 = new ItemMaker(Material.IRON_HELMET).setDisplayName("Casco del barbaro").build();
    private ItemStack armor2 = new ItemMaker(Material.IRON_CHESTPLATE).setDisplayName("Peto del barbaro").build();
    private ItemStack armor3 = new ItemMaker(Material.IRON_LEGGINGS).setDisplayName("Pantalones del barbaro").build();
    private ItemStack armor4 = new ItemMaker(Material.IRON_BOOTS).setDisplayName("Botas del barbaro").build();

    public void giveBarbaro(NexusPlayer p){



        p.getPlayer().getInventory().setItem(1, item1);
        p.getPlayer().getInventory().setItem(100,armor1);
        p.getPlayer().getInventory().setItem(101,armor2);
        p.getPlayer().getInventory().setItem(102,armor3);
        p.getPlayer().getInventory().setItem(103,armor4);

        p.sendMessage("&6Recibido el kit del barbaro.");

    }

    public void removeBarbaro(NexusPlayer p){

        p.getPlayer().getInventory().remove(item1);
        p.getPlayer().getInventory().remove(armor1);
        p.getPlayer().getInventory().remove(armor2);
        p.getPlayer().getInventory().remove(armor3);
        p.getPlayer().getInventory().remove(armor4);

        p.sendMessage("&7Eliminado el kit del barbaro.");

    }



}
