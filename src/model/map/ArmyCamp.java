package model.map;

import bitzero.util.common.business.CommonHandle;
import org.json.JSONException;
import org.json.JSONObject;
import util.Common;
import util.database.DBBuiltInUtil;

public class ArmyCamp extends Building {
    private int capacity;
    private static int timeToBuild;
    private static int elixirToBuild;
    public static final int MAX_LEVEL = 8;
    private static final String ARMY_CAMP_CONFIG_PATH = "conf/GameStatsConfig/ArmyCamp.json";
    private static final String ARMY_CAMP_CONFIG_NAME = "AMC_1";
    private static JSONObject armyCampConfig;
    public ArmyCamp(int id_, int x_,int y_, int level_, int buildingStatus_, int finishTime_) {
        super(id_, x_, y_, Building.ARMY_CAMP, level_, buildingStatus_, finishTime_);
    }

    public ArmyCamp(int id_, int x_,int y_, int level_) {
        super(id_, x_, y_, Building.ARMY_CAMP, level_);
    }

    public ArmyCamp(int id_, int x_,int y_, int level_, int mode) {
        super(id_, x_, y_, Building.ARMY_CAMP, level_, mode);
    }

    private static void loadConfig() {
        if(armyCampConfig != null) {
            return;
        }
        try {
            armyCampConfig = Common.loadJSONObjectFromFile(ARMY_CAMP_CONFIG_PATH);
            if (armyCampConfig != null) {
                armyCampConfig = armyCampConfig.getJSONObject(ARMY_CAMP_CONFIG_NAME);
            }
        } catch (JSONException e) {
            armyCampConfig = null;
        }
        if(armyCampConfig == null) {
            throw new RuntimeException("Cannot load army camp config");
        }
        try {
            JSONObject level1Config = armyCampConfig.getJSONObject(String.valueOf(1));
            timeToBuild = level1Config.getInt("buildTime");
            elixirToBuild = level1Config.getInt("elixir");
        } catch (JSONException e) {
            throw new RuntimeException("Army camp config is invalid");
        }
    }

    @Override
    public void setLevel(int level) {
        loadConfig();
        this.level = level;
        if (level < 1 || level > MAX_LEVEL) {
            throw new RuntimeException("Army camp level is invalid");
        }
        try {
            JSONObject currConfig = armyCampConfig.getJSONObject(String.valueOf(level));
            width = currConfig.getInt("width");
            height = currConfig.getInt("height");
            if(mode == BATTLE_MODE) {
                health = currConfig.getInt("hitpoints");
            }
            else {
                capacity = currConfig.getInt("capacity");
                if (level < MAX_LEVEL) {
                    JSONObject nextLevelConfig = armyCampConfig.getJSONObject(String.valueOf(level + 1));
                    elixirToUpgrade = nextLevelConfig.getInt("elixir");
                    darkElixirToUpgrade = nextLevelConfig.getInt("darkElixir");
                    timeToUpgrade = nextLevelConfig.getInt("buildTime");
                    townhallLevelToUpgrade = nextLevelConfig.getInt("townHallLevelRequired");
                } else {
                    goldToUpgrade = 0;
                    elixirToUpgrade = 0;
                    darkElixirToUpgrade = 0;
                    timeToUpgrade = 0;
                }
            }
        } catch (JSONException e) {
            CommonHandle.writeErrLog(e);
            throw new RuntimeException("Army camp config is invalid");
        }
    }

    public static ArmyCamp createArmyCamp(int x_, int y_) {
        int newId = DBBuiltInUtil.generateId(collectionName);
        return new ArmyCamp(newId, x_, y_, 1, Building.NORMAL_STATUS, 0);
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

    @Override
    public ArmyCamp clone() {
        if(mode == BATTLE_MODE)
            return new ArmyCamp(this.id, this.x, this.y, this.level, this.mode);
        return new ArmyCamp(this.id, this.x, this.y, this.level, this.status, this.finishTime);
    }

    public int getCapacity() {
        return capacity;
    }
}
