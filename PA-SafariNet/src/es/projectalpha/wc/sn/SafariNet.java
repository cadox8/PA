package es.projectalpha.wc.sn;

import es.projectalpha.pa.core.PACommands;
import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.wc.sn.cmd.SafariCMD;
import es.projectalpha.wc.sn.events.CatchMob;
import es.projectalpha.wc.sn.events.SpawnMob;
import es.projectalpha.wc.sn.files.Files;
import es.projectalpha.wc.sn.recipes.PokeEgg;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.event.Listener;
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

    @Getter private String prefix = PAData.NS.getPrefix();
    
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
