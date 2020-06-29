package model.map;

import com.google.gson.annotations.Expose;
import org.json.JSONException;
import org.json.JSONObject;
import util.Common;
import util.database.DBBuiltInUtil;

public class Townhall extends Building {

    private static final String TOWNHALL_CONFIG_PATH = "config/GameStatsConfig/TownHall.json";
    private static final String TOWNHALL_CONFIG_NAME = "TOW_1";
    private static JSONObject townhallConfig;
    public static final int MAX_LEVEL = 11;

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getElixir() {
        return elixir;
    }

    public void setElixir(int elixir) {
        this.elixir = elixir;
    }

    @Expose
    private int gold;
    @Expose
    private int elixir;
    @Expose
    private int darkElixir;

    public int getGoldCapacity() {
        return goldCapacity;
    }

    public int getElixirCapacity() {
        return elixirCapacity;
    }

    private int goldCapacity;
    private int elixirCapacity;
    private int darkElixirCapacity;

    public Townhall(int id_, int x_, int y_, int level_, int buildingStatus_, int finishTime_) {
        super(id_, x_, y_, Building.TOWNHALL, level_, buildingStatus_, finishTime_);
    }

    public static Townhall createTownhall(int x_, int y_) {
        int newId = DBBuiltInUtil.generateId(collectionName);
        return new Townhall(newId, x_, y_, 1, Building.NORMAL_STATUS, 0);
    }

    private void loadConfig() {
        if(townhallConfig != null) {
            return;
        }
        townhallConfig = Common.loadJSONObjectFromFile(TOWNHALL_CONFIG_PATH);
    }

    @Override
    public void setLevel(int level) {
        loadConfig();
        if (level < 1 || level > MAX_LEVEL) {
            throw new RuntimeException("Level is invalid");
        }
        try {
            JSONObject currConfig = townhallConfig.getJSONObject(TOWNHALL_CONFIG_NAME).getJSONObject(String.valueOf(level));
            width = currConfig.getInt("width");
            height = currConfig.getInt("height");
            health = currConfig.getInt("hitpoints");
            goldToUpgrade = currConfig.getInt("gold");
            elixirToUpgrade = currConfig.getInt("elixir");
            darkElixirToUpgrade = currConfig.getInt("darkElixir");
            timeToUpgrade = currConfig.getInt("buildTime");
            goldCapacity = currConfig.getInt("capacityGold");
            elixirCapacity = currConfig.getInt("capacityElixir");
            darkElixirCapacity = currConfig.getInt("capacityDarkElixir");
            // TODO: load config other buildings condition
        } catch (JSONException e) {
            throw new RuntimeException("Townhall config is invalid");
        }

    }
}
