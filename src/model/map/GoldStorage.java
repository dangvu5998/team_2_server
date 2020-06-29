package model.map;

import com.google.gson.annotations.Expose;
import org.json.JSONException;
import org.json.JSONObject;
import util.Common;
import util.database.DBBuiltInUtil;

public class GoldStorage extends Building {
    private static final String GOLD_STORAGE_CONFIG_PATH = "config/GameStatsConfig/Storage.json";
    private static final String GOLD_STORAGE_CONFIG_NAME = "STO_1";
    private static JSONObject goldStorageConfig;
    public static final int MAX_LEVEL = 11;

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    @Expose
    private int gold;

    public int getGoldCapacity() {
        return goldCapacity;
    }

    private int goldCapacity;


    public GoldStorage(int id_, int x_, int y_, int level_, int buildingStatus_, int finishTime_) {
        super(id_, x_, y_, Building.GOLD_STORAGE, level_, buildingStatus_, finishTime_);
    }

    @Override
    public void setLevel(int level) {
        // TODO: implement this
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
            goldToUpgrade = currConfig.getInt("gold");
            elixirToUpgrade = currConfig.getInt("elixir");
            darkElixirToUpgrade = currConfig.getInt("darkElixir");
            timeToUpgrade = currConfig.getInt("buildTime");
            goldCapacity = currConfig.getInt("capacity");
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
    }
}
