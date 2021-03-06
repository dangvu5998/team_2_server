package model.map;

import bitzero.core.J;
import com.google.gson.annotations.Expose;
import org.json.JSONException;
import org.json.JSONObject;
import util.Common;
import util.database.DBBuiltInUtil;

public class GoldStorage extends Building implements GoldContainable {
    private int gold;
    private int goldCapacity;
    private int maxGoldBattle;

    private static final String GOLD_STORAGE_CONFIG_PATH = "conf/GameStatsConfig/Storage.json";
    private static final String GOLD_STORAGE_CONFIG_NAME = "STO_1";
    private static int elixirToBuild;
    private static int timeToBuild;
    private static JSONObject goldStorageConfig;
    public static final int MAX_LEVEL = 11;

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getGoldCapacity() {
        return goldCapacity;
    }

    public GoldStorage(int id_, int x_, int y_, int level_, int buildingStatus_, int finishTime_) {
        super(id_, x_, y_, Building.GOLD_STORAGE, level_, buildingStatus_, finishTime_);
    }

    public GoldStorage(int id_, int x_, int y_, int level_) {
        super(id_, x_, y_, Building.GOLD_STORAGE, level_);
    }

    public GoldStorage(int id_, int x_, int y_, int level_, int mode) {
        super(id_, x_, y_, Building.GOLD_STORAGE, level_, mode);
    }

    @Override
    public void setLevel(int level) {
        loadConfig();
        this.level = level;
        if (level < 1 || level > MAX_LEVEL) {
            throw new RuntimeException("Gold storage level is invalid");
        }
        try {
            JSONObject currConfig = goldStorageConfig.getJSONObject(String.valueOf(level));
            width = currConfig.getInt("width");
            height = currConfig.getInt("height");
            goldCapacity = currConfig.getInt("capacity");
            if(mode == BATTLE_MODE) {
                health = currConfig.getInt("hitpoints");
            }
            else {
                if (level < MAX_LEVEL) {
                    JSONObject nextLevelConfig = goldStorageConfig.getJSONObject(String.valueOf(level + 1));
                    goldToUpgrade = nextLevelConfig.getInt("gold");
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
        try {
            goldStorageConfig = Common.loadJSONObjectFromFile(GOLD_STORAGE_CONFIG_PATH);
            if (goldStorageConfig != null) {
                goldStorageConfig = goldStorageConfig.getJSONObject(GOLD_STORAGE_CONFIG_NAME);
            }
        } catch (JSONException e) {
            goldStorageConfig = null;
        }
        if(goldStorageConfig == null) {
            throw new RuntimeException("Cannot load gold storage config");
        }
        try {
            JSONObject level1Config = goldStorageConfig.getJSONObject(String.valueOf(1));
            timeToBuild = level1Config.getInt("buildTime");
            elixirToBuild = level1Config.getInt("elixir");
        } catch (JSONException e) {
            throw new RuntimeException("Gold storage config is invalid");
        }
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
    public int getMaxGoldBattle() {
        return maxGoldBattle;
    }

    @Override
    public void setMaxGoldBattle(int maxGoldBattle) {
        this.maxGoldBattle = maxGoldBattle;
        this.setGold(maxGoldBattle);
    }

    @Override
    public GoldStorage clone() {
        if(mode == BATTLE_MODE)
            return new GoldStorage(this.id, this.x, this.y, this.level, this.mode);
        return new GoldStorage(this.id, this.x, this.y, this.level, this.status, this.finishTime);
    }

    @Override
    public void takeDamage(double dmg) {
        super.takeDamage(dmg);
        if (health < 0) {
            gold = 0;
        }
        gold = (int) Math.floor(health / maxHealth * maxGoldBattle);
    }
}
