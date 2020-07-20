package model;

import bitzero.util.common.business.CommonHandle;
import com.google.gson.annotations.Expose;
import model.map.MapObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import util.Common;

import java.util.ArrayList;
import java.util.HashMap;

public class SingleBattle {
    @Expose
    private int id;
    @Expose
    private int star;
    @Expose
    private int availGold;
    @Expose
    private int availElixir;
    private ArrayList<MapObject> battleMap;

    static HashMap<Integer, ArrayList<MapObject>> ID_TO_BATTLE_MAP;
    static HashMap<Integer, Integer> ID_TO_DEFAULT_GOLD;
    static HashMap<Integer, Integer> ID_TO_DEFAULT_ELIXIR;

    public SingleBattle(int id, int star, int availGold, int availElixir) {
        if(id < 1 || id > 10) {
            throw new RuntimeException("Battle id " + id + " is invalid");
        }
        this.id = id;
        this.star = star;
        this.availGold = availGold;
        this.availElixir = availElixir;
    }

    public static void loadConfig() {
        if (ID_TO_BATTLE_MAP != null) {
            return;
        }
        ID_TO_BATTLE_MAP = new HashMap<>();
        ID_TO_DEFAULT_GOLD = new HashMap<>();
        ID_TO_DEFAULT_ELIXIR = new HashMap<>();
        try {
            for (int mapId = 1; mapId <= 10; mapId++) {
                String configFileName = mapId + ".map";
                JSONObject battleConfig = Common.loadJSONObjectFromFile(configFileName);
                if(battleConfig == null) {
                    throw new RuntimeException("Battle config is invalid");
                }
                JSONObject resourceConfig = battleConfig.getJSONObject("resourse");
                if(resourceConfig == null) {
                    throw new RuntimeException("Resource battle config is invalid");
                }
                ID_TO_DEFAULT_GOLD.put(mapId, resourceConfig.getInt("gold"));
                ID_TO_DEFAULT_ELIXIR.put(mapId, resourceConfig.getInt("elixir"));
                JSONArray houseConfig = battleConfig.getJSONArray("house");
                if(houseConfig == null) {
                    throw new RuntimeException("House battle config is invalid");
                }
                for(Object mapObjConfObj: houseConfig.getArrayList()) {
                    if(!(mapObjConfObj instanceof JSONObject)) {
                        throw new RuntimeException("House battle config is invalid");
                    }
                }
            }
        } catch (JSONException e) {
            CommonHandle.writeErrLog(e);
            ID_TO_BATTLE_MAP = null;
        }
        if (ID_TO_BATTLE_MAP == null) {
            throw new RuntimeException("Cannot load single battle map config");
        }
    }

    public void loadBattleMap() {
        loadConfig();
    }

}
