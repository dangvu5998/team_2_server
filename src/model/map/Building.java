package model.map;

public abstract class Building extends MapObject {
    protected int level;
    protected int health;
    protected int finishTime;
    protected int buildingStatus;
    protected int goldToUpgrade;
    protected int elixirToUpgrade;
    protected int darkElixirToUpgrade;
    protected int timeToUpgrade;

    public static final int NORMAL_STATUS = 0;
    public static final int UPGRADING_STATUS = 1;

    public Building(int id_, int x_,int y_, int mapObjectType_, int level_, int buildingStatus_, int finishTime_) {
        super(id_, x_, y_, mapObjectType_);
        buildingStatus = buildingStatus_;
        setLevel(level_);
        finishTime = finishTime_;
    }

    public abstract void setLevel(int level);

    public boolean upgrade() {
        return false;
    }
}
