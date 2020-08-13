package model.map;

import com.google.gson.annotations.Expose;
import model.CanBeAttacked;
import util.Common;

public abstract class Building extends MapObject implements CanBeAttacked {
    @Expose
    protected int level;
    @Expose
    protected int finishTime;
    @Expose
    protected int status;
    protected double health;
    protected int goldToUpgrade = 0;
    protected int elixirToUpgrade = 0;
    protected int darkElixirToUpgrade = 0;
    protected int timeToUpgrade = 0;
    protected int townhallLevelToUpgrade = 0;

    public static final int NORMAL_STATUS = 0;
    public static final int BUILDING_STATUS = 1;
    public static final int UPGRADING_STATUS = 2;

    public int getLevel() {
        return level;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public int getStatus() {
        return status;
    }

    public int getGoldToUpgrade() {
        return goldToUpgrade;
    }

    public int getElixirToUpgrade() {
        return elixirToUpgrade;
    }

    public int getDarkElixirToUpgrade() {
        return 0;
    }

    public int getGToUpgrade() {
        return 0;
    }

    public int getTimeToUpgrade() {
        return timeToUpgrade;
    }

    public int getGoldToBuild() {
        return 0;
    }

    public int getElixirToBuild() {
        return 0;
    }

    public int getTimeToBuild() {
        return 0;
    }

    public int getDarkElixirToBuild() {
        return 0;
    }

    public int getGToBuild() {
        return 0;
    }

    public Building(int id_, int x_, int y_, int mapObjectType_, int level_, int status_, int finishTime_) {
        super(id_, x_, y_, mapObjectType_);
        mode = MAIN_MAP_MODE;
        status = status_;
        setLevel(level_);
        finishTime = finishTime_;
    }

    public Building(int id_, int x_, int y_, int mapObjectType_, int level_) {
        this(id_, x_, y_, mapObjectType_, level_, NORMAL_STATUS, 0);
    }

    public Building(int id, int x, int y, int mapObjectType, int level, int mode) {
        super(id, x, y, mapObjectType);
        this.mode = mode;
        status = NORMAL_STATUS;
        finishTime = 0;
        setLevel(level);
    }

    public abstract void setLevel(int level);

    public abstract int getMaxLevel();

    /**
     * Begin upgrade building
     */
    public void upgrade() {
        if(level >= getMaxLevel()) {
            return;
        }
        int timeToUpgrade = getTimeToUpgrade();
        if(timeToUpgrade > 0) {
            status = UPGRADING_STATUS;
            finishTime = Common.currentTimeInSecond() + timeToUpgrade;
        }
        save();
    }

    /**
     * Cancel upgrading building
     */
    public void cancelUpgrading() {
        status = NORMAL_STATUS;
        finishTime = 0;
        save();
    }

    /**
     * Update status of building after complete upgrade or build
     */
    @Override
    public void updateStatus() {
        if(status == NORMAL_STATUS) {
            return;
        }
        if(finishTime <= Common.currentTimeInSecond()) {
            if(status == UPGRADING_STATUS) {
                setLevel(level + 1);
            }
            status = NORMAL_STATUS;
            finishTime = 0;
        }
        save();
    }

    /**
     * Quick finish build/upgrade building
     */
    public void quickFinish() {
        if(status == NORMAL_STATUS) {
            return;
        }
        finishTime = Common.currentTimeInSecond();
        updateStatus();
    }

    public void build() {
        if(level > 1) {
            return;
        }
        setLevel(1);
        int timeToBuild = getTimeToBuild();
        if(timeToBuild > 0) {
            status = BUILDING_STATUS;
            finishTime = Common.currentTimeInSecond() + timeToBuild;
        }
    }

    public int getTownhallLevelToUpgrade() {
        return townhallLevelToUpgrade;
    }

    @Override
    public void loadExtraInfo() {
        setLevel(level);
    }

    @Override
    public void takeDamage(double dmg) {
        health -= dmg;
    }

    @Override
    public boolean isAlive() {
        return this.health > 0;
    }

    public double getHealth() {
        return health;
    }
}
