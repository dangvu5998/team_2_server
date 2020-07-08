package model;

import cmd.send.mainmap.*;
import com.google.gson.annotations.Expose;
import model.map.*;
import org.json.JSONException;
import org.json.JSONObject;
import util.Common;
import util.database.DBBuiltInUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class GameUser {
    @Expose
    private final int id;
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
    private int gold;
    private int elixir;
    private int goldCapacity;
    private int elixirCapacity;
    private int nbOfAvaiBuilder;
    private int nbOfBuilder;

    private static JSONObject initGameConfig;

    private static final String COLLECTION_NAME = "GameUser";
    private static final String USERNAME_MAP_COLLECTION_NAME = "UsernameMap_GameUser";
    private static final String INIT_GAME_CONFIG_PATH = "conf/GameStatsConfig/InitGame.json";

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

    public int getNbOfBuilder() {
        return nbOfBuilder;
    }

    public int getNbOfAvaiBuilder() {
        return nbOfAvaiBuilder;
    }

    public void deductAvaiBuilder() {
        if(nbOfAvaiBuilder <= 0) {
            throw new RuntimeException("Game user " + id + " has no available builers");
        }
        nbOfAvaiBuilder -= 1;
    }

    public void addAvaiBuilder() {
        nbOfAvaiBuilder += 1;
    }

    private void loadBuilders() {
        ArrayList<MapObject> mapObjects = getAllMapObjects();
        for(MapObject mapObject: mapObjects) {
            if(mapObject instanceof BuilderHut) {
                nbOfBuilder += 1;
                nbOfAvaiBuilder += 1;
            }
            if(mapObject instanceof Building) {
                Building building = (Building) mapObject;
                if(building.getStatus() == Building.BUILDING_STATUS ||
                building.getStatus() == Building.UPGRADING_STATUS) {
                    nbOfAvaiBuilder -= 1;
                }
            }
        }
    }

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
            if(goldStorage.getStatus() == Building.BUILDING_STATUS) {
                continue;
            }
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

    public int getElixir() {
        return elixir;
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
            townhall.setElixir(townhallElixirCapacity);
        }
        else {
            townhall.setElixir(elixir);
            elixir = 0;
        }
        townhall.save();
        for(ElixirStorage elixirStorage : elixirStorages) {
            if(elixirStorage.getStatus() == Building.BUILDING_STATUS) {
                continue;
            }
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

    public int getGoldCapacity() {
        return goldCapacity;
    }

    public int getElixirCapacity() {
        return elixirCapacity;
    }

    public void setG(int g) {
        this.g = g;
        save();
    }

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
        if(mapObject.getX() < 0 || mapObject.getX() + mapObject.getWidth() > MapObject.MAP_WIDTH ||
        mapObject.getY() < 0 || mapObject.getY() + mapObject.getHeight() > MapObject.MAP_HEIGHT) {

            return false;
        }
        if(isMapObjectOverlap(mapObject)) {
            return false;
        }
        mapObjectIds.add(mapObject.getId());
        save();
        if(mapObject instanceof Townhall || mapObject instanceof GoldStorage) {
            updateGoldCapacity();
        }
        if(mapObject instanceof Townhall || mapObject instanceof ElixirStorage) {
            updateElixirCapacity();
        }
        return true;
    }

    /**
     * Init game for new user
     */
    public void initGame() {
        if(initGameConfig == null) {
            initGameConfig = Common.loadJSONObjectFromFile(INIT_GAME_CONFIG_PATH);
            if (initGameConfig == null) {
                throw new RuntimeException("init config file is invalid or not found");
            }
        }
        try {
            // load buildings
            JSONObject initMapConfig = initGameConfig.getJSONObject("map");
            for (Iterator it = initMapConfig.keys(); it.hasNext(); ) {
                String buildingConfigName = (String) it.next();
                JSONObject buildingConfig = initMapConfig.getJSONObject(buildingConfigName);
                // position index in config start from 1
                int x = buildingConfig.getInt("posX") - 1;
                int y = buildingConfig.getInt("posY") - 1;
                MapObject newMapObject = MapObject.createMapObject(MapObject.MAP_OBJ_CONFIG_NAME_TO_ID.get(buildingConfigName), x, y);
                if(newMapObject != null) {
                    newMapObject.save();
                    boolean objAdded = addMapObject(newMapObject);
                    if(!objAdded) {
                        System.out.println("Cannot add " + buildingConfig);
                    }
                }
            }
            // load obstacles
            JSONObject initObsConfig = initGameConfig.getJSONObject("obs");
            for (Iterator it = initObsConfig.keys(); it.hasNext(); ) {
                String obsIndex = (String) it.next();
                JSONObject obsConfig = initObsConfig.getJSONObject(obsIndex);
                String obsType = obsConfig.getString("type");
                // position index in config start from 1
                int x = obsConfig.getInt("posX") - 1;
                int y = obsConfig.getInt("posY") - 1;
                MapObject newMapObject = MapObject.createMapObject(MapObject.MAP_OBJ_CONFIG_NAME_TO_ID.get(obsType), x, y);
                if(newMapObject != null) {
                    newMapObject.save();
                    boolean objAdded = addMapObject(newMapObject);
                    if(!objAdded) {
                        System.out.println("Cannot add " + obsConfig);
                    }
                }
            }
            // load resources
            JSONObject initResourcesConfig = initGameConfig.getJSONObject("player");
            int initGold = initResourcesConfig.getInt("gold");
            int initElixir = initResourcesConfig.getInt("elixir");
            int initG = initResourcesConfig.getInt("coin");
            addGold(initGold);
            addElixir(initElixir);
            addG(initG);
        } catch (JSONException e) {
            throw new RuntimeException("Init config is invalid");
        }

    }

    public static GameUser getGameUserById(int id) {
        GameUser gameUser = (GameUser) DBBuiltInUtil.get(COLLECTION_NAME, String.valueOf(id), GameUser.class);
        if(gameUser == null)
            return null;
        gameUser.loadElixir();
        gameUser.loadGold();
        gameUser.loadBuilders();
        return gameUser;
    }

    public ArrayList<MapObject> getAllMapObjects() {
        ArrayList<MapObject> result = new ArrayList<>();
        ArrayList<Integer> removedObstacles = new ArrayList<>();

        boolean mapObjIdsModified = false;
        for(int mapObjectId: mapObjectIds) {
            MapObject mapObj = MapObject.getById(mapObjectId);
            // skip removed obstacle
            if(mapObj instanceof Obstacle) {
                Obstacle obstacle = (Obstacle) mapObj;
                if(obstacle.getStatus() == Obstacle.REMOVED_STATUS) {
                    removedObstacles.add(mapObjectId);
                    mapObjIdsModified = true;
                    continue;
                }
            }
            if(mapObj != null) {
                result.add(MapObject.getById(mapObjectId));
            } else {
                // TODO: warning mapObjId of user is invalid
                System.out.println("Map object id " + mapObjectId + " is invalid!");
            }
        }
        if(mapObjIdsModified) {
            mapObjectIds.removeIf(removedObstacles::contains);
            save();
            for(int removeId: removedObstacles) {
                MapObject.removeById(removeId);
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

        for(int i = y; i < Math.min(y + mapObject.getHeight(), MapObject.MAP_HEIGHT); i++) {
            for(int j = x; j < Math.min(x + mapObject.getWidth(), MapObject.MAP_WIDTH); j++) {
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
        if(x == building.getX() && y == building.getY()) {
            return false;
        }
        if(x < 0 || y < 0 || x + building.getWidth() > MapObject.MAP_WIDTH || y + building.getHeight() > MapObject.MAP_HEIGHT) {
            return false;
        }
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

    public boolean moveBuildingById(int buildingId, int x, int y) {
        MapObject mapObject = MapObject.getById(buildingId);
        if(mapObject instanceof Building) {
            return moveBuilding((Building) mapObject, x, y);
        }
        return false;

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

    private void loadGold() {
        int amount = 0;
        int capacity = 0;
        Townhall townhall = getTownhallBuilding();
        if(townhall == null) {
            return;
        }
        capacity += townhall.getGoldCapacity();
        for(GoldStorage goldStorage : getAllGoldStorageBuilding()) {
            if(goldStorage.getStatus() != Building.BUILDING_STATUS) {
                amount += goldStorage.getGold();
                capacity += goldStorage.getGoldCapacity();
            }
        }
        amount += townhall.getGold();
        gold = amount;
        goldCapacity = capacity;
    }

    public void deductGold(int amount) {
        if(amount > gold) {
            return;
        }
        setGold(gold - amount);
    }

    public void addGold(int amount) {
        if(amount + gold > goldCapacity) {
            setGold(goldCapacity);
        }
        setGold(amount + gold);
    }

    public void deductElixir(int amount) {
        if(amount > elixir) {
            return;
        }
        setElixir(elixir - amount);
    }

    public void updateGoldCapacity() {
        Townhall townhall = getTownhallBuilding();
        if(townhall == null) {
            return;
        }
        goldCapacity = 0;
        goldCapacity += townhall.getGoldCapacity();
        ArrayList<GoldStorage> goldStorages = getAllGoldStorageBuilding();
        for(GoldStorage goldStorage : goldStorages) {
            if(goldStorage.getStatus() == Building.BUILDING_STATUS) {
                continue;
            }
            goldCapacity += goldStorage.getGoldCapacity();
        }
    }

    public void updateElixirCapacity() {
        Townhall townhall = getTownhallBuilding();
        if(townhall == null) {
            return;
        }
        elixirCapacity = 0;
        elixirCapacity += townhall.getElixirCapacity();
        ArrayList<ElixirStorage> elixirStorages = getAllElixirStorageBuilding();
        for(ElixirStorage elixirStorage : elixirStorages) {
            if(elixirStorage.getStatus() == Building.BUILDING_STATUS) {
                continue;
            }
            elixirCapacity += elixirStorage.getElixirCapacity();
        }
    }

    public void addElixir(int amount) {
        if(amount + elixir > elixirCapacity) {
            setElixir(elixirCapacity);
        }
        setElixir(elixir + amount);
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
            if(elixirStorage.getStatus() == Building.BUILDING_STATUS) {
                continue;
            }
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

    public void addG(int amount) {
        setG(g + amount);
    }

    public void deductG(int amount) {
        if(amount <= g) {
            setG(g - amount);
        }
    }

    /**
     * Buy new building
     * @param buildingTypeId building type id of building user need to build
     * @param x x position
     * @param y y postion
     * @return id of building if buy success, error code if buy fail
     */
    public int buyBuilding(int buildingTypeId, int x, int y) {
        if(!MapObject.isObjectTypeBuilding(buildingTypeId)) {
            return ResponseBuyBuilding.OBJECT_TYPE_INVALID;
        }

        // check available builders
        if(nbOfAvaiBuilder <= 0) {
            return ResponseBuyBuilding.NOT_ENOUGH_BUILDERS;
        }

        // check max number of building
        ArrayList<MapObject> mapObjects = getAllMapObjects();
        int nbOfCurrBuyingBuilding = 0;
        for(MapObject mapObject: mapObjects) {
            if(mapObject.getObjectType() == buildingTypeId) {
                nbOfCurrBuyingBuilding += 1;
            }
        }
        if(buildingTypeId == MapObject.BUILDER_HUT) {
            if(nbOfCurrBuyingBuilding >= 5) {
                return ResponseBuyBuilding.FULL_BUILDINGS;
            }
        } else {
            Townhall townhall = getTownhallBuilding();
            if (townhall.getMaxNumberBuilding(buildingTypeId) <= nbOfCurrBuyingBuilding) {
                return ResponseBuyBuilding.FULL_BUILDINGS;
            }
        }
        Building building = (Building) MapObject.createMapObject(buildingTypeId, x, y);
        if(building instanceof BuilderHut) {
            ((BuilderHut) building).setIndex(nbOfCurrBuyingBuilding + 1);
        }
//        TODO: handle all building
        if(building == null) {
            return -1000;
        }
        if(isMapObjectOverlap(building)) {
            return ResponseBuyBuilding.BUILDING_OVERLAP;
        }
        // check resource
        int goldToBuild = building.getGoldToBuild();
        if(gold < goldToBuild) {
            return ResponseBuyBuilding.NOT_ENOUGH_GOLD;
        }
        int elixirToBuild = building.getElixirToBuild();
        if(elixir < elixirToBuild) {
            return ResponseBuyBuilding.NOT_ENOUGH_ELIXIR;
        }
        int gToBuild = building.getGToBuild();
        if(g < gToBuild) {
            return ResponseBuyBuilding.NOT_ENOUGH_G;
        }
        int timeToBuild = building.getTimeToBuild();
        // deduct resources and add new building

        deductGold(goldToBuild);
        deductElixir(elixirToBuild);
        deductG(gToBuild);
        if(timeToBuild > 0) {
            deductAvaiBuilder();
        }
        mapObjectIds.add(building.getId());
        building.build();
        building.save();
        save();
        return building.getId();
    }

    /**
     * Cancel upgrading or building building
     * @param buildingId building id
     * @return building id if success, otherwise return error code
     */
    public int cancelBuilding(int buildingId) {
        if(!mapObjectIds.contains(buildingId)) {
            return ResponseCancelBuilding.INVALID_BUILDING_ID;
        }
        MapObject mapObject = MapObject.getById(buildingId);
        if(!(mapObject instanceof Building)) {
            return ResponseCancelBuilding.INVALID_BUILDING_ID;
        }
        Building building = (Building) mapObject;
        if(building.getStatus() == Building.BUILDING_STATUS) {
            int goldToBuild = building.getGoldToBuild();
            int elixirToBuild = building.getElixirToBuild();

            // add 1/2 resource of building
            addGold(goldToBuild / 2);
            addElixir(elixirToBuild / 2);

            // remove building
            mapObjectIds.removeIf(mapObjectId -> mapObjectId == buildingId);
            save();
            MapObject.removeById(buildingId);
            return buildingId;
        }

        if(building.getStatus() == Building.UPGRADING_STATUS) {
            int goldToUpgrade = building.getGoldToUpgrade();
            int elixirToUpgrade = building.getElixirToUpgrade();

            // add 1/2 resource of building
            addGold(goldToUpgrade / 2);
            addElixir(elixirToUpgrade / 2);

            // cancel building upgrading
            building.cancelUpgrading();
            return buildingId;
        }
        return ResponseCancelBuilding.INVALID_BUILDING_STATUS;
    }

    public int upgradeBuilding(int buildingId) {
        if(!mapObjectIds.contains(buildingId)) {
            return ResponseUpgradeBuilding.INVALID_BUILDING_ID;
        }
        MapObject mapObject = MapObject.getById(buildingId);
        if(!(mapObject instanceof Building)) {
            return ResponseUpgradeBuilding.INVALID_BUILDING_ID;
        }
        Building building = (Building) mapObject;

        if(building.getStatus() == Building.NORMAL_STATUS) {

            int goldToUpgrade = building.getGoldToUpgrade();
            int elixirToUpgrade = building.getElixirToUpgrade();
            if(goldToUpgrade > gold || elixirToUpgrade > elixir) {
                return ResponseUpgradeBuilding.NOT_ENOUGH_RESOURCE;
            }
            if(nbOfAvaiBuilder <= 0) {
                return ResponseUpgradeBuilding.NOT_ENOUGH_BUILDERS;
            }

            // deduct resources and upgrade building
            deductGold(goldToUpgrade);
            deductElixir(elixirToUpgrade);
            building.upgrade();

            return buildingId;
        }
        return ResponseUpgradeBuilding.INVALID_BUILDING_STATUS;
    }

    /**
     * quick finish remove obstacle, build/upgrade building
     * @param mapObjId id of map object
     * @param gToQuickFinish g to quick finish sent from client
     * @return id of map object, otherwise return error code
     */
    public int quickFinishMapObject(int mapObjId, int gToQuickFinish) {
        if(!mapObjectIds.contains(mapObjId)) {
            return ResponseQuickFinish.INVALID_MAP_OBJECT;
        }
        MapObject mapObject = MapObject.getById(mapObjId);
        if(mapObject instanceof Obstacle) {
            // TODO: handle obstacle
            return -1000;
        }
        if(mapObject instanceof Building) {
            Building building = (Building) mapObject;
            if(building.getStatus() == Building.NORMAL_STATUS) {
                return ResponseQuickFinish.INVALID_MAP_OBJECT_STATUS;
            }
            if(gToQuickFinish > g) {
                return ResponseQuickFinish.NOT_ENOUGH_G;
            }
            if(building.getStatus() == Building.BUILDING_STATUS ||
                    building.getStatus() == Building.UPGRADING_STATUS) {
                int finishTime = building.getFinishTime();
                int gToQuickFinishValidate = ResourceExchange.timeToG(finishTime - Common.currentTimeInSecond());
                if(gToQuickFinishValidate > gToQuickFinish) {
                    return ResponseQuickFinish.INVALID_G;
                }
                building.quickFinish();
                g -= gToQuickFinish;
                save();
                return mapObjId;
            }
        }
        return ResponseQuickFinish.INVALID_MAP_OBJECT;
    }

    public int removeObstacle(int obstacleId) {
        if(!mapObjectIds.contains(obstacleId)) {
            return ResponseRemoveObstacle.INVALID_MAP_OBJ_ID;
        }
        MapObject mapObject = MapObject.getById(obstacleId);
        if(mapObject instanceof Obstacle) {
            Obstacle obstacle = (Obstacle) mapObject;
            if(obstacle.getStatus() == Obstacle.NORMAL_STATUS) {
                if(nbOfAvaiBuilder <= 0) {
                    return ResponseRemoveObstacle.NOT_ENOUGH_BUILDER;
                }
                int goldToRemove = obstacle.getGoldToRemove();
                int elixirToRemove = obstacle.getElixirToRemove();
                if(gold < goldToRemove || elixir < elixirToRemove) {
                    return ResponseRemoveObstacle.NOT_ENOUGH_RESOURCES;
                }

                // deduct resource and remove obstacle
                deductGold(goldToRemove);
                deductElixir(elixirToRemove);
                obstacle.remove();
                return obstacleId;
            } else {
                return ResponseRemoveObstacle.INVALID_MAP_OBJ_STATUS;
            }
        }
        return ResponseRemoveObstacle.INVALID_MAP_OBJ_ID;
    }
}

