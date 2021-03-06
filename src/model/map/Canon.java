package model.map;

import model.BattleConst;
import org.json.JSONException;
import org.json.JSONObject;
import util.Common;
import util.database.DBBuiltInUtil;

public class Canon extends Defense {
    private static final String CANON_CONFIG_PATH = "conf/GameStatsConfig/Defence.json";
    private static final String CANON_CONFIG_NAME = "DEF_1";
    private static int timeToBuild;
    private static int goldToBuild;
    private static JSONObject canonConfig;
    public static final int MAX_LEVEL = 17;

    public Canon(int id_, int x_, int y_, int level_, int buildingStatus_, int finishTime_) {
        super(id_, x_, y_, Building.CANON, level_, buildingStatus_, finishTime_);
    }

    public Canon(int id_, int x_, int y_, int level_) {
        super(id_, x_, y_, Building.CANON, level_);
    }

    public Canon(int id_, int x_, int y_, int level_, int mode) {
        super(id_, x_, y_, Building.CANON, level_, mode);
    }

    private void loadConfig() {
        if (canonConfig != null) {
            return;
        }
        try {
            canonConfig = Common.loadJSONObjectFromFile(CANON_CONFIG_PATH);
            if (canonConfig != null) {
                canonConfig = canonConfig.getJSONObject(CANON_CONFIG_NAME);
            }
        } catch (JSONException e) {
            canonConfig = null;
        }
        if (canonConfig == null) {
            throw new RuntimeException("Cannot load canon config");
        }
        try {
            JSONObject level1Config = canonConfig.getJSONObject(String.valueOf(1));
            timeToBuild = level1Config.getInt("buildTime");
            goldToBuild = level1Config.getInt("gold");
        } catch (JSONException e) {
            throw new RuntimeException("Canon config is invalid");
        }
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
        loadConfig();
        try {
            JSONObject currConfig = canonConfig.getJSONObject(String.valueOf(level));
            width = currConfig.getInt("width");
            height = currConfig.getInt("height");
            if(mode == BATTLE_MODE) {
                health = currConfig.getInt("hitpoints");
                minRange = 0;
                maxRange = 9;
                attackSpeed = 0.8;
                attackRadius = 0;
                attackArea = BattleConst.GROUND_ATTACK_AREA;
                attackType = BattleConst.RANGED_ATTACK_TYPE;
                dmgPerShot = currConfig.getDouble("damagePerShot");

                stringObjectType = "DEF_1";
                attackCharacter = BattleConst.DEFENSE_ATTACK_ALWAYS_HIT;
                target = null;
                statusBattle = Building.BATTLE_STATUS_IDLE;
                stringObjectType = MapObject.MAP_ID_OBJ_TO_CONFIG_NAME.get(this.objectType);
            }
            else {
                if (level < MAX_LEVEL) {
                    JSONObject nextLevelConfig = canonConfig.getJSONObject(String.valueOf(level + 1));
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
            throw new RuntimeException("Canon config is invalid");
        }
    }

    @Override
    public int getMaxLevel() {
        return MAX_LEVEL;
    }

    public static Canon createCanon(int x_, int y_) {
        int newId = DBBuiltInUtil.generateId(collectionName);
        return new Canon(newId, x_, y_, 1, Building.NORMAL_STATUS, 0);
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
    public Canon clone() {
        if(mode == BATTLE_MODE)
            return new Canon(this.id, this.x, this.y, this.level, this.mode);
        return new Canon(this.id, this.x, this.y, this.level, this.status, this.finishTime);
    }
}
