package es.projectalpha.pa.core.cmd;

import es.projectalpha.pa.core.api.PAServer;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.utils.Utils;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class EnchantCMD extends PACmd {

    public EnchantCMD(){
        super("enchant", Grupo.ADMIN, "encantar");
    }

    public void run(PAUser user, String label, String[] args){
        if (args.length == 2){
            boolean unsafe = plugin.getConfig().getBoolean("allowUnsafeEnchantment");
            ItemStack i = user.getPlayer().getInventory().getItemInMainHand();

            Enchantment enchantment;
            int level;

            if (!Utils.isInt(args[1])) return;
            enchantment = getEnchantment(args[0]);
            level = Integer.parseInt(args[1]);

            if (enchantment == null) return;

            if (unsafe){
                i.addUnsafeEnchantment(enchantment, level);
                return;
            }
            if (level > enchantment.getMaxLevel()) return;

            i.addEnchantment(enchantment, level);
        }
    }

    private Enchantment getEnchantment(String enchantment){
        try{
            return Enchantment.getByName(enchantment);
        }catch (NullPointerException e){
            plugin.log(PAServer.Level.WARNING, "El encantamiento no existe");
        }
        return null;
    }
}
