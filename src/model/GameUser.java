package model;

import com.google.gson.annotations.Expose;
import model.map.*;
import org.json.JSONException;
import org.json.JSONObject;
import util.Common;
import util.database.DBBuiltInUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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

    @Expose
    private int id;
    @Expose
    private int vipLevel;
    @Expose
    private String username;
    @Expose
    private int frameScore;
    @Expose
    private int userLevel;
    @Expose
    private int userXP;

    private static final String INIT_GAME_CONFIG_PATH = "config/GameStatsConfig/InitGame.json";
    private static JSONObject initGameConfig;

    public void setGold(int gold) {
        this.gold = gold;
        // distribute to townhall and gold storage
        Townhall townhall = getTownhallBuilding();
        if(townhall == null) {
            return;
        }
        int townhallGoldCapacity = townhall.getGoldCapacity();
        ArrayList<GoldStorage> goldStorages = getAllGoldStorageBuilding();
        if(gold > townhallGoldCapacity) {
            gold -= townhallGoldCapacity;
            townhall.setGold(townhallGoldCapacity);
        }
        else {
            townhall.setGold(gold);
            gold = 0;
        }
        townhall.save();
        for(GoldStorage goldStorage : goldStorages) {
            int goldStorageCapacity = goldStorage.getGoldCapacity();
            if(gold > goldStorageCapacity) {
                goldStorage.setGold(goldStorageCapacity);
                gold -= goldStorageCapacity;
            } else {
                goldStorage.setGold(gold);
                gold = 0;
            }
            goldStorage.save();
        }
    }

    public void setElixir(int elixir) {
        this.elixir = elixir;
        // distribute to townhall and gold storage
        Townhall townhall = getTownhallBuilding();
        if(townhall == null) {
            return;
        }
        int townhallElixirCapacity = townhall.getElixirCapacity();
        ArrayList<ElixirStorage> elixirStorages = getAllElixirStorageBuilding();
        if(elixir > townhallElixirCapacity) {
            elixir -= townhallElixirCapacity;
            townhall.setGold(townhallElixirCapacity);
        }
        else {
            townhall.setElixir(elixir);
            elixir = 0;
        }
        townhall.save();
        for(ElixirStorage elixirStorage : elixirStorages) {
            int elixirStorageCapacity = elixirStorage.getElixirCapacity();
            if(elixir > elixirStorageCapacity) {
                elixirStorage.setElixir(elixirStorageCapacity);
                elixir -= elixirStorageCapacity;
            } else {
                elixirStorage.setElixir(elixir);
                elixir = 0;
            }
            elixirStorage.save();
        }
    }

    private int gold;
    private int elixir;
    private int goldCapacity;
    private int elixirCapacity;

    public void setG(int g) {
        this.g = g;
    }

    @Expose
    private int g;
    @Expose
    private boolean music;
    @Expose
    private boolean sound;
    @Expose
    private boolean notifications;
    @Expose
    private final ArrayList<Integer> mapObjectIds;

    private static final String COLLECTION_NAME = "GameUser";
    private static final String USERNAME_MAP_COLLECTION_NAME = "UsernameMap_GameUser";

    public GameUser(int vipLevel, String username, int frameScore, int userLevel, int userXP,
                    int g, boolean music, boolean sound, boolean notifications, ArrayList<Integer> mapObjectIds_) {
        this(DBBuiltInUtil.generateId(COLLECTION_NAME), vipLevel, username, frameScore, userLevel, userXP,
         g, music, sound, notifications, mapObjectIds_);
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
        loadGold();
        loadElixir();
    }

    public void save() {
        DBBuiltInUtil.save(USERNAME_MAP_COLLECTION_NAME, username, id);
        DBBuiltInUtil.save(COLLECTION_NAME, String.valueOf(id), this);
    }

    public static GameUser getGeneralInfoByUsername(String username) {
        String idStr = DBBuiltInUtil.get(USERNAME_MAP_COLLECTION_NAME, username);
        return (GameUser) DBBuiltInUtil.get(COLLECTION_NAME, idStr, GameUser.class);
    }

    public static GameUser createGameUserByUsername(String username) {
        GameUser gameUser = new GameUser(
                0,
                username,
                0,
                1,
                0,
                0,
                true,
                true,
                true,
                new ArrayList<>()
        );
        gameUser.initGame();
        gameUser.save();
        return gameUser;
    }

    /**
     * Add new map object to main map of current game user
     * @param mapObject map object to add
     * @return true if map object added successful. Otherwise, return false
     */
    public boolean addMapObject(MapObject mapObject) {
        if(mapObject == null) {
            return false;
        }
        if(isMapObjectOverlap(mapObject)) {
            return false;
        }
        mapObjectIds.add(mapObject.getId());
        save();
        return true;
    }

    public void initGame() {
        initGameConfig = Common.loadJSONObjectFromFile(INIT_GAME_CONFIG_PATH);
        if(initGameConfig == null) {
            throw new RuntimeException("init config file is invalid or not found");
        }
        try {
            JSONObject initMapConfig = initGameConfig.getJSONObject("map");
            for (Iterator it = initMapConfig.keys(); it.hasNext(); ) {
                String buildingConfigName = (String) it.next();
                JSONObject buildingConfig = initMapConfig.getJSONObject(buildingConfigName);
                int x = buildingConfig.getInt("posX");
                int y = buildingConfig.getInt("posY");
                MapObject newMapObject = MapObject.createMapObject(MapObject.MAP_OBJ_CONFIG_NAME_TO_ID.get(buildingConfigName), x, y);
                if(newMapObject != null) {
                    newMapObject.save();
                    addMapObject(newMapObject);
                }
            }
        } catch (JSONException e) {
            throw new RuntimeException("Init config is invalid");
        }

    }

    public static GameUser getGameUserById(int id) {
        return (GameUser) DBBuiltInUtil.get(COLLECTION_NAME, String.valueOf(id), GameUser.class);
    }

    public ArrayList<MapObject> getAllMapObjects() {
        ArrayList<MapObject> result = new ArrayList<>();
        for(int mapObjectId: mapObjectIds) {
            MapObject mapObj = MapObject.getMapObjectById(mapObjectId);
            if(mapObj != null) {
                result.add(MapObject.getMapObjectById(mapObjectId));
            } else {
                // TODO: warning mapObjId of user is invalid
                System.out.println("Map object id " + mapObjectId + " is invalid!");
            }
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
        mapObject = null;

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

    public ArrayList<ElixirStorage> getAllElixirStorageBuilding() {
        ArrayList<MapObject> mapObjects = getAllMapObjects();
        ArrayList<ElixirStorage> elixirStorages = new ArrayList<>();
        for(MapObject mapObject: mapObjects) {
            if(mapObject instanceof ElixirStorage) {
                elixirStorages.add((ElixirStorage) mapObject);
            }
        }
        return elixirStorages;
    }

    public ArrayList<GoldStorage> getAllGoldStorageBuilding() {
        ArrayList<MapObject> mapObjects = getAllMapObjects();
        ArrayList<GoldStorage> goldStorages = new ArrayList<>();
        for(MapObject mapObject: mapObjects) {
            if(mapObject instanceof GoldStorage) {
                goldStorages.add((GoldStorage) mapObject);
            }
        }
        return goldStorages;
    }

    public int getGold() {
        return gold;
    }

    public void loadGold() {
        int amount = 0;
        int capacity = 0;
        Townhall townhall = getTownhallBuilding();
        if(townhall == null) {
            return;
        }
        capacity += townhall.getGoldCapacity();
        for(GoldStorage goldStorage : getAllGoldStorageBuilding()) {
            amount += goldStorage.getGold();
            capacity += goldStorage.getGoldCapacity();
        }
        amount += townhall.getGold();
        gold = amount;
        goldCapacity = capacity;
    }

    public boolean deductGold(int amount) {
        if(amount > gold) {
            return false;
        }
        setGold(gold - amount);
        return true;
    }

    public boolean addGold(int amount) {
        if(amount + gold > goldCapacity) {
            return false;
        }
        setGold(amount + gold);
        return true;
    }

    public boolean deductElixir(int amount) {
        if(amount > elixir) {
            return false;
        }
        setElixir(elixir - amount);
        return true;
    }

    public boolean addElixir(int amount) {
        if(amount + elixir > elixirCapacity) {
            return false;
        }
        setElixir(elixir + amount);
        return true;
    }

    public void loadElixir() {
        int amount = 0;
        int capacity = 0;
        Townhall townhall = getTownhallBuilding();
        if(townhall == null) {
            return;
        }
        amount += townhall.getElixir();
        capacity += townhall.getElixirCapacity();
        for(ElixirStorage elixirStorage : getAllElixirStorageBuilding()) {
            amount += elixirStorage.getElixir();
            capacity += elixirStorage.getElixirCapacity();
        }
        elixir = amount;
        elixirCapacity = capacity;
    }

    public Townhall getTownhallBuilding() {
        ArrayList<MapObject> mapObjects = getAllMapObjects();
        Townhall townhall = null;
        for(MapObject mapObject: mapObjects) {
            if(mapObject instanceof Townhall) {
                townhall = (Townhall) mapObject;
            }
        }
        return townhall;
    }
}
