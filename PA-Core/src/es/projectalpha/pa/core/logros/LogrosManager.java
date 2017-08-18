package es.projectalpha.pa.core.logros;

import es.projectalpha.pa.core.PACore;
import es.projectalpha.pa.core.utils.Log;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class LogrosManager {

    private PACore plugin;

    private ArrayList<Logro> logros;

    public LogrosManager(PACore instance) {
        this.plugin = instance;
        logros = new ArrayList<>();
        Log.debugLog("Ja, aun no estan preparados :D");
        //loadLogros();
    }

    private void loadLogros() {
        JSONParser parser = new JSONParser();

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new URL("http://dev.projectalpha.es/logros.json").openStream()));
            JSONObject jsonObject = (JSONObject) parser.parse(in);
            int maxLogros = Integer.parseInt(String.valueOf(jsonObject.get("logros")));

            for (int x = 0; x <= maxLogros; x++) {
                JSONObject structure = (JSONObject) jsonObject.get(String.valueOf(x));
                logros.add(new Logro(x, structure.get("nombre").toString(), structure.get("desc").toString()));
            }
        } catch (Exception e) {
            Log.log(Log.Level.SEVERE, "Error al cargar los logros");
        }
    }
}
