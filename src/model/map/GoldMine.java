package model.map;

import org.json.JSONException;
import org.json.JSONObject;
import util.Common;
import util.database.DBBuiltInUtil;

public class GoldMine extends MineBuilding {

    private static final String GOLD_MINE_CONFIG_PATH = "config/GameStatsConfig/Resource.json";
    private static final String GOLD_MINE_CONFIG_NAME = "RES_1";
    private static JSONObject goldMineConfig;
    private static int timeToBuild;
    private static int elixirToBuild;
    public static final int MAX_LEVEL = 11;

    public GoldMine(int id_, int x_,int y_, int level_, int buildingStatus_, int finishTime_) {
        super(id_, x_,y_, GOLD_MINE, level_, buildingStatus_, finishTime_);
    }

    private void loadConfig() {
        if (goldMineConfig != null) {
            return;
        }
        try {
            goldMineConfig = Common.loadJSONObjectFromFile(GOLD_MINE_CONFIG_PATH);
            if(goldMineConfig != null) {
                goldMineConfig = goldMineConfig.getJSONObject(GOLD_MINE_CONFIG_NAME);
            }
        } catch(JSONException e) {
            goldMineConfig = null;
        }
        if (goldMineConfig == null) {
            throw new RuntimeException("Cannot load gold mine config");
        }
        try {
            JSONObject level1Config = goldMineConfig.getJSONObject(String.valueOf(1));
            timeToBuild = level1Config.getInt("buildTime");
            elixirToBuild = level1Config.getInt("elixir");
        } catch (JSONException e) {
            throw new RuntimeException("Gold mine config is invalid");
        }
    }


    @Override
    public void setLevel(int level) {
        this.level = level;
        loadConfig();
        try {
            JSONObject currConfig = goldMineConfig.getJSONObject(String.valueOf(level));
            width = currConfig.getInt("width");
            height = currConfig.getInt("height");
            health = currConfig.getInt("hitpoints");
            capacity = currConfig.getInt("capacity");
            productionRate = currConfig.getInt("productivity");
            if(level < MAX_LEVEL) {
                JSONObject nextLevelConfig = goldMineConfig.getJSONObject(String.valueOf(level + 1));
                elixirToUpgrade = nextLevelConfig.getInt("elixir");
                darkElixirToUpgrade = nextLevelConfig.getInt("darkElixir");
                timeToUpgrade = nextLevelConfig.getInt("buildTime");
            }
        } catch (JSONException e) {
            throw new RuntimeException("Gold mine config is invalid");
        }
    }

    public static GoldMine createGoldMine(int x, int y) {
        int newId = DBBuiltInUtil.generateId(collectionName);
        return new GoldMine(newId, x, y, 1, NORMAL_STATUS, 0);
    }

    @Override
    public int getMaxLevel() {
        return MAX_LEVEL;
    }

    @Override
    public int getElixirToBuild() {
        loadConfig();
        return elixirToBuild;
    }

    @Override
    public int getTimeToBuild() {
        loadConfig();
        return timeToBuild;
    }
}
