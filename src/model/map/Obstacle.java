package model.map;

import com.google.gson.annotations.Expose;
import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;
import util.Common;
import util.database.DBBuiltInUtil;

public class Obstacle extends MapObject {
    @Expose
    protected int status;
    @Expose
    protected int finishTime;
    @Expose
    protected int buildTime;
    @Expose
    protected int goldToRemove;
    @Expose
    protected int elixirToRemove;
    @Expose
    protected int exp;

    public static final int NORMAL_STATUS = 0;
    public static final int REMOVING_STATUS = 1;
    private static final String OBSTACLE_CONFIG_PATH = "config/GameStatsConfig/Obstacle.json";

    private static JSONObject obtacleConfig;

    public Obstacle(int id_, int x_, int y_, int objectType_, int obstacleStatus_, int finishTime_) {
        super(id_, x_, y_, objectType_);
        status = obstacleStatus_;
        finishTime = finishTime_;
        loadConfig();
        String obtacleConfigName = switch (objectType) {
            case OBSTACLE_1 -> "OBS_1";
            case OBSTACLE_2 -> "OBS_2";
            case OBSTACLE_3 -> "OBS_3";
            case OBSTACLE_4 -> "OBS_4";
            case OBSTACLE_5 -> "OBS_5";
            case OBSTACLE_6 -> "OBS_6";
            case OBSTACLE_7 -> "OBS_7";
            case OBSTACLE_8 -> "OBS_8";
            case OBSTACLE_9 -> "OBS_9";
            case OBSTACLE_10 -> "OBS_10";
            case OBSTACLE_11 -> "OBS_11";
            case OBSTACLE_12 -> "OBS_12";
            case OBSTACLE_13 -> "OBS_13";
            case OBSTACLE_14 -> "OBS_14";
            case OBSTACLE_15 -> "OBS_15";
            case OBSTACLE_16 -> "OBS_16";
            case OBSTACLE_17 -> "OBS_17";
            case OBSTACLE_18 -> "OBS_18";
            case OBSTACLE_19 -> "OBS_19";
            case OBSTACLE_20 -> "OBS_20";
            case OBSTACLE_21 -> "OBS_21";
            case OBSTACLE_22 -> "OBS_22";
            case OBSTACLE_23 -> "OBS_23";
            case OBSTACLE_24 -> "OBS_24";
            case OBSTACLE_25 -> "OBS_25";
            case OBSTACLE_26 -> "OBS_26";
            case OBSTACLE_27 -> "OBS_27";
            default -> throw new RuntimeException("obstacle object type is invalid");
        };
        try {
            JSONObject currConfig = obtacleConfig.getJSONObject(obtacleConfigName).getJSONObject("1");
            width = currConfig.getInt("width");
            height = currConfig.getInt("height");
            buildTime = currConfig.getInt("buildTime");
            elixirToRemove = currConfig.getInt("elixir");
            goldToRemove = currConfig.getInt("gold");
            exp = currConfig.getInt("exp");
        } catch (JSONException e) {
            throw new RuntimeException("obtacle config is invalid");
        }
    }

    public static void loadConfig() {
        if(obtacleConfig != null) {
            return;
        }
        obtacleConfig = Common.loadJSONObjectFromFile(OBSTACLE_CONFIG_PATH);
    }

    public static Obstacle createObtacle(int objType, int x, int y) {
        int newId = DBBuiltInUtil.generateId(collectionName);
        return new Obstacle(newId, x, y, objType, NORMAL_STATUS, 0);
    }

    public int getStatus() {
        return status;
    }

    public int getFinishTime() {
        return finishTime;
    }
}
