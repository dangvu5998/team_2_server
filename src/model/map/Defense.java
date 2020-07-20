package model.map;

public abstract class Defense extends Building {
    public Defense(int id_, int x_, int y_, int mapObjectType_, int level_, int buildingStatus_, int finishTime_) {
        super(id_, x_, y_, mapObjectType_, level_, buildingStatus_, finishTime_);
    }

    public Defense(int id_, int x_, int y_, int mapObjectType_, int level_) {
        super(id_, x_, y_, mapObjectType_, level_);
    }
}
