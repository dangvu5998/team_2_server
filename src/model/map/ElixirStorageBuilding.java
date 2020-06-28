package model.map;

import org.json.JSONException;
import org.json.JSONObject;
import util.Common;
import util.database.DBBuiltInUtil;

public class ElixirStorageBuilding extends Building {
    private static final String ELIXIR_STORAGE_CONFIG_PATH = "config/GameStatsConfig/Storage.json";
    private static final String ELIXIR_STORAGE_CONFIG_NAME = "STO_1";

    private static JSONObject elixirStorageConfig;
    public static final int MAX_LEVEL = 11;

    public ElixirStorageBuilding(int id_, int x_,int y_, int level_, int buildingStatus_, int finishTime_) {
        super(id_, x_, y_, Building.ELIXIR_STORAGE, level_, buildingStatus_, finishTime_);
        loadConfig();
        if (level < 1 || level > MAX_LEVEL) {
            // TODO: handle exception
        }
        // TODO: load config
//        try {
//            JSONObject currConfig = goldStorageConfig.getJSONObject("TOW_1").getJSONObject(String.valueOf(level));
//            width = currConfig.getInt("width");
//            height = currConfig.getInt("height");
//            health = currConfig.getInt("hitpoints");
//            // TODO: complete this
//
//        } catch (JSONException e) {
//            // TODO: handle exception
//        }
    }

    @Override
    public void setLevel(int level) {

    }

    public static ElixirStorageBuilding createElixirStorageBuilding(int userId_, int x_, int y_) {
        int newId = DBBuiltInUtil.generateId(collectionName);
        return new ElixirStorageBuilding(newId, x_, y_, 1, Building.NORMAL_STATUS, 0);
    }

    private void loadConfig() {
        if(elixirStorageConfig != null) {
            return;
        }
        elixirStorageConfig = Common.loadJSONObjectFromFile(ELIXIR_STORAGE_CONFIG_PATH);
    }


}
