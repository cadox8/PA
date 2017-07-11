package es.projectalpha.pa.core.cmd;

import es.projectalpha.pa.core.api.PAUser;

import java.util.Arrays;

public class SocialCMD extends PACmd {

    public SocialCMD(){
        super("social", Grupo.Usuario, Arrays.asList("web", "twitter", "tienda", "foro"));
    }

    public void run(PAUser user, String label, String[] args){
        if (args.length >= 0){
            user.sendDiv();
            user.sendMessage("&cDa click para acceder a las páginas");
            user.jsonURL("&6Web", "&6Web", "http://projectalpha.es");
            user.jsonURL("&5Foro", "&5Foro", "http://projectalpha.es/foro");
            user.jsonURL("&aTienda", "&aTienda", "http://tienda.projectalpha.es");
            user.jsonURL("&3Twitter", "&3Twitter", "https://twitter.com/ProjectAlphaSV");
            user.sendDiv();
        }
    }
}
