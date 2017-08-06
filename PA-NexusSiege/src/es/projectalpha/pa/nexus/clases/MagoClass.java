package es.projectalpha.pa.nexus.clases;

import es.projectalpha.pa.core.utils.ItemMaker;
import es.projectalpha.pa.nexus.api.NexusPlayer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public final class MagoClass {
    ItemStack item1 = new ItemMaker(Material.STICK).setDisplayName("Varita").setLores("&5Harry Potter quedría esta varita.").build();
    ItemStack item2 = new ItemMaker(Material.BOOK).setDisplayName("Libro de hechizos").build();
    ItemStack item3 = new ItemMaker(Material.BARRIER).setDisplayName("Próximamente").build();

    ItemStack armor1 = new ItemMaker(Material.LEATHER_HELMET).setDisplayName("Casco del mago").build();
    ItemStack armor2 = new ItemMaker(Material.LEATHER_CHESTPLATE).setDisplayName("Peto del mago").build();
    ItemStack armor3 = new ItemMaker(Material.LEATHER_LEGGINGS).setDisplayName("Pantalones del mago").build();
    ItemStack armor4 = new ItemMaker(Material.LEATHER_BOOTS).setDisplayName("Botas del mago").build();

    public void giveMago(NexusPlayer p){

        p.getPlayer().getInventory().setItem(1, item1);
        p.getPlayer().getInventory().setItem(2, item2);
        p.getPlayer().getInventory().setItem(3, item3);
        p.getPlayer().getInventory().setItem(100, armor1);
        p.getPlayer().getInventory().setItem(101, armor2);
        p.getPlayer().getInventory().setItem(102, armor3);
        p.getPlayer().getInventory().setItem(103, armor4);

        p.sendMessage("&7Obtenido el kit del mago.");

    }

    public void removeMago(NexusPlayer p){

        p.getPlayer().getInventory().remove(item1);
        p.getPlayer().getInventory().remove(item2);
        p.getPlayer().getInventory().remove(item3);
        p.getPlayer().getInventory().remove(armor1);
        p.getPlayer().getInventory().remove(armor2);
        p.getPlayer().getInventory().remove(armor3);
        p.getPlayer().getInventory().remove(armor4);

        p.sendMessage("&7Eliminado el kit del mago.");

    }

}
