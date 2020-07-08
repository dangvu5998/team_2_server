package model.map;

import org.json.JSONException;
import org.json.JSONObject;
import util.Common;
import util.database.DBBuiltInUtil;

public class Laboratory extends Building {

    private static final String LABORATORY_CONFIG_PATH = "conf/GameStatsConfig/Laboratory.json";
    private static final String LABORATORY_CONFIG_NAME = "LAB_1";
    private static int elixirToBuild;
    private static int timeToBuild;
    private static JSONObject laboratoryConfig;
    public static final int MAX_LEVEL = 9;

    public Laboratory(int id_, int x_, int y_, int level_, int buildingStatus_, int finishTime_) {
        super(id_, x_, y_, Building.LABORATORY, level_, buildingStatus_, finishTime_);
    }

    private void loadConfig() {
        if (laboratoryConfig != null) {
            return;
        }
        try {
            laboratoryConfig = Common.loadJSONObjectFromFile(LABORATORY_CONFIG_PATH);
            if (laboratoryConfig != null) {
                laboratoryConfig = laboratoryConfig.getJSONObject(LABORATORY_CONFIG_NAME);
            }
        } catch (JSONException e) {
            laboratoryConfig = null;
        }
        if (laboratoryConfig == null) {
            throw new RuntimeException("Cannot load laboratory config");
        }
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
        loadConfig();
        try {
            JSONObject currConfig = laboratoryConfig.getJSONObject(String.valueOf(level));
            width = currConfig.getInt("width");
            height = currConfig.getInt("height");
            health = currConfig.getInt("hitpoints");
            if(level < MAX_LEVEL) {
                JSONObject nextLevelConfig = laboratoryConfig.getJSONObject(String.valueOf(level + 1));
                goldToUpgrade = nextLevelConfig.getInt("gold");
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
            throw new RuntimeException("Laboratory config is invalid");
        }

    }

    public static Laboratory createLaboratory(int x_, int y_) {
        int newId = DBBuiltInUtil.generateId(collectionName);
        return new Laboratory(newId, x_, y_, 1, Building.NORMAL_STATUS, 0);
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
