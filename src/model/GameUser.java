package model;

import model.map.*;
import util.database.DBBuiltInUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class GameUser {
    public int getId() {
        return id;
    }

    public int getVipLevel() {
        return vipLevel;
    }

    public String getUsername() {
        return username;
    }

    public int getFrameScore() {
        return frameScore;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public int getUserXP() {
        return userXP;
    }

    public int getG() {
        return g;
    }

    public boolean isMusic() {
        return music;
    }

    public boolean isSound() {
        return sound;
    }

    public boolean isNotifications() {
        return notifications;
    }

    private int id;
    private int vipLevel;
    private String username;
    private int frameScore;
    private int userLevel;
    private int userXP;
    private int gold;
    private int elixir;
    private int maxGold;
    private int maxElixir;
    private int g;
    private boolean music;
    private boolean sound;
    private boolean notifications;
    private ArrayList<Integer> mapObjectIds;

    private static String COLLECTION_NAME = "GameUser";
    private static final String USERNAME_MAP_COLLECTION_NAME = "UsernameMap_GameUser";

    public GameUser(int vipLevel, String username, int frameScore, int userLevel, int userXP,
                    int g, boolean music, boolean sound, boolean notifications, ArrayList<Integer> mapObjectIds_) {
        this.id = DBBuiltInUtil.generateId(COLLECTION_NAME);
        this.username = username;
        this.vipLevel = vipLevel;
        this.username = username;
        this.frameScore = frameScore;
        this.userLevel = userLevel;
        this.userXP = userXP;
        this.g = g;
        this.music = music;
        this.sound = sound;
        this.notifications = notifications;
        mapObjectIds = mapObjectIds_;
    }

    public GameUser(int id, int vipLevel, String username, int frameScore, int userLevel, int userXP,
                    int g, boolean music, boolean sound, boolean notifications, ArrayList<Integer> mapObjectIds_) {
        this.id = id;
        this.username = username;
        this.vipLevel = vipLevel;
        this.username = username;
        this.frameScore = frameScore;
        this.userLevel = userLevel;
        this.userXP = userXP;
        this.g = g;
        this.music = music;
        this.sound = sound;
        this.notifications = notifications;
        mapObjectIds = mapObjectIds_;
    }

    public void save() {
        DBBuiltInUtil.save(USERNAME_MAP_COLLECTION_NAME, username, id);
        DBBuiltInUtil.save(COLLECTION_NAME, String.valueOf(id), this);
    }

    public static GameUser getGeneralInfoByUsername(String username) {
        String idStr = DBBuiltInUtil.get(USERNAME_MAP_COLLECTION_NAME, username);
        return (GameUser) DBBuiltInUtil.get(COLLECTION_NAME, idStr, GameUser.class);
    }

    public static GameUser createGeneralInfoByUsername(String username) {
        GameUser gameUser = new GameUser(
                0,
                username,
                500,
                1,
                0,
                0,
                true,
                true,
                true,
                new ArrayList<>()
        );
        gameUser.initialMap();
        gameUser.save();
        return gameUser;
    }

    /**
     * Add new map object to main map of current game user
     * @param mapObject map object to add
     * @return true if map object added successful. Otherwise, return false
     */
    public boolean addMapObject(MapObject mapObject) {
        if(isMapObjectOverlap(mapObject)) {
            return false;
        }
        mapObjectIds.add(mapObject.getId());
        return true;
    }

    public void initialMap() {
//        MapObject newTownhall = TownhallBuilding.createTownhallBuilding(20, 19);
//        newTownhall.save();
//        addMapObject(newTownhall);
        MapObject goldStorageBuilding = GoldStorageBuilding.createGoldStorageBuilding(30, 19);
        goldStorageBuilding.save();
        addMapObject(goldStorageBuilding);
        MapObject goldStorageBuilding2 = GoldStorageBuilding.createGoldStorageBuilding(30, 35);
        goldStorageBuilding2.save();
        addMapObject(goldStorageBuilding2);
//        MapObject goldStorageBuilding = ElixirStorageBuilding.createGoldStorageBuilding(30, 19);
//        goldStorageBuilding.save();
//        addMapObject(goldStorageBuilding);
    }

    public static GameUser getGameUserById(int id) {
        return (GameUser) DBBuiltInUtil.get(COLLECTION_NAME, String.valueOf(id), GameUser.class);
    }

    public ArrayList<MapObject> getAllMapObjects() {
        ArrayList<MapObject> result = new ArrayList<>();
        for(int mapObjectId: mapObjectIds) {
            result.add(MapObject.getMapObjectById(mapObjectId));
        }
        return result;
    }

    /**
     * Return a 2d array has id of map objects value followed by its coordinate
     * if position has no object, it has a value of -1
     * @return 2d array
     */
    public int[][] getGridMap() {
        // TODO: load from cached
        int[][] gridMap = new int[MapObject.MAP_HEIGHT][MapObject.MAP_WIDTH];
        for(int i = 0; i < MapObject.MAP_HEIGHT; i++) {
            Arrays.fill(gridMap[i], -1);
        }
        ArrayList<MapObject> mapObjects = getAllMapObjects();
        for (MapObject mapObject: mapObjects) {
            int x = mapObject.getX();
            int y = mapObject.getY();
            for(int i = y; i < y + mapObject.getHeight(); i++) {
                for(int j = x; j < x + mapObject.getWidth(); j++) {
                    gridMap[i][j] = mapObject.getId();
                }
            }
        }
        return gridMap;
    }

    public boolean isMapObjectOverlap(MapObject mapObject, int x, int y) {
        int[][] gridMap = getGridMap();
        for(int i = y; i < y + mapObject.getHeight(); i++) {
            for(int j = x; j < x + mapObject.getWidth(); j++) {
                if(gridMap[i][j] != -1 && gridMap[i][j] != mapObject.getId()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isMapObjectOverlap(MapObject mapObject) {
        return isMapObjectOverlap(mapObject, mapObject.getX(), mapObject.getY());
    }

    public boolean moveBuilding(Building building, int x, int y) {
        if(isMapObjectOverlap(building, x, y)) {
            return false;
        }
        if(!mapObjectIds.contains(building.getId())) {
            return false;
        }
        building.setX(x);
        building.setY(y);
        building.save();
        return true;
    }

    public ArrayList<GoldStorageBuilding> getAllGoldStorageBuilding() {
        ArrayList<MapObject> mapObjects = getAllMapObjects();
        ArrayList<GoldStorageBuilding> goldStorages = new ArrayList<>();
        for(MapObject mapObject: mapObjects) {
            if(mapObject instanceof GoldStorageBuilding) {
                goldStorages.add((GoldStorageBuilding) mapObject);
            }
        }
        return goldStorages;
    }

    public int getGold() {
        return gold;
    }

    public boolean deductGold(int amount) {
        if(amount > gold) {
            return false;
        }
        return true;
    }

    public boolean addGold(int amount) {
        return false;
    }

    public boolean deductElixir(int amount) {
        if(amount > elixir) {
            return false;
        }
        return true;
    }

    public boolean addElixir(int amount) {
        return false;
    }

}
