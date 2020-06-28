package model.map;

public class ClanCastleBuilding extends Building {

    public ClanCastleBuilding(int id_, int x_,int y_, int level_, int buildingStatus_, int finishTime_) {
        super(id_, x_, y_, Building.CLAN_CASTLE, level_, buildingStatus_, finishTime_);
    }

    @Override
    public void setLevel(int level) {

    }
}
