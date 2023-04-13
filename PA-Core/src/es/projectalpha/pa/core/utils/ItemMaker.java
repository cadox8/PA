package es.projectalpha.pa.core.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.List;

public class ItemMaker {

    private final ItemStack itemStack;

    public ItemMaker() {
        itemStack = new ItemStack(Material.AIR);
    }

    public ItemMaker(ItemStack i) {
        this.itemStack = i;
    }

    public ItemMaker(Material type) {
        itemStack = new ItemStack(type);
    }

    public static ItemStack setAmount(ItemStack itemStack, int amount) {
        itemStack.setAmount(amount);
        return itemStack;
    }

    public ItemMaker setType(Material type) {
        itemStack.setType(type);
        return this;
    }

    public ItemMaker setAmount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    public ItemMaker setDisplayName(String displayName) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.setDisplayName(Utils.colorize(displayName));
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemMaker setDurability(short durability) {
        this.itemStack.setDurability(durability);
        return this;
    }

    public ItemMaker setLores(List<String> lores) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.setLore(lores);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemMaker setLores(String... lores) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        List<String> loresList = new ArrayList<>();
        for (String lore : lores) loresList.add(Utils.colorize(lore));
        itemMeta.setLore(loresList);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemMaker addItemFlag(ItemFlag itemFlag) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.addItemFlags(itemFlag);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemMaker addItemFlag(ItemFlag... itemFlags) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.addItemFlags(itemFlags);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemMaker addEnchant(Enchantment ench, int level) {
        this.itemStack.addUnsafeEnchantment(ench, level);
        return this;
    }

    public ItemMaker addPotionType(PotionType type, int time) {
        addPotionType(type, time, 0);
        return this;
    }

    public ItemMaker addPotionType(PotionType type, int time, int amplifier) {
        PotionMeta itemMeta = (PotionMeta) this.itemStack.getItemMeta();
        itemMeta.setMainEffect(type.getEffectType());

        itemMeta.addCustomEffect(new PotionEffect(type.getEffectType(), time * 20, amplifier), true);

        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemMaker setUnbreakable() {
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.setUnbreakable(true);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemStack build() {
        return this.itemStack;
    }
}
