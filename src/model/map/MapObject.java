package model.map;

import bitzero.util.common.business.CommonHandle;
import com.google.gson.annotations.Expose;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.database.DBBuiltInUtil;

import java.util.*;

public abstract class MapObject {
    private static final Logger logger = LoggerFactory.getLogger("MapObject");

    @Expose
    protected int id;
    @Expose
    protected int x;
    @Expose
    protected int y;
    @Expose
    protected int objectType;
    protected int width;
    protected int height;

    public static final int ARMY_CAMP = 1;
    public static final int BARRACK = 2;
    public static final int BUILDER_HUT = 3;
    public static final int CLAN_CASTLE = 4;
    public static final int ARCHER_TOWER = 5;
    public static final int AIR_DEFENSE = 6;
    public static final int CANON = 7;
    public static final int ELIXIR_MINE = 8;
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
    public static final int OBSTACLE_13 = 26;
    public static final int OBSTACLE_14 = 27;
    public static final int OBSTACLE_15 = 28;
    public static final int OBSTACLE_16 = 29;
    public static final int OBSTACLE_17 = 30;
    public static final int OBSTACLE_18 = 31;
    public static final int OBSTACLE_19 = 32;
    public static final int OBSTACLE_20 = 33;
    public static final int OBSTACLE_21 = 34;
    public static final int OBSTACLE_22 = 35;
    public static final int OBSTACLE_23 = 36;
    public static final int OBSTACLE_24 = 37;
    public static final int OBSTACLE_25 = 38;
    public static final int OBSTACLE_26 = 39;
    public static final int OBSTACLE_27 = 40;
    public static final int GOLD_STORAGE = 41;
    public static final int GOLD_MINE = 42;
    public static final Set<Integer> BUILDING_TYPES = new HashSet<>(Arrays.asList(
        ARMY_CAMP,
        BARRACK,
        BUILDER_HUT,
        CLAN_CASTLE,
        ARCHER_TOWER,
        AIR_DEFENSE,
        CANON,
        ELIXIR_MINE,
        ELIXIR_STORAGE,
        LABORATORY,
        TREBUCHET,
        TOWNHALL,
        WALL,
        GOLD_STORAGE,
        GOLD_MINE
    ));

    public static final int MAP_WIDTH = 40;
    public static final int MAP_HEIGHT = 40;

    public static final Map<String, Integer> MAP_OBJ_CONFIG_NAME_TO_ID = new HashMap<String, Integer>() {
        {
            put("AMC_1", MapObject.ARMY_CAMP);
            put("BAR_1", BARRACK);
            put("BDH_1", BUILDER_HUT);
            put("CLC_1", CLAN_CASTLE);
            put("DEF_2", ARCHER_TOWER);
            put("DEF_5", AIR_DEFENSE);
            put("DEF_1", CANON);
            put("RES_2", ELIXIR_MINE);
            put("STO_2", ELIXIR_STORAGE);
            put("LAB_1", LABORATORY);
            put("DEF_3", TREBUCHET);
            put("TOW_1", TOWNHALL);
            put("WAL_1", WALL);
            put("OBS_1", OBSTACLE_1);
            put("OBS_2", OBSTACLE_2);
            put("OBS_3", OBSTACLE_3);
            put("OBS_4", OBSTACLE_4);
            put("OBS_5", OBSTACLE_5);
            put("OBS_6", OBSTACLE_6);
            put("OBS_7", OBSTACLE_7);
            put("OBS_8", OBSTACLE_8);
            put("OBS_9", OBSTACLE_9);
            put("OBS_10", OBSTACLE_10);
            put("OBS_11", OBSTACLE_11);
            put("OBS_12", OBSTACLE_12);
            put("OBS_14", OBSTACLE_14);
            put("OBS_15", OBSTACLE_15);
            put("OBS_16", OBSTACLE_16);
            put("OBS_17", OBSTACLE_17);
            put("OBS_18", OBSTACLE_18);
            put("OBS_19", OBSTACLE_19);
            put("OBS_20", OBSTACLE_20);
            put("OBS_21", OBSTACLE_21);
            put("OBS_22", OBSTACLE_22);
            put("OBS_23", OBSTACLE_23);
            put("OBS_24", OBSTACLE_24);
            put("OBS_25", OBSTACLE_25);
            put("OBS_26", OBSTACLE_26);
            put("OBS_27", OBSTACLE_27);
            put("OBS_13", OBSTACLE_13);
            put("STO_1", GOLD_STORAGE);
            put("RES_1", GOLD_MINE);
        }
    };

    public static final Map<Integer, String> MAP_ID_OBJ_TO_CONFIG_NAME = new HashMap<Integer, String>() {
        {
            put(ARMY_CAMP, "AMC_1");
            put(BARRACK, "BAR_1");
            put(BUILDER_HUT, "BDH_1");
            put(CLAN_CASTLE, "CLC_1");
            put(ARCHER_TOWER, "DEF_2");
            put(AIR_DEFENSE, "DEF_5");
            put(CANON, "DEF_1");
            put(ELIXIR_MINE, "RES_2");
            put(ELIXIR_STORAGE, "STO_2");
            put(LABORATORY, "LAB_1");
            put(TREBUCHET, "DEF_3");
            put(TOWNHALL, "TOW_1");
            put(WALL, "WAL_1");
            put(OBSTACLE_1, "OBS_1");
            put(OBSTACLE_2, "OBS_2");
            put(OBSTACLE_3, "OBS_3");
            put(OBSTACLE_4, "OBS_4");
            put(OBSTACLE_5, "OBS_5");
            put(OBSTACLE_6, "OBS_6");
            put(OBSTACLE_7, "OBS_7");
            put(OBSTACLE_8, "OBS_8");
            put(OBSTACLE_9, "OBS_9");
            put(OBSTACLE_10, "OBS_10");
            put(OBSTACLE_11, "OBS_11");
            put(OBSTACLE_12, "OBS_12");
            put(OBSTACLE_14, "OBS_14");
            put(OBSTACLE_15, "OBS_15");
            put(OBSTACLE_16, "OBS_16");
            put(OBSTACLE_17, "OBS_17");
            put(OBSTACLE_18, "OBS_18");
            put(OBSTACLE_19, "OBS_19");
            put(OBSTACLE_20, "OBS_20");
            put(OBSTACLE_21, "OBS_21");
            put(OBSTACLE_22, "OBS_22");
            put(OBSTACLE_23, "OBS_23");
            put(OBSTACLE_24, "OBS_24");
            put(OBSTACLE_25, "OBS_25");
            put(OBSTACLE_26, "OBS_26");
            put(OBSTACLE_27, "OBS_27");
            put(OBSTACLE_13, "OBS_13");
            put(GOLD_STORAGE, "STO_1");
            put(GOLD_MINE, "RES_1");
        }
    };

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
            CommonHandle.writeErrLog(e);
            logger.warn("Save map object" + id + " error");
        }
    }

    public static void removeById(int id) {
        DBBuiltInUtil.delete(collectionName, String.valueOf(id));
    }

    public static MapObject loadFromJSONString(String mapObjectStr) {
        if(mapObjectStr == null) {
            return null;
        }
        try {
            JSONObject mapObjectJson = new JSONObject(mapObjectStr);
            int objectType = mapObjectJson.getInt("objectType");
            MapObject mapObject = null;
            switch (objectType) {
                case TOWNHALL:
                    mapObject = DBBuiltInUtil.gsonWithExpose.fromJson(mapObjectStr, Townhall.class);
                    break;
                case ARMY_CAMP:
                    mapObject = DBBuiltInUtil.gsonWithExpose.fromJson(mapObjectStr, ArmyCamp.class);
                    break;
                case GOLD_STORAGE:
                    mapObject = DBBuiltInUtil.gsonWithExpose.fromJson(mapObjectStr, GoldStorage.class);
                    break;
                case GOLD_MINE:
                    mapObject = DBBuiltInUtil.gsonWithExpose.fromJson(mapObjectStr, GoldMine.class);
                    break;
                case ELIXIR_STORAGE:
                    mapObject = DBBuiltInUtil.gsonWithExpose.fromJson(mapObjectStr, ElixirStorage.class);
                    break;
                case ELIXIR_MINE:
                    mapObject = DBBuiltInUtil.gsonWithExpose.fromJson(mapObjectStr, ElixirMine.class);
                    break;
                case CLAN_CASTLE:
                    mapObject = DBBuiltInUtil.gsonWithExpose.fromJson(mapObjectStr, ClanCastle.class);
                    break;
                case BUILDER_HUT:
                    mapObject = DBBuiltInUtil.gsonWithExpose.fromJson(mapObjectStr, BuilderHut.class);
                    break;
                case OBSTACLE_1:
                case OBSTACLE_2:
                case OBSTACLE_3:
                case OBSTACLE_4:
                case OBSTACLE_5:
                case OBSTACLE_6:
                case OBSTACLE_7:
                case OBSTACLE_8:
                case OBSTACLE_9:
                case OBSTACLE_10:
                case OBSTACLE_11:
                case OBSTACLE_12:
                case OBSTACLE_13:
                case OBSTACLE_14:
                case OBSTACLE_15:
                case OBSTACLE_16:
                case OBSTACLE_17:
                case OBSTACLE_18:
                case OBSTACLE_19:
                case OBSTACLE_20:
                case OBSTACLE_21:
                case OBSTACLE_22:
                case OBSTACLE_23:
                case OBSTACLE_24:
                case OBSTACLE_25:
                case OBSTACLE_26:
                case OBSTACLE_27:
                    mapObject = DBBuiltInUtil.gsonWithExpose.fromJson(mapObjectStr, Obstacle.class);
                    break;
                case CANON:
                    mapObject = DBBuiltInUtil.gsonWithExpose.fromJson(mapObjectStr, Canon.class);
                    break;
                case BARRACK:
                    mapObject = DBBuiltInUtil.gsonWithExpose.fromJson(mapObjectStr, Barrack.class);
                    break;
                case ARCHER_TOWER:
                    mapObject = DBBuiltInUtil.gsonWithExpose.fromJson(mapObjectStr, ArcherTower.class);
                    break;
                case AIR_DEFENSE:
                    mapObject = DBBuiltInUtil.gsonWithExpose.fromJson(mapObjectStr, AirDefense.class);
                    break;
                case WALL:
                    mapObject = DBBuiltInUtil.gsonWithExpose.fromJson(mapObjectStr, Wall.class);
                    break;
                case TREBUCHET:
                    mapObject = DBBuiltInUtil.gsonWithExpose.fromJson(mapObjectStr, Trebuchet.class);
                    break;
            };
            if(mapObject != null) {
                mapObject.loadExtraInfo();
                mapObject.updateStatus();
            } else {
                logger.warn("not handle map object by id" + mapObjectStr);
            }
            return mapObject;

        } catch (JSONException e) {
            CommonHandle.writeErrLog(e);
            logger.warn("Eror get json map oject " + mapObjectStr);
        }
        return null;

    }
    public static MapObject getById(int id) {
        String mapObjectStr = DBBuiltInUtil.get(collectionName, String.valueOf(id));
        return loadFromJSONString(mapObjectStr);
    }

    public static ArrayList<MapObject> getByIdList(ArrayList<Integer> ids) {
        ArrayList<String> idStrings = new ArrayList<>();
        for(int id: ids) {
            idStrings.add(String.valueOf(id));
        }
        ArrayList<String> mapObjectStrs = DBBuiltInUtil.multiget(collectionName, idStrings);
        ArrayList<MapObject> mapObjects = new ArrayList<>();
        for(String mapObjectStr: mapObjectStrs) {
            mapObjects.add(loadFromJSONString(mapObjectStr));
        }
        return mapObjects;
    }

    public static MapObject createMapObject(int mapObjectType, int x, int y) {
        switch (mapObjectType) {
            case TOWNHALL:
                return Townhall.createTownhall(x, y);
            case GOLD_STORAGE:
                return GoldStorage.createGoldStorage(x, y);
            case ARMY_CAMP:
                return ArmyCamp.createArmyCamp(x, y);
            case ELIXIR_STORAGE:
                return ElixirStorage.createElixirStorage(x, y);
            case ELIXIR_MINE:
                return ElixirMine.createElixirMine(x, y);
            case CLAN_CASTLE:
                return ClanCastle.createClanCastle(x, y);
            case BUILDER_HUT:
                return BuilderHut.createBuilderHut(x, y);
            case GOLD_MINE:
                return GoldMine.createGoldMine(x, y);
            case OBSTACLE_1:
            case OBSTACLE_2:
            case OBSTACLE_3:
            case OBSTACLE_4:
            case OBSTACLE_5:
            case OBSTACLE_6:
            case OBSTACLE_7:
            case OBSTACLE_8:
            case OBSTACLE_9:
            case OBSTACLE_10:
            case OBSTACLE_11:
            case OBSTACLE_12:
            case OBSTACLE_13:
            case OBSTACLE_14:
            case OBSTACLE_15:
            case OBSTACLE_16:
            case OBSTACLE_17:
            case OBSTACLE_18:
            case OBSTACLE_19:
            case OBSTACLE_20:
            case OBSTACLE_21:
            case OBSTACLE_22:
            case OBSTACLE_23:
            case OBSTACLE_24:
            case OBSTACLE_25:
            case OBSTACLE_26:
            case OBSTACLE_27:
                return Obstacle.createObtacle(mapObjectType, x, y);
            case AIR_DEFENSE:
                return AirDefense.createAirDefense(x, y);
            case ARCHER_TOWER:
                return ArcherTower.createArcherTower(x, y);
            case BARRACK:
                return Barrack.createBarrack(x, y);
            case CANON:
                return Canon.createCanon(x, y);
            case WALL:
                return Wall.createWall(x, y);
        }
        ;
        return null;
    }

    public static boolean isObjectTypeBuilding(int id) {
        return BUILDING_TYPES.contains(id);
    }

    /**
     * Load extra informations derived from attributes loaded from database
     */
    public abstract void loadExtraInfo();

    public abstract void updateStatus();

}
