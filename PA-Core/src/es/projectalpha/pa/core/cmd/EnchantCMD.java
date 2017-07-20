package es.projectalpha.pa.core.cmd;

import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.utils.Log;
import es.projectalpha.pa.core.utils.Utils;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class EnchantCMD extends PACmd {

    public EnchantCMD() {
        super("enchant", Grupo.Admin, "encantar");
    }

    public void run(PAUser user, String label, String[] args) {
        if (args.length == 2) {
            ItemStack i = user.getPlayer().getInventory().getItemInHand();

            Enchantment enchantment;
            int level;

            if (!Utils.isInt(args[1])) return;
            enchantment = getEnchantment(args[0]);
            level = Integer.parseInt(args[1]);

            if (enchantment == null) return;

            i.addUnsafeEnchantment(enchantment, level);
        }
    }

    private Enchantment getEnchantment(String enchantment) {
        try {
            return Enchantment.getByName(enchantment);
        } catch (NullPointerException e) {
            Log.log(Log.Level.WARNING, "El encantamiento no existe");
            return Enchantment.ARROW_INFINITE;
        }
    }
}
