package model.map;

import org.json.JSONException;
import org.json.JSONObject;
import util.Common;
import util.database.DBBuiltInUtil;

public class Wall extends Building {
    private static final String WALL_CONFIG_PATH = "conf/GameStatsConfig/Wall.json";
    private static final String WALL_CONFIG_NAME = "WAL_1";
    private static JSONObject wallConfig;
    public static final int MAX_LEVEL = 11;

    public Wall(int id_, int x_, int y_, int level_, int buildingStatus_, int finishTime_) {
        super(id_, x_, y_, Building.WALL, level_, buildingStatus_, finishTime_);
    }

    public Wall(int id_, int x_, int y_, int level_) {
        super(id_, x_, y_, WALL, level_);
    }

    public Wall(int id_, int x_, int y_, int level_, int mode) {
        super(id_, x_, y_, WALL, level_, mode);
    }

    private void loadConfig() {
        if (wallConfig != null) {
            return;
        }
        wallConfig = Common.loadJSONObjectFromFile(WALL_CONFIG_PATH);
        if (wallConfig == null) {
            throw new RuntimeException("Cannot load wall config");
        }
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
        loadConfig();
        try {
            JSONObject currConfig = wallConfig.getJSONObject(WALL_CONFIG_NAME).getJSONObject(String.valueOf(level));
            width = currConfig.getInt("width");
            height = currConfig.getInt("height");
            if(mode == BATTLE_MODE) {
                health = currConfig.getInt("hitpoints");
            }
            else {
                if (level < MAX_LEVEL) {
                    JSONObject nextLevelConfig = wallConfig.getJSONObject(WALL_CONFIG_NAME).getJSONObject(String.valueOf(level + 1));
                    goldToUpgrade = nextLevelConfig.getInt("gold");
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
            throw new RuntimeException("Wall config is invalid");
        }
    }

    @Override
    public int getMaxLevel() {
        return MAX_LEVEL;
    }

    public static Wall createWall(int x, int y) {
        int newId = DBBuiltInUtil.generateId(MapObject.collectionName);
        return new Wall(newId,  x, y, 1, Building.NORMAL_STATUS, 0);
    }

    @Override
    public Wall clone() {
        if(mode == BATTLE_MODE)
            return new Wall(this.id, this.x, this.y, this.level, this.mode);
        return new Wall(this.id, this.x, this.y, this.level, this.status, this.finishTime);
    }
}
