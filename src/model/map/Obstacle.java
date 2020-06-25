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
            case OBTACLE_1:
                obtacleConfigName = "OBS_1";
                break;
            case OBTACLE_2:
                obtacleConfigName = "OBS_2";
                break;
            case OBTACLE_3:
                obtacleConfigName = "OBS_3";
                break;
            case OBTACLE_4:
                obtacleConfigName = "OBS_4";
                break;
            case OBTACLE_5:
                obtacleConfigName = "OBS_5";
                break;
            case OBTACLE_6:
                obtacleConfigName = "OBS_6";
                break;
            case OBTACLE_7:
                obtacleConfigName = "OBS_7";
                break;
            case OBTACLE_8:
                obtacleConfigName = "OBS_8";
                break;
            case OBTACLE_9:
                obtacleConfigName = "OBS_9";
                break;
            case OBTACLE_10:
                obtacleConfigName = "OBS_10";
                break;
            case OBTACLE_11:
                obtacleConfigName = "OBS_11";
                break;
            case OBTACLE_12:
                obtacleConfigName = "OBS_12";
                break;
            case OBTACLE_13:
                obtacleConfigName = "OBS_13";
                break;
            case OBTACLE_14:
                obtacleConfigName = "OBS_14";
                break;
            case OBTACLE_15:
                obtacleConfigName = "OBS_15";
                break;
            case OBTACLE_16:
                obtacleConfigName = "OBS_16";
                break;
            case OBTACLE_17:
                obtacleConfigName = "OBS_17";
                break;
            case OBTACLE_18:
                obtacleConfigName = "OBS_18";
                break;
            case OBTACLE_19:
                obtacleConfigName = "OBS_19";
                break;
            case OBTACLE_20:
                obtacleConfigName = "OBS_20";
                break;
            case OBTACLE_21:
                obtacleConfigName = "OBS_21";
                break;
            case OBTACLE_22:
                obtacleConfigName = "OBS_22";
                break;
            case OBTACLE_23:
                obtacleConfigName = "OBS_23";
                break;
            case OBTACLE_24:
                obtacleConfigName = "OBS_24";
                break;
            case OBTACLE_25:
                obtacleConfigName = "OBS_25";
                break;
            case OBTACLE_26:
                obtacleConfigName = "OBS_26";
                break;
            case OBTACLE_27:
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
