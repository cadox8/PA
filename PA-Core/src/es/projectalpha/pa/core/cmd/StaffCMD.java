package es.projectalpha.pa.core.cmd;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.utils.Messages;
import es.projectalpha.pa.core.utils.Sounds;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;

public class StaffCMD extends PACmd {

    public StaffCMD() {
        super("staff", Grupo.Usuario);
    }

    @Override
    public void run(PAUser user, String label, String[] args) {
        if (args.length == 0) {
            user.getPlayer().closeInventory();
            user.getPlayer().openInventory(staffInv());
            user.sendSound(Sounds.CLICK);
        }

        if (args.length >= 1) user.sendMessage(Messages.getMessage(Messages.BUFF_ARGS, PAData.CORE));
    }

    //Temporal
    private Inventory staffInv() {
        Inventory inv = plugin.getServer().createInventory(null, 27, ChatColor.RED + "ProjectAlpha Staff");

        return inv;
    }
}
