package es.projectalpha.pa.sn;

import es.projectalpha.pa.core.PACommands;
import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.sn.cmd.SafariCMD;
import es.projectalpha.pa.sn.events.CatchMob;
import es.projectalpha.pa.sn.events.SpawnMob;
import es.projectalpha.pa.sn.files.Files;
import es.projectalpha.pa.sn.recipes.PokeEgg;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by cadox on 12/12/2016.
 */
public class SafariNet extends JavaPlugin implements Listener{

    @Getter private static SafariNet instance;

    @Getter private Economy eco;
    private PokeEgg pe = new PokeEgg();
    private Files files = new Files();

    @Getter private String prefix = Utils.colorize(PAData.SN.getPrefix());
    
    public void onEnable(){
        instance = this;
        files.setupFiles();

        registerEvents();
        registerCommands();
        pe.registerRecipe();

        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            eco = economyProvider.getProvider();
        }
    }

    private void registerEvents(){
        new CatchMob(this);
        new SpawnMob(this);
    }

    private void registerCommands(){
        PACommands.register(new SafariCMD());
    }
}
