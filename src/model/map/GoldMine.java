package model.map;

import util.database.DBBuiltInUtil;

public class GoldMine extends MineBuilding {

    public static final int MAX_LEVEL = 11;

    public GoldMine(int id_, int x_,int y_, int mapObjectType_, int level_, int buildingStatus_, int finishTime_) {
        super(id_, x_,y_, mapObjectType_, level_, buildingStatus_, finishTime_);
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
        capacity = 0;
        productionRate = 0;
    }

    public static GoldMine createGoldMine(int x, int y) {
        int newId = DBBuiltInUtil.generateId(collectionName);
        return new GoldMine(newId, x, y, GOLD_MINE, 1, NORMAL_STATUS, 0);
    }

    @Override
    public int getMaxLevel() {
        return MAX_LEVEL;
    }
}
