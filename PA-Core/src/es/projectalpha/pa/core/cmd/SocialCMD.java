package es.projectalpha.pa.core.cmd;

import es.projectalpha.pa.core.api.PAUser;

import java.util.Arrays;

public class SocialCMD extends PACmd {

    public SocialCMD(){
        super("social", Grupo.Craftero, Arrays.asList("web", "twitter", "facebook", "tienda", "foro"));
    }

    public void run(PAUser user, String label, String[] args){
        if (args.length >= 0){
            user.sendDiv();
            user.sendMessage("&cDa click para acceder a las páginas");
            user.jsonURL("&6Web", "&6Web", "http://worldcrafteros.net");
            user.jsonURL("&5Foro", "&5Foro", "http://worldcrafteros.net/foro");
            user.jsonURL("&aTienda", "&aTienda", "http://tienda.worldcrafteros.net");
            user.jsonURL("&1Facebook", "&1Facebook", "https://www.facebook.com/worldcrafteros/");
            user.jsonURL("&3Twitter", "&3Twitter", "https://twitter.com/World_Crafteros");
            user.sendDiv();
        }
    }
}
