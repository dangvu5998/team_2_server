package model.map;

import com.google.gson.annotations.Expose;
import util.Common;

public abstract class MineBuilding extends Building {
    protected int capacity;
    protected int productionRate;
    @Expose
    protected int lastTimeCollected;

    public MineBuilding(int id_, int x_,int y_, int mapObjectType_, int level_, int buildingStatus_, int finishTime_) {
        super(id_, x_,y_, mapObjectType_, level_, buildingStatus_, finishTime_);
    }

    public int getCurrentResource() {
        int currTime = Common.currentTimeInSecond();
        return (currTime - lastTimeCollected) * productionRate;
    }

    public int getLastTimeCollected() {
        return lastTimeCollected;
    }
}
