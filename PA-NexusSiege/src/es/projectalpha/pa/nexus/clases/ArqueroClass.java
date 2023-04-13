package es.projectalpha.pa.nexus.clases;

import es.projectalpha.pa.core.utils.ItemMaker;
import es.projectalpha.pa.nexus.api.NexusPlayer;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public final class ArqueroClass {


    private ItemStack item1 = new ItemMaker(Material.BOW).setDisplayName("Arco del Arquero").addEnchant(Enchantment.ARROW_INFINITE, 1).build();
    private ItemStack item2 = new ItemMaker(Material.ARROW).setDisplayName("Flecha del Arquero").setAmount(10).build();
    private ItemStack item3 = new ItemMaker(Material.WOODEN_SWORD).setDisplayName("Espada del Arquero").build();
    private ItemStack armor1 = new ItemMaker(Material.CHAINMAIL_HELMET).setDisplayName("Casco del arquero").build();
    private ItemStack armor2 = new ItemMaker(Material.CHAINMAIL_CHESTPLATE).setDisplayName("Peto del arquero").build();
    private ItemStack armor3 = new ItemMaker(Material.CHAINMAIL_LEGGINGS).setDisplayName("Pantalones del arquero").build();
    private ItemStack armor4 = new ItemMaker(Material.CHAINMAIL_BOOTS).setDisplayName("Botas del arquero").build();

    public void giveArquero(NexusPlayer p){

        p.getPlayer().getInventory().setItem(1, item1);
        p.getPlayer().getInventory().setItem(9, item2);
        p.getPlayer().getInventory().setItem(100,armor1);
        p.getPlayer().getInventory().setItem(101,armor2);
        p.getPlayer().getInventory().setItem(102,armor3);
        p.getPlayer().getInventory().setItem(103,armor4);

        p.sendMessage("&6Recibido el kit del arquero.");

    }

    public void removeArquero(NexusPlayer p){

        p.getPlayer().getInventory().remove(item1);
        p.getPlayer().getInventory().remove(armor1);
        p.getPlayer().getInventory().remove(armor2);
        p.getPlayer().getInventory().remove(armor3);
        p.getPlayer().getInventory().remove(armor4);

    }



}
