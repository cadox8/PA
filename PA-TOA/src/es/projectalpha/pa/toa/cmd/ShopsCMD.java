package es.projectalpha.pa.toa.cmd;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.core.utils.Messages;
import es.projectalpha.pa.core.utils.NoAI;
import es.projectalpha.pa.core.utils.Utils;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;

public class ShopsCMD extends PACmd {

    public ShopsCMD() {
        super("shops", Grupo.Admin, "tiendas");
    }

    public void run(PAUser u, String label, String... args) {
        switch (args.length) {
            case 0:
                u.sendMessage(Messages.getMessage(Messages.NEED_ARGS, PAData.TOA));
                break;
            case 1:
                if (spawnVillager(u.getLoc(), Utils.isInt(args[0]) ? Integer.parseInt(args[0]) : -1)) {
                    u.sendMessage(PAData.TOA.getPrefix() + "&2Vendedor creado con exito");
                    return;
                }
                u.sendMessage(PAData.TOA.getPrefix() + "&cError al crear el aldeano");
                break;
            default:
                u.sendMessage(Messages.getMessage(Messages.BUFF_ARGS, PAData.TOA));
                break;
        }
    }

    private boolean spawnVillager(Location l, int data) {
        final Villager v = (Villager) l.getWorld().spawnEntity(l, EntityType.VILLAGER);
        v.setAgeLock(true);
        v.setCustomNameVisible(true);

        NoAI.setAIEnabled(v, false); //No queremos la "querida" AI de Minecraft :D

        v.teleport(l);

        switch (data) {
            case 0:
                v.setProfession(Villager.Profession.FARMER);
                v.setCustomName("&dComprador Variado");
                return true;
            case 1:
                v.setProfession(Villager.Profession.BLACKSMITH);
                v.setCustomName("&cComprador Armas");
                return true;
            default:
                return false;
        }
    }
}
