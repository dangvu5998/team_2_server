package model.map;

import util.database.DBBuiltInUtil;

public class ClanCastle extends Building {

    public ClanCastle(int id_, int x_, int y_, int level_, int buildingStatus_, int finishTime_) {
        super(id_, x_, y_, Building.CLAN_CASTLE, level_, buildingStatus_, finishTime_);
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    public static ClanCastle createClanCastle(int x_, int y_) {
        int newId = DBBuiltInUtil.generateId(collectionName);
        return new ClanCastle(newId, x_, y_, 1, Building.NORMAL_STATUS, 0);
    }
}
