package es.projectalpha.pa.core.utils;

import es.projectalpha.pa.core.PACore;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;

/**
 * WorldBorderUtils by cadox8
 **/

//Quitar WorldBorder, hacerlo propio
public class WorldBorderUtils {

    private PACore plugin = PACore.getInstance();

    private WorldBorder worldBorder;

    public WorldBorderUtils(World world){
        this.worldBorder = world.getWorldBorder();
    }

    public void setCenter(Location location){
        worldBorder.setCenter(location);
    }

    public void setRadius(double radius){
        worldBorder.setSize(radius);
    }

    public void setDamage(double damage){
        worldBorder.setDamageAmount(damage);
    }

    public void setWarning(int warning){
        worldBorder.setWarningDistance(warning);
    }
}
