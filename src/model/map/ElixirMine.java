package model.map;

import org.json.JSONException;
import org.json.JSONObject;
import util.Common;
import util.database.DBBuiltInUtil;

public class ElixirMine extends MineBuilding implements ElixirContainable {
    private int elixir;
    private int maxElixirBattle;
    private static final String ELIXIR_MINE_CONFIG_PATH = "conf/GameStatsConfig/Resource.json";
    private static final String ELIXIR_MINE_CONFIG_NAME = "RES_2";
    private static JSONObject elixirMineConfig;
    private static int timeToBuild;
    private static int goldToBuild;
    public static final int MAX_LEVEL = 11;

    public ElixirMine(int id_, int x_,int y_, int level_, int buildingStatus_, int finishTime_) {
        super(id_, x_, y_, ELIXIR_MINE, level_, buildingStatus_, finishTime_);
    }

    public ElixirMine(int id_, int x_,int y_, int level_) {
        super(id_, x_, y_, ELIXIR_MINE, level_);
    }

    public ElixirMine(int id_, int x_,int y_, int level_, int mode) {
        super(id_, x_, y_, ELIXIR_MINE, level_, mode);
    }

    private void loadConfig() {
        if (elixirMineConfig != null) {
            return;
        }
        try {
            elixirMineConfig = Common.loadJSONObjectFromFile(ELIXIR_MINE_CONFIG_PATH);
            if(elixirMineConfig != null) {
                elixirMineConfig = elixirMineConfig.getJSONObject(ELIXIR_MINE_CONFIG_NAME);
            }
        } catch(JSONException e) {
            elixirMineConfig = null;
        }
        if (elixirMineConfig == null) {
            throw new RuntimeException("Cannot load elixir mine config");
        }
        try {
            JSONObject level1Config = elixirMineConfig.getJSONObject(String.valueOf(1));
            timeToBuild = level1Config.getInt("buildTime");
            goldToBuild = level1Config.getInt("gold");
        } catch (JSONException e) {
            throw new RuntimeException("Gold mine config is invalid");
        }
    }


    @Override
    public void setLevel(int level) {
        this.level = level;
        loadConfig();
        try {
            JSONObject currConfig = elixirMineConfig.getJSONObject(String.valueOf(level));
            width = currConfig.getInt("width");
            height = currConfig.getInt("height");
            health = currConfig.getInt("hitpoints");
            capacity = currConfig.getInt("capacity");
            productionRate = currConfig.getInt("productivity");
            if(level < MAX_LEVEL) {
                JSONObject nextLevelConfig = elixirMineConfig.getJSONObject(String.valueOf(level + 1));
                goldToUpgrade = nextLevelConfig.getInt("gold");
                timeToUpgrade = nextLevelConfig.getInt("buildTime");
                townhallLevelToUpgrade = nextLevelConfig.getInt("townHallLevelRequired");
            }
        } catch (JSONException e) {
            throw new RuntimeException("Elixir mine config is invalid");
        }
    }

    public static ElixirMine createElixirMine(int x, int y) {
        int newId = DBBuiltInUtil.generateId(collectionName);
        return new ElixirMine(newId, x, y, 1, NORMAL_STATUS, 0);
    }

    @Override
    public int getMaxLevel() {
        return MAX_LEVEL;
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
    public ElixirMine clone() {
        if(mode == BATTLE_MODE)
            return new ElixirMine(this.id, this.x, this.y, this.level, this.mode);
        return new ElixirMine(this.id, this.x, this.y, this.level, this.status, this.finishTime);
    }

    @Override
    public void setElixir(int elixir) {
        if(elixir > capacity) {
            throw new RuntimeException("Elixir exceeded capacity");
        }
        this.elixir = elixir;
    }

    @Override
    public int getElixir() {
        return elixir;
    }

    @Override
    public int getElixirCapacity() {
        return capacity;
    }

    @Override
    public int getMaxElixirBattle() {
        return maxElixirBattle;
    }

    @Override
    public void setMaxElixirBattle(int maxElixirBattle) {
        this.maxElixirBattle = maxElixirBattle;
        this.setElixir(maxElixirBattle);
    }

}
