package model.map;

import com.google.gson.annotations.Expose;
import org.json.JSONException;
import org.json.JSONObject;
import util.database.DBBuiltInUtil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class MapObject {

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

    public static final Map<String, Integer> MAP_OBJ_CONFIG_NAME_TO_ID = Map.ofEntries(
            Map.entry("AMC_1", ARMY_CAMP),
            Map.entry("BAR_2", BARRACK),
            Map.entry("BDH_1", BUILDER_HUT),
            Map.entry("CLC_1", CLAN_CASTLE),
            Map.entry("DEF_2", ARCHER_TOWER),
            Map.entry("DEF_5", AIR_DEFENSE),
            Map.entry("DEF_1", CANON),
            Map.entry("RES_2", ELIXIR_MINE),
            Map.entry("STO_2", ELIXIR_STORAGE),
            Map.entry("LAB_1", LABORATORY),
            Map.entry("DEF_3", TREBUCHET),
            Map.entry("TOW_1", TOWNHALL),
            Map.entry("WAL_1", WALL),
            Map.entry("OBS_1", OBSTACLE_1),
            Map.entry("OBS_2", OBSTACLE_2),
            Map.entry("OBS_3", OBSTACLE_3),
            Map.entry("OBS_4", OBSTACLE_4),
            Map.entry("OBS_5", OBSTACLE_5),
            Map.entry("OBS_6", OBSTACLE_6),
            Map.entry("OBS_7", OBSTACLE_7),
            Map.entry("OBS_8", OBSTACLE_8),
            Map.entry("OBS_9", OBSTACLE_9),
            Map.entry("OBS_10", OBSTACLE_10),
            Map.entry("OBS_11", OBSTACLE_11),
            Map.entry("OBS_12", OBSTACLE_12),
            Map.entry("OBS_14", OBSTACLE_14),
            Map.entry("OBS_15", OBSTACLE_15),
            Map.entry("OBS_16", OBSTACLE_16),
            Map.entry("OBS_17", OBSTACLE_17),
            Map.entry("OBS_18", OBSTACLE_18),
            Map.entry("OBS_19", OBSTACLE_19),
            Map.entry("OBS_20", OBSTACLE_20),
            Map.entry("OBS_21", OBSTACLE_21),
            Map.entry("OBS_22", OBSTACLE_22),
            Map.entry("OBS_23", OBSTACLE_23),
            Map.entry("OBS_24", OBSTACLE_24),
            Map.entry("OBS_25", OBSTACLE_25),
            Map.entry("OBS_26", OBSTACLE_26),
            Map.entry("OBS_27", OBSTACLE_27),
            Map.entry("OBS_13", OBSTACLE_13),
            Map.entry("STO_1", GOLD_STORAGE),
            Map.entry("RES_1", GOLD_MINE)
    );

    public static final Map<Integer, String> MAP_ID_OBJ_TO_CONFIG_NAME = Map.ofEntries(
            Map.entry(ARMY_CAMP, "AMC_1"),
            Map.entry(BARRACK, "BAR_2"),
            Map.entry(BUILDER_HUT, "BDH_1"),
            Map.entry(CLAN_CASTLE, "CLC_1"),
            Map.entry(ARCHER_TOWER, "DEF_2"),
            Map.entry(AIR_DEFENSE, "DEF_5"),
            Map.entry(CANON, "DEF_1"),
            Map.entry(ELIXIR_MINE, "RES_2"),
            Map.entry(ELIXIR_STORAGE, "STO_2"),
            Map.entry(LABORATORY, "LAB_1"),
            Map.entry(TREBUCHET, "DEF_3"),
            Map.entry(TOWNHALL, "TOW_1"),
            Map.entry(WALL, "WAL_1"),
            Map.entry(OBSTACLE_1, "OBS_1"),
            Map.entry(OBSTACLE_2, "OBS_2"),
            Map.entry(OBSTACLE_3, "OBS_3"),
            Map.entry(OBSTACLE_4, "OBS_4"),
            Map.entry(OBSTACLE_5, "OBS_5"),
            Map.entry(OBSTACLE_6, "OBS_6"),
            Map.entry(OBSTACLE_7, "OBS_7"),
            Map.entry(OBSTACLE_8, "OBS_8"),
            Map.entry(OBSTACLE_9, "OBS_9"),
            Map.entry(OBSTACLE_10, "OBS_10"),
            Map.entry(OBSTACLE_11, "OBS_11"),
            Map.entry(OBSTACLE_12, "OBS_12"),
            Map.entry(OBSTACLE_14, "OBS_14"),
            Map.entry(OBSTACLE_15, "OBS_15"),
            Map.entry(OBSTACLE_16, "OBS_16"),
            Map.entry(OBSTACLE_17, "OBS_17"),
            Map.entry(OBSTACLE_18, "OBS_18"),
            Map.entry(OBSTACLE_19, "OBS_19"),
            Map.entry(OBSTACLE_20, "OBS_20"),
            Map.entry(OBSTACLE_21, "OBS_21"),
            Map.entry(OBSTACLE_22, "OBS_22"),
            Map.entry(OBSTACLE_23, "OBS_23"),
            Map.entry(OBSTACLE_24, "OBS_24"),
            Map.entry(OBSTACLE_25, "OBS_25"),
            Map.entry(OBSTACLE_26, "OBS_26"),
            Map.entry(OBSTACLE_27, "OBS_27"),
            Map.entry(OBSTACLE_13, "OBS_13"),
            Map.entry(GOLD_STORAGE, "STO_1"),
            Map.entry(GOLD_MINE, "RES_1")
    );
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
            MapObject mapObject = switch (objectType) {
                case TOWNHALL -> DBBuiltInUtil.gsonWithExpose.fromJson(mapObjectStr, Townhall.class);
                case ARMY_CAMP -> DBBuiltInUtil.gsonWithExpose.fromJson(mapObjectStr, ArmyCamp.class);
                case GOLD_STORAGE -> DBBuiltInUtil.gsonWithExpose.fromJson(mapObjectStr, GoldStorage.class);
                case GOLD_MINE -> DBBuiltInUtil.gsonWithExpose.fromJson(mapObjectStr, GoldMine.class);
                case ELIXIR_STORAGE -> DBBuiltInUtil.gsonWithExpose.fromJson(mapObjectStr, ElixirStorage.class);
                case CLAN_CASTLE -> DBBuiltInUtil.gsonWithExpose.fromJson(mapObjectStr, ClanCastle.class);
                case BUILDER_HUT -> DBBuiltInUtil.gsonWithExpose.fromJson(mapObjectStr, BuilderHut.class);
                case OBSTACLE_1, OBSTACLE_2, OBSTACLE_3, OBSTACLE_4, OBSTACLE_5, OBSTACLE_6, OBSTACLE_7, OBSTACLE_8, OBSTACLE_9, OBSTACLE_10, OBSTACLE_11, OBSTACLE_12, OBSTACLE_13, OBSTACLE_14, OBSTACLE_15, OBSTACLE_16, OBSTACLE_17, OBSTACLE_18, OBSTACLE_19, OBSTACLE_20, OBSTACLE_21, OBSTACLE_22, OBSTACLE_23, OBSTACLE_24, OBSTACLE_25, OBSTACLE_26, OBSTACLE_27 -> DBBuiltInUtil.gsonWithExpose.fromJson(mapObjectStr, Obstacle.class);
                default -> null;
            };
            if(mapObject != null) {
                mapObject.loadExtraInfo();
            }
            return mapObject;

        } catch (JSONException e) {
            System.out.println("Eror get map oject " + id);
        }
        System.out.println("not handle map object by id" + mapObjectStr);
        return null;
    }

    public static MapObject createMapObject(int mapObjectType, int x, int y) {
        return switch (mapObjectType) {
            case TOWNHALL -> Townhall.createTownhall(x, y);
            case GOLD_STORAGE -> GoldStorage.createGoldStorage(x, y);
            case ARMY_CAMP -> ArmyCamp.createArmyCamp(x, y);
            case ELIXIR_STORAGE -> ElixirStorage.createElixirStorage(x, y);
            case CLAN_CASTLE -> ClanCastle.createClanCastle(x, y);
            case BUILDER_HUT -> BuilderHut.createBuilderHut(x, y);
            case GOLD_MINE -> GoldMine.createGoldMine(x, y);
            case
                    OBSTACLE_1,
                    OBSTACLE_2,
                    OBSTACLE_3,
                    OBSTACLE_4,
                    OBSTACLE_5,
                    OBSTACLE_6,
                    OBSTACLE_7,
                    OBSTACLE_8,
                    OBSTACLE_9,
                    OBSTACLE_10,
                    OBSTACLE_11,
                    OBSTACLE_12,
                    OBSTACLE_13,
                    OBSTACLE_14,
                    OBSTACLE_15,
                    OBSTACLE_16,
                    OBSTACLE_17,
                    OBSTACLE_18,
                    OBSTACLE_19,
                    OBSTACLE_20,
                    OBSTACLE_21,
                    OBSTACLE_22,
                    OBSTACLE_23,
                    OBSTACLE_24,
                    OBSTACLE_25,
                    OBSTACLE_26,
                    OBSTACLE_27 -> Obstacle.createObtacle(mapObjectType, x, y);
            default -> null;
        };
    }

    public static boolean isObjectTypeBuilding(int id) {
        return BUILDING_TYPES.contains(id);
    }

    public abstract void loadExtraInfo();

}
