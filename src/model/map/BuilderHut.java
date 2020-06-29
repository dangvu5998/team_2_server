package model.map;

import util.database.DBBuiltInUtil;

public class BuilderHut extends Building {

    public BuilderHut(int id_, int x_, int y_) {
        super(id_, x_, y_, BUILDER_HUT, 1, NORMAL_STATUS, 0);
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    public static BuilderHut createBuilderHut(int x, int y) {
        int newId = DBBuiltInUtil.generateId(MapObject.collectionName);
        return new BuilderHut(newId, x, y);
    }
}
