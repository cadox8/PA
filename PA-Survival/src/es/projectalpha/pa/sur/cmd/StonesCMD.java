package es.projectalpha.pa.sur.cmd;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.core.utils.CuboidZone;
import es.projectalpha.pa.core.utils.Messages;
import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.sur.PASurvival;
import es.projectalpha.pa.sur.files.Files;
import es.projectalpha.pa.sur.manager.Balance;
import es.projectalpha.pa.sur.utils.Stones;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.List;

public class StonesCMD extends PACmd {

    private PASurvival plugin = PASurvival.getInstance();

    public StonesCMD() {
        super("pvp", Grupo.Usuario);
    }

    private Files files = plugin.getFiles();
    private Balance balance = new Balance();
    private Player p;
    private Location l;
    private int i = files.getStone().getInt("piedras");
    private Stones stones = new Stones();

    @Override
    public void run(PAUser user, String label, String[] args) {
        if(args.length == 0){
            user.sendMessage(Messages.getMessage(Messages.NEED_ARGS, PAData.SURVIVAL));
            return;
        }

        if(args.length == 1){
            switch (args[0]){
                case "add":
                case "remove":
                    user.sendMessage(Messages.getMessage(Messages.NEED_ARGS, PAData.SURVIVAL));
                break;
                case "1":
                    user.getPlayer().getInventory().addItem(stones.stone1);
                    break;
                case "2":
                    user.getPlayer().getInventory().addItem(stones.stone2);
                    break;
                case "3":
                    user.getPlayer().getInventory().addItem(stones.stone3);
                    break;
                case "4":
                    user.getPlayer().getInventory().addItem(stones.stone4);
                    break;
                case "5":
                    user.getPlayer().getInventory().addItem(stones.stone5);
                    break;
                case "6":
                    user.getPlayer().getInventory().addItem(stones.stone6);
                    break;
                case "staff":
                    user.getPlayer().getInventory().addItem(stones.staffst);
                    break;
                default:
                    user.sendMessage(PAData.SURVIVAL.getPrefix() + "&4Ese comando no existe, revisa que lo hayas escrito bien.");
                    break;
            }

        }

        if(args.length == 2){
            switch (args[0]){
                case "add":
                    p = Bukkit.getPlayerExact(args[1]);
                    Location l = user.getPlayer().getLocation();
                    for(int s = 0; s <= i; s++){
                        Block fb1,fb2;
                        fb1 = l.getWorld().getBlockAt(l.add(Utils.stringToLocation(files.getStone().getString("piedras." + s + ".b1"))));
                        fb2 = l.getWorld().getBlockAt(l.add(Utils.stringToLocation(files.getStone().getString("piedras." + s + ".b2"))));
                        CuboidZone fcz = new CuboidZone(fb1, fb2);
                        boolean owner = files.getStone().getString("piedras." + s + ".owner").equals(user.getName());
                        fcz.toArray().forEach(st ->{
                            if(p.getLocation() == st.getLocation() && !owner){
                                p.sendMessage(PAData.SURVIVAL.getPrefix() + ChatColor.DARK_RED + "No puedes darle permisos a otra persona si no estás en tu región.");
                                return;
                            }
                        });
                        files.getStone().set("piedras." + s + ".perm.", p.getName());
                        Files.saveFiles();
                    }
                    break;
                case "remove":
                    p = Bukkit.getPlayerExact(args[1]);
                    l = user.getPlayer().getLocation();
                    for(int s = 0; s <= i; s++){
                        Block fb1,fb2;
                        fb1 = l.getWorld().getBlockAt(l.add(Utils.stringToLocation(files.getStone().getString("piedras." + s + ".b1"))));
                        fb2 = l.getWorld().getBlockAt(l.add(Utils.stringToLocation(files.getStone().getString("piedras." + s + ".b2"))));
                        CuboidZone fcz = new CuboidZone(fb1, fb2);
                        boolean owner = files.getStone().getString("piedras." + s + ".owner").equals(user.getName());
                        fcz.toArray().forEach(st ->{
                            if(p.getLocation() == st.getLocation() && !owner){
                                p.sendMessage(PAData.SURVIVAL.getPrefix() + ChatColor.DARK_RED + "No puedes quitarle permisos a otra persona si no estás en tu región.");
                                return;
                            }
                        });
                        List<String> perms = files.getStone().getStringList("piedras." + s + ".perm.");
                        perms.remove(p.getName());
                        files.getStone().set("piedras." + s + ".perm.", perms);
                        Files.saveFiles();
                    }
                    break;
                default:
                    user.sendMessage(PAData.SURVIVAL.getPrefix() + "&4Ese comando no existe, revisa que lo hayas escrito bien.");
                    break;
            }
        }

    }
}
