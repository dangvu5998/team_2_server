package model.map;

import org.json.JSONException;
import org.json.JSONObject;
import util.Common;
import util.database.DBBuiltInUtil;

public class ClanCastle extends Building {

    private static final String CLAN_CASTLE_CONFIG_PATH = "config/GameStatsConfig/ClanCastle.json";
    private static final String CLAN_CASTLE_CONFIG_NAME = "CLC_1";
    private static JSONObject clanCastleConfig;
    public static final int MAX_LEVEL = 6;

    public ClanCastle(int id_, int x_, int y_, int level_, int buildingStatus_, int finishTime_) {
        super(id_, x_, y_, Building.CLAN_CASTLE, level_, buildingStatus_, finishTime_);
    }

    private void loadConfig() {
        if (clanCastleConfig != null) {
            return;
        }
        clanCastleConfig = Common.loadJSONObjectFromFile(CLAN_CASTLE_CONFIG_PATH);
        if (clanCastleConfig == null) {
            throw new RuntimeException("Cannot load clan castle config");
        }
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
        loadConfig();
        try {
            JSONObject currConfig = clanCastleConfig.getJSONObject(CLAN_CASTLE_CONFIG_NAME).getJSONObject(String.valueOf(level));
            width = currConfig.getInt("width");
            height = currConfig.getInt("height");
            health = currConfig.getInt("hitpoints");
            if(level < MAX_LEVEL) {
                JSONObject nextLevelConfig = clanCastleConfig.getJSONObject(CLAN_CASTLE_CONFIG_NAME).getJSONObject(String.valueOf(level + 1));
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
            throw new RuntimeException("Clan castle config is invalid");
        }

    }

    public static ClanCastle createClanCastle(int x_, int y_) {
        int newId = DBBuiltInUtil.generateId(collectionName);
        return new ClanCastle(newId, x_, y_, 1, Building.NORMAL_STATUS, 0);
    }

    @Override
    public int getMaxLevel() {
        return MAX_LEVEL;
    }
}
