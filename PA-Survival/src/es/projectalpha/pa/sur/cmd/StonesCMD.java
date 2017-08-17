package es.projectalpha.pa.sur.cmd;

import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.core.utils.CuboidZone;
import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.sur.PASurvival;
import es.projectalpha.pa.sur.files.Files;
import es.projectalpha.pa.sur.manager.Balance;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

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

    @Override
    public void run(PAUser user, String label, String[] args) {
        if(args.length == 0){
            user.sendMessage("&4Aquí ha habido un problema, te faltan argumentos.");
        }

        if(args.length == 1){

            switch (args[0]){
                case "add":
                case "remove":
                user.sendMessage("&4Aquí ha habido un problema, te faltan argumentos.");
                break;
                default:
                    user.sendMessage("&4Ese comando no existe, revisa que lo hayas escrito bien.");
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
                        Boolean owner = files.getStone().getString("piedras." + s + ".owner").equals(user.getName());
                            fcz.toArray().forEach(st ->{
                                if(p.getLocation() == st.getLocation() && !owner){
                                    p.sendMessage(ChatColor.DARK_RED + "No puedes darle permisos a otra persona si no estás en tu región.");
                                    return;
                                }
                            });
                            files.getStone().set("piedras." + s + ".perm.", p.getName());
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
                        Boolean owner = files.getStone().getString("piedras." + s + ".owner").equals(user.getName());
                        fcz.toArray().forEach(st ->{
                            if(p.getLocation() == st.getLocation() && !owner){
                                p.sendMessage(ChatColor.DARK_RED + "No puedes quitarle permisos a otra persona si no estás en tu región.");
                                return;
                            }
                        });
                        files.getStone().getList("piedras." + s + ".perm.").forEach(j ->{
                                    if(j.toString().equals(p.getName())){
                                     j.toString().replace(p.getName(), "");
                                    }
                        });
                    }
                    break;
                default:
                    user.sendMessage("&4Ese comando no existe, revisa que lo hayas escrito bien.");
                    break;
            }
        }

    }
}
