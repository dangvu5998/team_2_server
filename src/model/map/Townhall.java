package model.map;

import com.google.gson.annotations.Expose;
import org.json.JSONException;
import org.json.JSONObject;
import util.Common;
import util.database.DBBuiltInUtil;

import java.util.HashMap;

public class Townhall extends Building implements GoldContainable, ElixirContainable {
    private int gold;
    private int elixir;
    @Expose
    private int darkElixir;
    private int goldCapacity;
    private int elixirCapacity;
    private int darkElixirCapacity;
    private HashMap<Integer, Integer> maxNumberBuilding;

    private static final String TOWNHALL_CONFIG_PATH = "conf/GameStatsConfig/TownHall.json";
    private static final String TOWNHALL_CONFIG_NAME = "TOW_1";
    private static JSONObject townhallConfig;
    public static final int MAX_LEVEL = 11;

    public int getGoldCapacity() {
        return goldCapacity;
    }

    public int getElixirCapacity() {
        return elixirCapacity;
    }

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

    public Townhall(int id_, int x_, int y_, int level_, int buildingStatus_, int finishTime_) {
        super(id_, x_, y_, Building.TOWNHALL, level_, buildingStatus_, finishTime_);
    }

    public Townhall(int id_, int x_, int y_, int level_) {
        super(id_, x_, y_, Building.TOWNHALL, level_);
    }

    public Townhall(int id_, int x_, int y_, int level_, int mode) {
        super(id_, x_, y_, Building.TOWNHALL, level_, mode);
    }

    public static Townhall createTownhall(int x_, int y_) {
        int newId = DBBuiltInUtil.generateId(collectionName);
        return new Townhall(newId, x_, y_, 1, Building.NORMAL_STATUS, 0);
    }

    private void loadConfig() {
        if(townhallConfig != null) {
            return;
        }
        try {
            townhallConfig = Common.loadJSONObjectFromFile(TOWNHALL_CONFIG_PATH);
            if (townhallConfig != null) {
                townhallConfig = townhallConfig.getJSONObject(TOWNHALL_CONFIG_NAME);
            }
        } catch (JSONException e) {
            townhallConfig = null;
        }
    }

    @Override
    public void setLevel(int level) {
        loadConfig();
        this.level = level;
        if (level < 1 || level > MAX_LEVEL) {
            throw new RuntimeException("Level is invalid");
        }
        try {
            JSONObject currConfig = townhallConfig.getJSONObject(String.valueOf(level));
            width = currConfig.getInt("width");
            height = currConfig.getInt("height");
            goldCapacity = currConfig.getInt("capacityGold");
            elixirCapacity = currConfig.getInt("capacityElixir");
            if(mode == BATTLE_MODE) {
                health = currConfig.getInt("hitpoints");
            }
            else {
                elixirToUpgrade = 0;
                if (level < MAX_LEVEL) {
                    JSONObject nextLevelConfig = townhallConfig.getJSONObject(String.valueOf(level + 1));
                    goldToUpgrade = nextLevelConfig.getInt("gold");
                    darkElixirToUpgrade = nextLevelConfig.getInt("darkElixir");
                    timeToUpgrade = nextLevelConfig.getInt("buildTime");
                }
                maxNumberBuilding = new HashMap<>();
                for (int buildingTypeId : MapObject.BUILDING_TYPES) {
                    if (buildingTypeId == MapObject.BUILDER_HUT || buildingTypeId == MapObject.TOWNHALL) {
                        continue;
                    }
                    String configBuildingName = MapObject.MAP_ID_OBJ_TO_CONFIG_NAME.get(buildingTypeId);
                    maxNumberBuilding.put(buildingTypeId, currConfig.getInt(configBuildingName));
                }
            }
        } catch (JSONException e) {
            throw new RuntimeException("Townhall config is invalid");
        }
    }

    public int getDarkElixir() {
        return darkElixir;
    }

    public int getMaxNumberBuilding(int buildingTypeId) {
        Integer res = maxNumberBuilding.get(buildingTypeId);
        if(res == null) {
            return -1;
        }
        return res;
    }

    @Override
    public int getMaxLevel() {
        return MAX_LEVEL;
    }

    @Override
    public Townhall clone() {
        if(mode == BATTLE_MODE)
            return new Townhall(this.id, this.x, this.y, this.level, this.mode);
        return new Townhall(this.id, this.x, this.y, this.level, this.status, this.finishTime);
    }
}
