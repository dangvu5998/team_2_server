package model.map;

public class ArmyCamp extends Building {
    public ArmyCamp(int id_, int x_,int y_, int level_, int buildingStatus_, int finishTime_) {
        super(id_, x_, y_, Building.ARMY_CAMP, level_, buildingStatus_, finishTime_);
    }

    @Override
    public void setLevel(int level) {

    }
}
