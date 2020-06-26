package model.map;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;
import util.database.MongodbDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class MapObject {

    protected int id;
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected int objectType;
    protected int userId;

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

    public MapObject(int id_, int userId_, int x_, int y_, int objectType_) {
        id = id_;
        userId = userId_;
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

    public abstract Document getMetadata();

    public void save() {
        Document doc = new Document();
        doc.append("_id", id);
        doc.append("x", x);
        doc.append("y", y);
        doc.append("objectType", objectType);
        doc.append("userId", userId);
        doc.append("metadata", getMetadata());
        getCollection().replaceOne(new Document("_id", id), doc, new ReplaceOptions().upsert(true));
    }

    public static MongoCollection<Document> getCollection() {
        return MongodbDatabase.getInstance().getDatabase().getCollection(collectionName);
    }

    public static void initialMapForUser(int userId) {
        TownhallBuilding.createNewTownhallBuilding(userId, 20, 19).save();
    }

//    public static ArrayList<MapObject> getAllMapObjectsByUserId(int userId) {
//        ArrayList<Document> mapObjects = getCollection().find(new Document("userId", userId));
//        return mapObjects;
//    }

}
