package es.projectalpha.pa.core.cmd;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAUser;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KillAllCMD extends PACmd {

    public KillAllCMD() {
        super("killall", Grupo.DEV, Arrays.asList("kall"));
    }

    @Override
    public void run(PAUser user, String label, String[] args) {
        if (args.length == 0) {
            user.sendDiv();
            user.sendMessage(formatedCMD(label, " <Entity> [-n]", " &7-> &2Elimina las entidades del mundo"));
            user.sendMessage("&3-n &7-> &cParámetro para no comprobar el nombre");
            user.sendDiv();
        }

        if (args.length == 1) {
            EntityType entity = EntityType.valueOf(args[0]);

            if (entity == null || entity == EntityType.UNKNOWN) return;

            int mobs = worldEntities(user.getPlayer().getWorld(), entity).size();
            worldEntities(user.getPlayer().getWorld(), entity).forEach(e -> e.remove());
            user.sendMessage(PAData.CORE.getPrefix() + "&6Eliminados &c" + mobs + " &6mobs");
        }

        if (args.length == 2) {
            if (args[1].equalsIgnoreCase("-n")) {
                EntityType entity = EntityType.valueOf(args[0]);

                if (entity == null || entity == EntityType.UNKNOWN) return;

                int mobs = worldEntities(user.getPlayer().getWorld(), entity).size();
                worldClassEntities(user.getPlayer().getWorld(), entity).forEach(e -> e.remove());
                user.sendMessage(PAData.CORE.getPrefix() + "&6Eliminados &c" + mobs + " &6mobs");
            }
        }
    }

    private List<Entity> worldEntities(World w, EntityType entityType) {
        List<Entity> entities = new ArrayList<>();

        w.getEntities().forEach(e -> {
            if (e.getType() == entityType) {
                entities.add(e);
            }
        });
        return entities;
    }

    private List<Entity> worldClassEntities(World w, EntityType entityType) {
        List<Entity> entities = new ArrayList<>();

        w.getEntitiesByClass(entityType.getEntityClass()).forEach(e -> {
            if (e.getType() == entityType) {
                entities.add(e);
            }
        });
        return entities;
    }
}
