package es.projectalpha.pa.sur.events;

import es.projectalpha.pa.core.utils.CuboidZone;
import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.sur.PASurvival;
import es.projectalpha.pa.sur.files.Files;
import es.projectalpha.pa.sur.utils.Stones;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;

public class StonesEvents implements Listener {

    private Files files = new Files();
    private Stones stones = new Stones();
    private int piedras = files.getStone().getInt("piedras");
    double l1,l2,l3;
    double fl1,fl2,fl3;
    private PASurvival plugin;

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        Player p = e.getPlayer();
        Block b = e.getBlock();

    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e){

        Player p = e.getPlayer();
        Block b = e.getBlockPlaced();
        Block b1;
        Block b2;
        Location l = b.getLocation();
        if(b == stones.stone1 || b == stones.stone2 ||b == stones.stone3 ||b == stones.stone4 ||b == stones.stone5 || b == stones.stone6){
            switch(b.getType()){
                case COAL_BLOCK:
                    l1 = 5; l2 = 5; l3 = 5;
                    fl1 = -5; fl2 = -5; fl3 = -5;
                    b1 = l.getWorld().getBlockAt(l.add(l1, l2, l3));
                    b2 = l.getWorld().getBlockAt(l.add(fl1, fl2, fl3));
                    new CuboidZone(b1,b2);
                    break;

                case IRON_ORE:
                    l1 = 10; l2 = 10; l3 = 10;
                    fl1 = -10; fl2 = -10; fl3 = -10;
                    b1 = l.getWorld().getBlockAt(l.add(l1, l2, l3));
                    b2 = l.getWorld().getBlockAt(l.add(fl1, fl2, fl3));
                    new CuboidZone(b1,b2);
                    break;

                case GOLD_ORE:
                    l1 = 10; l2 = 10; l3 = 10;
                    fl1 = -10; fl2 = -10; fl3 = -10;
                    b1 = l.getWorld().getBlockAt(l.add(l1, l2, l3));
                    b2 = l.getWorld().getBlockAt(l.add(fl1, fl2, fl3));
                    new CuboidZone(b1,b2);
                    break;

                case LAPIS_ORE:
                    l1 = 10; l2 = 10; l3 = 10;
                    fl1 = -10; fl2 = -10; fl3 = -10;
                    b1 = l.getWorld().getBlockAt(l.add(l1, l2, l3));
                    b2 = l.getWorld().getBlockAt(l.add(fl1, fl2, fl3));
                    new CuboidZone(b1,b2);
                    break;

                case EMERALD_ORE:
                    l1 = 10; l2 = 10; l3 = 10;
                    fl1 = -10; fl2 = -10; fl3 = -10;
                    b1 = l.getWorld().getBlockAt(l.add(l1, l2, l3));
                    b2 = l.getWorld().getBlockAt(l.add(fl1, fl2, fl3));
                    new CuboidZone(b1,b2);
                    break;

                case DIAMOND_ORE:
                    l1 = 10; l2 = 10; l3 = 10;
                    fl1 = -10; fl2 = -10; fl3 = -10;
                    b1 = l.getWorld().getBlockAt(l.add(l1, l2, l3));
                    b2 = l.getWorld().getBlockAt(l.add(fl1, fl2, fl3));
                    new CuboidZone(b1,b2);
                    break;
            }
        }
    }

}
