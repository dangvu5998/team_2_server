package model.map;

import org.bson.Document;
import org.json.JSONObject;

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

    public Building(int id_, int userId_, int x_,int y_, int mapObjectType_, int level_, int buildingStatus_, int finishTime_) {
        super(id_, userId_, x_, y_, mapObjectType_);
        buildingStatus = buildingStatus_;
        level = level_;
        finishTime = finishTime_;
    }

    @Override
    public Document getMetadata() {
        Document metadata = new Document();
        metadata.append("status", buildingStatus);
        metadata.append("level", level);
        metadata.append("finishTime", finishTime);
        return metadata;
    }
}
