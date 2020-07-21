package model.map;

import org.json.JSONException;
import org.json.JSONObject;
import util.Common;
import util.database.DBBuiltInUtil;

public class Trebuchet extends Defense {
    private static final String TREBUCHET_CONFIG_PATH = "conf/GameStatsConfig/Defence.json";
    private static final String TREBUCHET_CONFIG_NAME = "DEF_3";
    private static int timeToBuild;
    private static int goldToBuild;
    private static JSONObject trebuchetConfig;
    public static final int MAX_LEVEL = 11;

    public Trebuchet(int id_, int x_, int y_, int level_, int buildingStatus_, int finishTime_) {
        super(id_, x_, y_, TREBUCHET, level_, buildingStatus_, finishTime_);
    }

    public Trebuchet(int id_, int x_, int y_, int level_) {
        super(id_, x_, y_, TREBUCHET, level_);
    }

    private void loadConfig() {
        if (trebuchetConfig != null) {
            return;
        }
        try {
            trebuchetConfig = Common.loadJSONObjectFromFile(TREBUCHET_CONFIG_PATH);
            if(trebuchetConfig != null) {
                trebuchetConfig = trebuchetConfig.getJSONObject(TREBUCHET_CONFIG_NAME);
            }
        } catch(JSONException e) {
            trebuchetConfig = null;
        }
        if (trebuchetConfig == null) {
            throw new RuntimeException("Cannot load trebuchet config");
        }
        try {
            JSONObject level1Config = trebuchetConfig.getJSONObject(String.valueOf(1));
            timeToBuild = level1Config.getInt("buildTime");
            goldToBuild = level1Config.getInt("gold");
        } catch (JSONException e) {
            throw new RuntimeException("Archer tower config is invalid");
        }
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
        loadConfig();
        try {
            JSONObject currConfig = trebuchetConfig.getJSONObject(String.valueOf(level));
            width = currConfig.getInt("width");
            height = currConfig.getInt("height");
            health = currConfig.getInt("hitpoints");
            if(level < MAX_LEVEL) {
                JSONObject nextLevelConfig = trebuchetConfig.getJSONObject(String.valueOf(level + 1));
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
            throw new RuntimeException("Trebuchet config is invalid");
        }
    }

    @Override
    public int getMaxLevel() {
        return MAX_LEVEL;
    }

    public static Trebuchet createTrebuchet(int x_, int y_) {
        int newId = DBBuiltInUtil.generateId(collectionName);
        return new Trebuchet(newId, x_, y_, 1, Building.NORMAL_STATUS, 0);
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


    @Override
    public Trebuchet clone() {
        return new Trebuchet(this.id, this.x, this.y, this.level, this.status, this.finishTime);
    }
}
