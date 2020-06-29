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
        try {
            JSONObject currConfig = elixirStorageConfig.getJSONObject(ELIXIR_STORAGE_CONFIG_NAME).getJSONObject(String.valueOf(level));
            width = currConfig.getInt("width");
            height = currConfig.getInt("height");
            health = currConfig.getInt("hitpoints");
            goldToUpgrade = currConfig.getInt("gold");
            elixirToUpgrade = currConfig.getInt("elixir");
            darkElixirToUpgrade = currConfig.getInt("darkElixir");
            timeToUpgrade = currConfig.getInt("buildTime");
            elixirCapacity = currConfig.getInt("capacity");
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
        elixirStorageConfig = Common.loadJSONObjectFromFile(ELIXIR_STORAGE_CONFIG_PATH);
    }


}
