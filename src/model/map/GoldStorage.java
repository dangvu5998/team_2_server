package model.map;

import com.google.gson.annotations.Expose;
import org.json.JSONException;
import org.json.JSONObject;
import util.Common;
import util.database.DBBuiltInUtil;

public class GoldStorage extends Building {
    @Expose
    private int gold;
    private int goldCapacity;

    private static final String GOLD_STORAGE_CONFIG_PATH = "config/GameStatsConfig/Storage.json";
    private static final String GOLD_STORAGE_CONFIG_NAME = "STO_1";
    private static int elixirToBuild;
    private static int timeToBuild;
    private static JSONObject goldStorageConfig;
    public static final int MAX_LEVEL = 11;

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getGoldCapacity() {
        return goldCapacity;
    }

    public GoldStorage(int id_, int x_, int y_, int level_, int buildingStatus_, int finishTime_) {
        super(id_, x_, y_, Building.GOLD_STORAGE, level_, buildingStatus_, finishTime_);
    }

    @Override
    public void setLevel(int level) {
        loadConfig();
        this.level = level;
        if (level < 1 || level > MAX_LEVEL) {
            throw new RuntimeException("Gold storage level is invalid");
        }
        try {
            JSONObject currConfig = goldStorageConfig.getJSONObject(GOLD_STORAGE_CONFIG_NAME).getJSONObject(String.valueOf(level));
            width = currConfig.getInt("width");
            height = currConfig.getInt("height");
            health = currConfig.getInt("hitpoints");
            goldCapacity = currConfig.getInt("capacity");
            if(level < MAX_LEVEL) {
                JSONObject nextLevelConfig = goldStorageConfig.getJSONObject(GOLD_STORAGE_CONFIG_NAME).getJSONObject(String.valueOf(level + 1));
                goldToUpgrade = nextLevelConfig.getInt("gold");
                elixirToUpgrade = nextLevelConfig.getInt("elixir");
                darkElixirToUpgrade = nextLevelConfig.getInt("darkElixir");
                timeToUpgrade = nextLevelConfig.getInt("buildTime");
            }
            else {
                goldToUpgrade = 0;
                elixirToUpgrade = 0;
                darkElixirToUpgrade = 0;
                timeToUpgrade = 0;
            }
        } catch (JSONException e) {
            throw new RuntimeException("Gold storage config is invalid");
        }
    }

    public static GoldStorage createGoldStorage(int x_, int y_) {
        int newId = DBBuiltInUtil.generateId(MapObject.collectionName);
        return new GoldStorage(newId, x_, y_, 1, Building.NORMAL_STATUS, 0);
    }

    private void loadConfig() {
        if(goldStorageConfig != null) {
            return;
        }
        goldStorageConfig = Common.loadJSONObjectFromFile(GOLD_STORAGE_CONFIG_PATH);
        if(goldStorageConfig == null) {
            throw new RuntimeException("Cannot load army camp config");
        }
        try {
            JSONObject level1Config = goldStorageConfig.getJSONObject(GOLD_STORAGE_CONFIG_NAME).getJSONObject(String.valueOf(1));
            timeToBuild = level1Config.getInt("buildTime");
            elixirToBuild = level1Config.getInt("elixir");
        } catch (JSONException e) {
            throw new RuntimeException("Gold storage config is invalid");
        }
    }

    @Override
    public int getMaxLevel() {
        return MAX_LEVEL;
    }

    @Override
    public int getElixirToBuild() {
        return elixirToBuild;
    }

    @Override
    public int getTimeToBuild() {
        return timeToBuild;
    }
}
