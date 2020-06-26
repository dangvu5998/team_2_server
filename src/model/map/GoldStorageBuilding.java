package model.map;

import org.json.JSONException;
import org.json.JSONObject;
import util.Common;
import util.database.MongodbDatabase;

public class GoldStorageBuilding extends Building {
    private static final String GOLD_STORAGE_CONFIG_PATH = "config/GameStatsConfig/Storage.json";

    private static JSONObject goldStorageConfig;
    public static final int MAX_LEVEL = 11;

    public GoldStorageBuilding(int id_, int userId_, int x_,int y_, int level_, int buildingStatus_, int finishTime_) {
        super(id_, userId_, x_, y_, Building.GOLD_STORAGE, level_, buildingStatus_, finishTime_);
        loadConfig();
        if (level < 1 || level > MAX_LEVEL) {
            // TODO: handle exception
        }
        // TODO: load config
//        try {
//            JSONObject currConfig = townhallConfig.getJSONObject("TOW_1").getJSONObject(String.valueOf(level));
//            width = currConfig.getInt("width");
//            height = currConfig.getInt("height");
//            health = currConfig.getInt("hitpoints");
//            // TODO: complete this
//
//        } catch (JSONException e) {
//            // TODO: handle exception
//        }
    }

    public static TownhallBuilding createNewTownhallBuilding(int userId_, int x_,int y_) {
        int newId = MongodbDatabase.generateId(collectionName);
        return new TownhallBuilding(newId, userId_, x_, y_, 1, Building.NORMAL_STATUS, 0);
    }

    private void loadConfig() {
        if(goldStorageConfig != null) {
            return;
        }
        goldStorageConfig = Common.loadJSONObjectFromFile(GOLD_STORAGE_CONFIG_PATH);
    }


}
