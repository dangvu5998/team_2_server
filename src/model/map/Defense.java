package model.map;

public abstract class Defense extends Building {
    protected double minRange;
    protected double maxRange;
    protected double attackSpeed;
    protected double attackRadius;
    protected double attackArea;
    protected double attackType;
    protected double dmgPerShot;

    public Defense(int id_, int x_, int y_, int mapObjectType_, int level_, int buildingStatus_, int finishTime_) {
        super(id_, x_, y_, mapObjectType_, level_, buildingStatus_, finishTime_);
    }

    public Defense(int id_, int x_, int y_, int mapObjectType_, int level_) {
        super(id_, x_, y_, mapObjectType_, level_);
    }

    public Defense(int id, int x, int y, int mapObjectType, int level, int mode) {
        super(id, x, y, mapObjectType, level, mode);
    }
}
