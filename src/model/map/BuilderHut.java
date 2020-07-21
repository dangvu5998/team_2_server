package model.map;

import org.json.JSONException;
import org.json.JSONObject;
import util.Common;
import util.database.DBBuiltInUtil;

public class BuilderHut extends Building {
    private static final String BUILDER_HUT_CONFIG_PATH = "conf/GameStatsConfig/BuilderHut.json";
    private static final String BUILDER_HUT_CONFIG_NAME = "BDH_1";
    private static JSONObject builderHutConfig;
    private int index = 1;

    public BuilderHut(int id_, int x_, int y_) {
        super(id_, x_, y_, BUILDER_HUT, 1, NORMAL_STATUS, 0);
    }

    private static void loadConfig() {
        if(builderHutConfig != null) {
            return;
        }
        builderHutConfig = Common.loadJSONObjectFromFile(BUILDER_HUT_CONFIG_PATH);
        if(builderHutConfig == null) {
            throw new RuntimeException("Cannot load builder hut config");
        }
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
        loadConfig();
        try {
            JSONObject currConfig = builderHutConfig.getJSONObject(BUILDER_HUT_CONFIG_NAME).getJSONObject(String.valueOf(level));
            width = currConfig.getInt("width");
            height = currConfig.getInt("height");
            health = currConfig.getInt("hitpoints");
        } catch (JSONException e) {
            throw new RuntimeException("Builder hut config is invalid");
        }
    }

    public void setIndex(int i) {
        if(i > 5 || i < 1) {
            throw new RuntimeException("Index builder hut is invalid");
        }
        index = i;
    }

    public static BuilderHut createBuilderHut(int x, int y) {
        int newId = DBBuiltInUtil.generateId(MapObject.collectionName);
        return new BuilderHut(newId, x, y);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getGToBuild() {
        // TODO: fix hardcode, load from json
        return 250 * index;
    }

    @Override
    public BuilderHut clone() {
        return new BuilderHut(this.id, this.x, this.y);
    }
}
