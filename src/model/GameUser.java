package model;

import model.map.ElixirStorageBuilding;
import model.map.GoldStorageBuilding;
import model.map.TownhallBuilding;
import util.database.DBBuiltInUtil;
import model.map.MapObject;

import java.util.ArrayList;

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

    public void addMapObject(MapObject mapObject) {
        mapObjectIds.add(mapObject.getId());
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

}
