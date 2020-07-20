package model.map;

import org.json.JSONException;
import org.json.JSONObject;
import util.Common;
import util.database.DBBuiltInUtil;

public class AirDefense extends Defense {
    private static final String AIR_DEFENSE_CONFIG_PATH = "conf/GameStatsConfig/Defence.json";
    private static final String AIR_DEFENSE_CONFIG_NAME = "DEF_5";
    private static int timeToBuild;
    private static int goldToBuild;
    private static JSONObject airDefenseConfig;
    public static final int MAX_LEVEL = 11;

    public AirDefense(int id_, int x_, int y_, int level_, int buildingStatus_, int finishTime_) {
        super(id_, x_, y_, AIR_DEFENSE, level_, buildingStatus_, finishTime_);
    }

    public AirDefense(int id_, int x_, int y_, int level_) {
        super(id_, x_, y_, AIR_DEFENSE, level_);
    }

    private void loadConfig() {
        if (airDefenseConfig != null) {
            return;
        }
        try {
            airDefenseConfig = Common.loadJSONObjectFromFile(AIR_DEFENSE_CONFIG_PATH);
            if(airDefenseConfig != null) {
                airDefenseConfig = airDefenseConfig.getJSONObject(AIR_DEFENSE_CONFIG_NAME);
            }
        } catch(JSONException e) {
            airDefenseConfig = null;
        }
        if (airDefenseConfig == null) {
            throw new RuntimeException("Cannot load air defense config");
        }
        try {
            JSONObject level1Config = airDefenseConfig.getJSONObject(String.valueOf(1));
            timeToBuild = level1Config.getInt("buildTime");
            goldToBuild = level1Config.getInt("gold");
        } catch (JSONException e) {
            throw new RuntimeException("Air defense config is invalid");
        }
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
        loadConfig();
        try {
            JSONObject currConfig = airDefenseConfig.getJSONObject(String.valueOf(level));
            width = currConfig.getInt("width");
            height = currConfig.getInt("height");
            health = currConfig.getInt("hitpoints");
            if(level < MAX_LEVEL) {
                JSONObject nextLevelConfig = airDefenseConfig.getJSONObject(String.valueOf(level + 1));
                goldToUpgrade = nextLevelConfig.getInt("gold");
                darkElixirToUpgrade = nextLevelConfig.getInt("darkElixir");
                timeToUpgrade = nextLevelConfig.getInt("buildTime");
                townhallLevelToUpgrade = nextLevelConfig.getInt("townHallLevelRequired");
            }
            else {
                goldToUpgrade = 0;
                elixirToUpgrade = 0;
                darkElixirToUpgrade = 0;
                timeToUpgrade = 0;
            }
        } catch (JSONException e) {
            throw new RuntimeException("Air defense config is invalid");
        }
    }

    @Override
    public int getMaxLevel() {
        return MAX_LEVEL;
    }

    public static AirDefense createAirDefense(int x_, int y_) {
        int newId = DBBuiltInUtil.generateId(collectionName);
        return new AirDefense(newId, x_, y_, 1, Building.NORMAL_STATUS, 0);
    }

    @Override
    public int getGoldToBuild() {
        loadConfig();
        return goldToBuild;
    }

    @Override
    public int getTimeToBuild() {
        loadConfig();
        return timeToBuild;
    }

}
