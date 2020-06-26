package model.map;

import model.GameUser;
import org.json.JSONException;
import org.json.JSONObject;
import util.database.DBBuiltInUtil;

public abstract class MapObject {

    protected int id;
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected int objectType;

    public static final int ARMY_CAMP = 1;
    public static final int BARRACK = 2;
    public static final int BUILDER_HUT = 3;
    public static final int CLAN_CASTLE = 4;
    public static final int ARCHER_TOWER = 5;
    public static final int AIR_DEFENSE = 6;
    public static final int CANON = 7;
    public static final int ELIXIR_COLLECTOR = 8;
    public static final int ELIXIR_STORAGE = 9;
    public static final int LABORATORY = 10;
    public static final int TREBUCHET = 11;
    public static final int TOWNHALL = 12;
    public static final int WALL = 13;
    public static final int OBSTACLE_1 = 14;
    public static final int OBSTACLE_2 = 15;
    public static final int OBSTACLE_3 = 16;
    public static final int OBSTACLE_4 = 17;
    public static final int OBSTACLE_5 = 18;
    public static final int OBSTACLE_6 = 19;
    public static final int OBSTACLE_7 = 20;
    public static final int OBSTACLE_8 = 21;
    public static final int OBSTACLE_9 = 22;
    public static final int OBSTACLE_10 = 23;
    public static final int OBSTACLE_11 = 24;
    public static final int OBSTACLE_12 = 25;
    public static final int OBSTACLE_14 = 26;
    public static final int OBSTACLE_15 = 27;
    public static final int OBSTACLE_16 = 28;
    public static final int OBSTACLE_17 = 29;
    public static final int OBSTACLE_18 = 30;
    public static final int OBSTACLE_19 = 31;
    public static final int OBSTACLE_20 = 32;
    public static final int OBSTACLE_21 = 33;
    public static final int OBSTACLE_22 = 34;
    public static final int OBSTACLE_23 = 35;
    public static final int OBSTACLE_24 = 36;
    public static final int OBSTACLE_25 = 37;
    public static final int OBSTACLE_26 = 38;
    public static final int OBSTACLE_27 = 39;
    public static final int OBSTACLE_13 = 40;
    public static final int GOLD_STORAGE = 41;
    public static final int GOLD_COLLECTOR = 42;

    protected static final String collectionName = "MapObject";

    public MapObject(int id_, int x_, int y_, int objectType_) {
        id = id_;
        x = x_;
        y = y_;
        objectType = objectType_;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getObjectType() {
        return objectType;
    }

    public void save() {
        try {
            DBBuiltInUtil.save(collectionName, String.valueOf(id), this);
        } catch (Exception e) {
            System.out.println("Save map object" + id + " error");
        }
    }

    public static MapObject getMapObjectById(int id) {
        String mapObjectStr = DBBuiltInUtil.get(collectionName, String.valueOf(id));
        if(mapObjectStr == null) {
            return null;
        }
        try {
            JSONObject mapObjectJson = new JSONObject(mapObjectStr);
            int objectType = mapObjectJson.getInt("objectType");
            switch (objectType) {
                case TOWNHALL:
                    return DBBuiltInUtil.gson.fromJson(mapObjectStr, TownhallBuilding.class);
                case GOLD_STORAGE:
                    return DBBuiltInUtil.gson.fromJson(mapObjectStr, GoldStorageBuilding.class);
                case ELIXIR_STORAGE:
                    return DBBuiltInUtil.gson.fromJson(mapObjectStr, ElixirStorageBuilding.class);
            }

        } catch (Exception e) {
            System.out.println("Eror get map oject " + id);
        }
//        return (MapObject) DBBuiltInUtil.get(collectionName, String.valueOf(id), MapObject.class);
        return null;
    }

}
