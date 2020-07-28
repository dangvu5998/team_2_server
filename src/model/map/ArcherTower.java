package model.map;

import model.BattleConst;
import org.json.JSONException;
import org.json.JSONObject;
import util.Common;
import util.database.DBBuiltInUtil;

public class ArcherTower extends Defense {
    private static final String ARCHER_TOWER_CONFIG_PATH = "conf/GameStatsConfig/Defence.json";
    private static final String ARCHER_TOWER_CONFIG_NAME = "DEF_2";
    private static JSONObject archerTowerConfig;
    private static int timeToBuild;
    private static int goldToBuild;
    public static final int MAX_LEVEL = 17;

    public ArcherTower(int id_, int x_, int y_, int level_, int buildingStatus_, int finishTime_) {
        super(id_, x_, y_, ARCHER_TOWER, level_, buildingStatus_, finishTime_);
    }

    public ArcherTower(int id_, int x_, int y_, int level_) {
        super(id_, x_, y_, ARCHER_TOWER, level_);
    }

    public ArcherTower(int id_, int x_, int y_, int level_, int mode) {
        super(id_, x_, y_, ARCHER_TOWER, level_, mode);
    }

    private void loadConfig() {
        if (archerTowerConfig != null) {
            return;
        }
        try {
            archerTowerConfig = Common.loadJSONObjectFromFile(ARCHER_TOWER_CONFIG_PATH);
            if(archerTowerConfig != null) {
                archerTowerConfig = archerTowerConfig.getJSONObject(ARCHER_TOWER_CONFIG_NAME);
            }
        } catch(JSONException e) {
            archerTowerConfig = null;
        }
        if (archerTowerConfig == null) {
            throw new RuntimeException("Cannot load archer tower config");
        }
        try {
            JSONObject level1Config = archerTowerConfig.getJSONObject(String.valueOf(1));
            timeToBuild = level1Config.getInt("buildTime");
            goldToBuild = level1Config.getInt("gold");
        } catch (JSONException e) {
            throw new RuntimeException("Archer tower config is invalid");
        }
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
        loadConfig();
        try {
            JSONObject currConfig = archerTowerConfig.getJSONObject(String.valueOf(level));
            width = currConfig.getInt("width");
            height = currConfig.getInt("height");
            if(mode == BATTLE_MODE) {
                health = currConfig.getInt("hitpoints");
                minRange = 0;
                maxRange = 10;
                attackSpeed = 1;
                attackRadius = 0;
                attackArea = BattleConst.GROUND_AERIAL_ATTACK_AREA;
                attackType = BattleConst.RANGED_ATTACK_TYPE;
                dmgPerShot = currConfig.getDouble("damagePerShot");
            }
            else {
                if (level < MAX_LEVEL) {
                    JSONObject nextLevelConfig = archerTowerConfig.getJSONObject(String.valueOf(level + 1));
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
            throw new RuntimeException("Archer tower config is invalid");
        }
    }

    @Override
    public int getMaxLevel() {
        return MAX_LEVEL;
    }

    public static ArcherTower createArcherTower(int x_, int y_) {
        int newId = DBBuiltInUtil.generateId(collectionName);
        return new ArcherTower(newId, x_, y_, 1, Building.NORMAL_STATUS, 0);
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


    @Override
    public ArcherTower clone() {
        if(mode == BATTLE_MODE)
            return new ArcherTower(this.id, this.x, this.y, this.level, this.mode);
        return new ArcherTower(this.id, this.x, this.y, this.level, this.status, this.finishTime);
    }
}
