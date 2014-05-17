package de.hhu.propra14.team132.gameSystem;

import com.google.gson.Gson;
import de.hhu.propra14.team132.gameMechanics.Map;

import java.io.FileWriter;

/**
 * Created by isabel on 17.05.14.
 */
public class SaveManager {
    public SaveManager(Map map, String path) {
        try {
            Gson gson = new Gson();
            FileWriter fw = new FileWriter(path);
            gson.toJson(map, fw);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
