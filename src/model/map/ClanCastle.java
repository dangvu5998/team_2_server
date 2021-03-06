package model.map;

import bitzero.util.common.business.CommonHandle;
import org.json.JSONException;
import org.json.JSONObject;
import util.Common;
import util.database.DBBuiltInUtil;

public class ClanCastle extends Building {

    private static final String CLAN_CASTLE_CONFIG_PATH = "conf/GameStatsConfig/ClanCastle.json";
    private static final String CLAN_CASTLE_CONFIG_NAME = "CLC_1";
    private static int timeToBuild;
    private static int goldToBuild;
    private static JSONObject clanCastleConfig;
    public static final int MAX_LEVEL = 6;

    public ClanCastle(int id_, int x_, int y_, int level_, int buildingStatus_, int finishTime_) {
        super(id_, x_, y_, Building.CLAN_CASTLE, level_, buildingStatus_, finishTime_);
    }

    public ClanCastle(int id_, int x_, int y_, int level_) {
        super(id_, x_, y_, Building.CLAN_CASTLE, level_);
    }

    public ClanCastle(int id_, int x_, int y_, int level_, int mode) {
        super(id_, x_, y_, Building.CLAN_CASTLE, level_, mode);
    }

    private void loadConfig() {
        if (clanCastleConfig != null) {
            return;
        }
        clanCastleConfig = Common.loadJSONObjectFromFile(CLAN_CASTLE_CONFIG_PATH);
        try {
            if (clanCastleConfig != null) {
                clanCastleConfig = clanCastleConfig.getJSONObject(CLAN_CASTLE_CONFIG_NAME);
            }
        } catch (JSONException e) {
            clanCastleConfig = null;
        }
        if (clanCastleConfig == null) {
            throw new RuntimeException("Cannot load clan castle config");
        }
        try {
            JSONObject level1Config = clanCastleConfig.getJSONObject(String.valueOf(1));
            timeToBuild = level1Config.getInt("buildTime");
            goldToBuild = level1Config.getInt("gold");
        } catch (JSONException e) {
            CommonHandle.writeErrLog(e);
            throw new RuntimeException("Clan castle config is invalid");
        }
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
        loadConfig();
        try {
            JSONObject currConfig = clanCastleConfig.getJSONObject(String.valueOf(level));
            width = currConfig.getInt("width");
            height = currConfig.getInt("height");
            if(mode == BATTLE_MODE) {
                health = currConfig.getInt("hitpoints");
            }
            else {
                if (level < MAX_LEVEL) {
                    JSONObject nextLevelConfig = clanCastleConfig.getJSONObject(String.valueOf(level + 1));
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
            CommonHandle.writeErrLog(e);
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
    public ClanCastle clone() {
        if(mode == BATTLE_MODE)
            return new ClanCastle(this.id, this.x, this.y, this.level, this.mode);
        return new ClanCastle(this.id, this.x, this.y, this.level, this.status, this.finishTime);
    }
}
