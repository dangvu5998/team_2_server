package model.map;

import bitzero.util.common.business.CommonHandle;
import org.json.JSONException;
import org.json.JSONObject;
import util.Common;
import util.database.DBBuiltInUtil;

public class Barrack extends Building {
    private static final String BARRACK_CONFIG_PATH = "conf/GameStatsConfig/Barrack.json";
    private static final String BARRACK_CONFIG_NAME = "BAR_1";
    private static int timeToBuild;
    private static int elixirToBuild;
    private static JSONObject barrackConfig;
    public static final int MAX_LEVEL = 12;

    public Barrack(int id_, int x_, int y_, int level_, int buildingStatus_, int finishTime_) {
        super(id_, x_, y_, BARRACK, level_, buildingStatus_, finishTime_);
    }

    public Barrack(int id_, int x_, int y_, int level_) {
        super(id_, x_, y_, BARRACK, level_);
    }

    private void loadConfig() {
        if (barrackConfig != null) {
            return;
        }
        try {
            barrackConfig = Common.loadJSONObjectFromFile(BARRACK_CONFIG_PATH);
            if(barrackConfig != null) {
                barrackConfig = barrackConfig.getJSONObject(BARRACK_CONFIG_NAME);
            }
        } catch(JSONException e) {
            barrackConfig = null;
        }
        if (barrackConfig == null) {
            throw new RuntimeException("Cannot load barrack config");
        }
        try {
            JSONObject level1Config = barrackConfig.getJSONObject(String.valueOf(1));
            timeToBuild = level1Config.getInt("buildTime");
            elixirToBuild = level1Config.getInt("elixir");
        } catch (JSONException e) {
            CommonHandle.writeErrLog(e);
            throw new RuntimeException("Barrack config is invalid");
        }
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
        loadConfig();
        try {
            JSONObject currConfig = barrackConfig.getJSONObject(String.valueOf(level));
            width = currConfig.getInt("width");
            height = currConfig.getInt("height");
            health = currConfig.getInt("hitpoints");
            if(level < MAX_LEVEL) {
                JSONObject nextLevelConfig = barrackConfig.getJSONObject(String.valueOf(level + 1));
                elixirToUpgrade = nextLevelConfig.getInt("elixir");
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
            CommonHandle.writeErrLog(e);
            throw new RuntimeException("Barrack config is invalid");
        }
    }

    @Override
    public int getMaxLevel() {
        return MAX_LEVEL;
    }

    public static Barrack createBarrack(int x_, int y_) {
        int newId = DBBuiltInUtil.generateId(collectionName);
        return new Barrack(newId, x_, y_, 1, Building.NORMAL_STATUS, 0);
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
