package es.projectalpha.pa.core.logros;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import es.projectalpha.pa.core.PACore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LogrosManager {

    private PACore plugin;

    private Map<String, Object> map;
    private File f;

    private ArrayList<Logro> logros;

    public LogrosManager(PACore instance) {
        this.plugin = instance;
        logros = new ArrayList<>();
        map = new HashMap<>();
        f = new File(plugin.getDataFolder(), "logros.json");
        loadLogros();
    }

    private void loadLogros() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try {
            map = gson.fromJson(new FileReader(f), new HashMap<String, Object>().getClass());
            System.out.println(map.toString());
        } catch (FileNotFoundException e) {

        }
    }
}
