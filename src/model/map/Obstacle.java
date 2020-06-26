package model.map;

import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;
import util.Common;

public class Obstacle extends MapObject {
    protected int obstacleStatus;
    protected int finishTime;
    protected int buildTime;
    protected int goldToRemove;
    protected int elixirToRemove;
    protected int exp;

    public static final int NORMAL_STATUS = 0;
    public static final int REMOVING_STATUS = 1;
    private static final String OBSTACLE_CONFIG_PATH = "config/GameStatsConfig/Obstacle.json";

    private static JSONObject obtacleConfig;

    public Obstacle(int id_, int userId_, int x_, int y_, int objectType_, int obstacleStatus_, int finishTime_) {
        super(id_, userId_, x_, y_, objectType_);
        obstacleStatus = obstacleStatus_;
        finishTime = finishTime_;
        loadConfig();
        String obtacleConfigName = "";
        switch (objectType) {
            case OBSTACLE_1:
                obtacleConfigName = "OBS_1";
                break;
            case OBSTACLE_2:
                obtacleConfigName = "OBS_2";
                break;
            case OBSTACLE_3:
                obtacleConfigName = "OBS_3";
                break;
            case OBSTACLE_4:
                obtacleConfigName = "OBS_4";
                break;
            case OBSTACLE_5:
                obtacleConfigName = "OBS_5";
                break;
            case OBSTACLE_6:
                obtacleConfigName = "OBS_6";
                break;
            case OBSTACLE_7:
                obtacleConfigName = "OBS_7";
                break;
            case OBSTACLE_8:
                obtacleConfigName = "OBS_8";
                break;
            case OBSTACLE_9:
                obtacleConfigName = "OBS_9";
                break;
            case OBSTACLE_10:
                obtacleConfigName = "OBS_10";
                break;
            case OBSTACLE_11:
                obtacleConfigName = "OBS_11";
                break;
            case OBSTACLE_12:
                obtacleConfigName = "OBS_12";
                break;
            case OBSTACLE_13:
                obtacleConfigName = "OBS_13";
                break;
            case OBSTACLE_14:
                obtacleConfigName = "OBS_14";
                break;
            case OBSTACLE_15:
                obtacleConfigName = "OBS_15";
                break;
            case OBSTACLE_16:
                obtacleConfigName = "OBS_16";
                break;
            case OBSTACLE_17:
                obtacleConfigName = "OBS_17";
                break;
            case OBSTACLE_18:
                obtacleConfigName = "OBS_18";
                break;
            case OBSTACLE_19:
                obtacleConfigName = "OBS_19";
                break;
            case OBSTACLE_20:
                obtacleConfigName = "OBS_20";
                break;
            case OBSTACLE_21:
                obtacleConfigName = "OBS_21";
                break;
            case OBSTACLE_22:
                obtacleConfigName = "OBS_22";
                break;
            case OBSTACLE_23:
                obtacleConfigName = "OBS_23";
                break;
            case OBSTACLE_24:
                obtacleConfigName = "OBS_24";
                break;
            case OBSTACLE_25:
                obtacleConfigName = "OBS_25";
                break;
            case OBSTACLE_26:
                obtacleConfigName = "OBS_26";
                break;
            case OBSTACLE_27:
                obtacleConfigName = "OBS_27";
                break;
        }
        try {
            JSONObject currConfig = obtacleConfig.getJSONObject(obtacleConfigName).getJSONObject("1");
            width = currConfig.getInt("width");
            height = currConfig.getInt("height");
            buildTime = currConfig.getInt("buildTime");
            elixirToRemove = currConfig.getInt("elixir");
            goldToRemove = currConfig.getInt("gold");
            exp = currConfig.getInt("exp");
        } catch (JSONException e) {
            // TODO: log exception
        }
    }

    public static void loadConfig() {
        if(obtacleConfig != null) {
            return;
        }
        obtacleConfig = Common.loadJSONObjectFromFile(OBSTACLE_CONFIG_PATH);
    }

    @Override
    public Document getMetadata() {
        // TODO: override this
        return new Document();
    }
}
