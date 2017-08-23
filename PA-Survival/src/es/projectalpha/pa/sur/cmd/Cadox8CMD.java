package es.projectalpha.pa.sur.cmd;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.core.utils.ItemMaker;
import es.projectalpha.pa.core.utils.Sounds;
import org.bukkit.Material;

public class Cadox8CMD extends PACmd {

    public Cadox8CMD() {
        super("cadox8", Grupo.Usuario);
    }

    public void run(PAUser u, String label, String... args) {
        if (args.length == 0) {
            if (!u.isOnRank(Grupo.Mod)) {
                u.sendMessage(PAData.SURVIVAL.getPrefix() + "&3Vaya vaya vaya... Pero si alguien está intentando acceder a este comando...");
                return;
            } else {
                u.sendMessage(PAData.SURVIVAL.getPrefix() + "&3QUE TE APRENDAS LOS ARGUMENTOS!");
                return;
            }
        }
        if (!u.isOnRank(Grupo.Mod)) return;
        switch (args.length) {
            case 1:
                switch (args[0]) {
                    case "pico":
                        u.getPlayer().getInventory().addItem(new ItemMaker(Material.WOOD_PICKAXE).setDisplayName("&dTu pico <3").setUnbreakable().build());
                        u.getPlayer().updateInventory();
                        break;
                    case "magic":
                        u.sendSound(Sounds.AMBIENCE_THUNDER);
                        break;
                }
                break;
        }
    }
}
