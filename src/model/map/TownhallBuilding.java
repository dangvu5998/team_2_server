package model.map;

import model.GameUser;
import org.json.JSONException;
import org.json.JSONObject;
import org.bson.Document;
import util.Common;
import util.database.DBBuiltInUtil;
import util.database.MongodbDatabase;

public class TownhallBuilding extends Building {

    private static final String TOWNHALL_CONFIG_PATH = "config/GameStatsConfig/TownHall.json";

    private static JSONObject townhallConfig;
    public static final int MAX_LEVEL = 11;

    public TownhallBuilding(int id_, int x_,int y_, int level_, int buildingStatus_, int finishTime_) {
        super(id_, x_, y_, Building.TOWNHALL, level_, buildingStatus_, finishTime_);
        loadConfig();
        if (level < 1 || level > MAX_LEVEL) {
            // TODO: handle exception
        }
        try {
            JSONObject currConfig = townhallConfig.getJSONObject("TOW_1").getJSONObject(String.valueOf(level));
            width = currConfig.getInt("width");
            height = currConfig.getInt("height");
            health = currConfig.getInt("hitpoints");
            // TODO: complete this


        } catch (JSONException e) {
            // TODO: handle exception
        }
    }

    public static TownhallBuilding createTownhallBuilding(int x_, int y_) {
        int newId = DBBuiltInUtil.generateId(collectionName);
        return new TownhallBuilding(newId, x_, y_, 1, Building.NORMAL_STATUS, 0);
    }

    private void loadConfig() {
        if(townhallConfig != null) {
            return;
        }
        townhallConfig = Common.loadJSONObjectFromFile(TOWNHALL_CONFIG_PATH);
    }

}
