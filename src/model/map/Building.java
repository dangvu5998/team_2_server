package model.map;

public abstract class Building extends MapObject {
    protected int level;
    protected int health;

    public int getLevel() {
        return level;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public int getStatus() {
        return status;
    }

    protected int finishTime;
    protected int status;
    protected int goldToUpgrade;
    protected int elixirToUpgrade;
    protected int darkElixirToUpgrade;
    protected int timeToUpgrade;

    public static final int NORMAL_STATUS = 0;
    public static final int BUILDING_STATUS = 1;
    public static final int UPGRADING_STATUS = 2;

    public Building(int id_, int x_,int y_, int mapObjectType_, int level_, int status_, int finishTime_) {
        super(id_, x_, y_, mapObjectType_);
        status = status_;
        setLevel(level_);
        finishTime = finishTime_;
    }

    public abstract void setLevel(int level);

    public boolean upgrade() {
        return false;
    }
}
