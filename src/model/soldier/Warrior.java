package model.soldier;

import bitzero.util.common.business.CommonHandle;
import model.BattleConst;
import org.json.JSONException;
import org.json.JSONObject;
import util.Common;

public class Warrior extends Soldier {
    private int level;
    public static final String TYPE_ID = "ARM_1";
    private static JSONObject baseConfig;
    private static JSONObject config;

    public Warrior(int id, int x, int y, int level) {
        this.id = id;
        this.x = x;
        this.y = y;
        type = TYPE_ID;
        attackType = BattleConst.MELEE_ATTACK_TYPE;
        attackArea = BattleConst.GROUND_ATTACK_AREA;
        favoriteTarget = BattleConst.NONE_FAVOR_TARGET;
        try {
            moveSpeed = getBaseConfig().getInt("moveSpeed") * BattleConst.MOVE_SPEED_FACTOR_CONFIG;
            attackSpeed = getBaseConfig().getDouble("attackSpeed");
        } catch (JSONException e) {
            CommonHandle.writeErrLog(e);
        }
        setLevel(level);
    }

    public void setLevel(int level) {
        this.level = level;
        try {
            JSONObject currConfig = getConfig().getJSONObject(String.valueOf(this.level));
            dmgPerAtk = currConfig.getDouble("damagePerAttack");
            health = currConfig.getDouble("hitpoints");
        } catch (JSONException e) {
            CommonHandle.writeErrLog(e);
        }
    }

    public static void loadConfig() {
        if(config != null && baseConfig != null) {
            return;
        }
        try {
            String CONFIG_PATH = "conf/GameStatsConfig/Troop.json";
            config = Common.loadJSONObjectFromFile(CONFIG_PATH);
            if (config != null) {
                config = config.getJSONObject(TYPE_ID);
            }
            String BASE_CONFIG_PATH = "conf/GameStatsConfig/TroopBase.json";
            baseConfig = Common.loadJSONObjectFromFile(BASE_CONFIG_PATH);
            if (baseConfig != null) {
                baseConfig = baseConfig.getJSONObject(TYPE_ID);
            }
            else {
                System.out.println("eror load base conf wa");
            }
        } catch (JSONException e) {
            config = null;
            baseConfig = null;
            CommonHandle.writeErrLog(e);
        }
    }

    public JSONObject getConfig() {
        loadConfig();
        return config;
    }

    public int getLevel() {
        return level;
    }

    public JSONObject getBaseConfig() {
        loadConfig();
        return baseConfig;
    }

}
