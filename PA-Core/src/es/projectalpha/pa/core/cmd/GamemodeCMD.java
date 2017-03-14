package es.projectalpha.pa.core.cmd;

import es.projectalpha.pa.core.api.PAServer;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.exceptions.NullInventorySaveException;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class GamemodeCMD extends PACmd {

    public GamemodeCMD(){
        super("gamemode", Grupo.Builder, "gm");
    }

    public void run(PAUser user, String label, String[] args){
        if (args.length == 1){
            user.getUserData().setInventory(user.getPlayer().getInventory());

            //user.getPlayer().getInventory().clear();
            //backInventory(user);

            user.getPlayer().setGameMode(parseGamemode(args[0]));
            user.sendMessagePrefix("&6Modo de juego cambiado a &c" + parseGamemode(args[0]).toString());
        }

        if (args.length == 2){
            PAUser target = PAServer.getUser(args[1]);

            if (target == null || !target.isOnline()){
                userNotOnline(user);
                return;
            }
            target.getPlayer().setGameMode(parseGamemode(args[0]));
            user.sendMessagePrefix("&6Modo de juego cambiado de &2" + target.getName() + " a &c" + parseGamemode(args[0]).toString());
            target.sendMessagePrefix("&6Modo de juego cambiado a &c" + parseGamemode(args[0]).toString());
        }
    }

    private void backInventory(PAUser user){
        if (user.getPlayer().getGameMode() != GameMode.CREATIVE) return;
        Inventory inv = user.getUserData().getInventory();

        try{
            if (inv == null) {
                throw new NullInventorySaveException("Inventario no guardado al cambiar de gamemode");
            } else {
                user.getPlayer().getInventory().clear();
                user.getPlayer().getInventory().addItem(inv.getContents());
            }
        }catch (NullInventorySaveException e){
            plugin.debugLog("Causa: " + e.getCause());
        }
    }

    private GameMode parseGamemode(String gamemode){
        switch (gamemode){
            case "creativo":
            case "1":
            case "c":
                return GameMode.CREATIVE;
            case "survival":
            case "0":
            case "s":
                return GameMode.SURVIVAL;
            case "adventura":
            case "2":
            case "a":
                return GameMode.ADVENTURE;
            case "espectador":
            case "3":
            case "e":
                return GameMode.SPECTATOR;
            default:
                return GameMode.SURVIVAL;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender cs, Command cmd, String alias, String[] args, String curs, Integer curn) {
        return null;
    }
}
