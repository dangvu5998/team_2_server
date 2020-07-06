package model.map;

import com.google.gson.annotations.Expose;
import org.json.JSONException;
import org.json.JSONObject;
import util.Common;
import util.database.DBBuiltInUtil;

public class ElixirStorage extends Building {
    private static final String ELIXIR_STORAGE_CONFIG_PATH = "config/GameStatsConfig/Storage.json";
    private static final String ELIXIR_STORAGE_CONFIG_NAME = "STO_2";

    private static JSONObject elixirStorageConfig;
    private static int goldToBuild;
    private static int timeToBuild;
    public static final int MAX_LEVEL = 11;

    public int getElixir() {
        return elixir;
    }

    public void setElixir(int elixir) {
        this.elixir = elixir;
    }

    @Expose
    private int elixir;

    public int getElixirCapacity() {
        return elixirCapacity;
    }

    private int elixirCapacity;

    public ElixirStorage(int id_, int x_, int y_, int level_, int buildingStatus_, int finishTime_) {
        super(id_, x_, y_, Building.ELIXIR_STORAGE, level_, buildingStatus_, finishTime_);
    }

    @Override
    public void setLevel(int level) {
        loadConfig();
        if (level < 1 || level > MAX_LEVEL) {
            throw new RuntimeException("level is invalid");
        }
        this.level = level;
        try {
            JSONObject currConfig = elixirStorageConfig.getJSONObject(String.valueOf(level));
            width = currConfig.getInt("width");
            height = currConfig.getInt("height");
            health = currConfig.getInt("hitpoints");
            elixirCapacity = currConfig.getInt("capacity");
            if(level < MAX_LEVEL) {
                JSONObject nextLevelConfig = elixirStorageConfig.getJSONObject(String.valueOf(level + 1));
                goldToUpgrade = nextLevelConfig.getInt("gold");
                timeToUpgrade = nextLevelConfig.getInt("buildTime");
            }
            else {
                goldToUpgrade = 0;
                elixirToUpgrade = 0;
                darkElixirToUpgrade = 0;
                timeToUpgrade = 0;
            }
            // TODO: load config other buildings condition
        } catch (JSONException e) {
            throw new RuntimeException("elixir storage config is invalid");
        }
    }

    public static ElixirStorage createElixirStorage(int x_, int y_) {
        int newId = DBBuiltInUtil.generateId(collectionName);
        return new ElixirStorage(newId, x_, y_, 1, Building.NORMAL_STATUS, 0);
    }

    private void loadConfig() {
        if(elixirStorageConfig != null) {
            return;
        }
        try {
            elixirStorageConfig = Common.loadJSONObjectFromFile(ELIXIR_STORAGE_CONFIG_PATH);
            if (elixirStorageConfig != null) {
                elixirStorageConfig = elixirStorageConfig.getJSONObject(ELIXIR_STORAGE_CONFIG_NAME);
            }
        } catch (JSONException e) {
            elixirStorageConfig = null;
        }

        if (elixirStorageConfig == null) {
            throw new RuntimeException("Cannot load elixir storage config");
        }
        try {
            JSONObject level1Config = elixirStorageConfig.getJSONObject(String.valueOf(1));
            timeToBuild = level1Config.getInt("buildTime");
            goldToBuild = level1Config.getInt("gold");
        } catch (JSONException e) {
            throw new RuntimeException("Elixir storage config is invalid");
        }
    }

    @Override
    public int getMaxLevel() {
        return MAX_LEVEL;
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
