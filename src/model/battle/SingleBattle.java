package model.battle;

import bitzero.util.common.business.CommonHandle;
import com.google.gson.annotations.Expose;
import model.map.ElixirContainable;
import model.map.GoldContainable;
import model.map.MapObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import util.Common;

import java.util.ArrayList;
import java.util.HashMap;

public class SingleBattle {
    @Expose
    private int id;
    @Expose
    private int star;

    // available gold, if equals -1, use default in config file
    @Expose
    private int availGold;

    // available elixir, if equals -1, use default in config file
    @Expose
    private int availElixir;
    private ArrayList<MapObject> battleMapObjects;

    private static HashMap<Integer, ArrayList<MapObject>> ID_TO_BATTLE_MAP;
    public static HashMap<Integer, Integer> ID_TO_DEFAULT_GOLD;
    public static HashMap<Integer, Integer> ID_TO_DEFAULT_ELIXIR;
    private static final String MAP_DIRECTORY_PATH = "conf/Battle/map/";

    public static final int MIN_ID = 1;
    public static final int MAX_ID = 10;

    public SingleBattle(int id, int star, int availGold, int availElixir) {
        if(id < 1 || id > 10) {
            throw new RuntimeException("Battle id " + id + " is invalid");
        }
        this.id = id;
        this.star = star;
        this.availGold = availGold;
        this.availElixir = availElixir;
    }

    public SingleBattle(int id) {
        this(id, 0, getDefaultGold(id), getDefaultElixir(id));
    }

    public static void loadConfig() {
        if (ID_TO_BATTLE_MAP != null) {
            return;
        }
        ID_TO_BATTLE_MAP = new HashMap<>();
        ID_TO_DEFAULT_GOLD = new HashMap<>();
        ID_TO_DEFAULT_ELIXIR = new HashMap<>();
        try {
            for (int mapId = 1; mapId <= 10; mapId++) {
                String configFileName = mapId + ".map";
                String confPath = MAP_DIRECTORY_PATH + configFileName;
                JSONObject battleConfig = Common.loadJSONObjectFromFile(confPath);
                if(battleConfig == null) {
                    throw new RuntimeException(configFileName + "battle config is invalid");
                }
                JSONObject resourceConfig = battleConfig.getJSONObject("resourse");
                if(resourceConfig == null) {
                    throw new RuntimeException(configFileName + "resource battle config is invalid");
                }
                ID_TO_DEFAULT_GOLD.put(mapId, resourceConfig.getInt("gold"));
                ID_TO_DEFAULT_ELIXIR.put(mapId, resourceConfig.getInt("elixir"));
                JSONArray houseConfig = battleConfig.getJSONArray("house");
                if(houseConfig == null) {
                    throw new RuntimeException(configFileName + "house battle config is invalid");
                }
                ArrayList<MapObject> battleMap = new ArrayList<>();
                for(Object mapObjConfObj: houseConfig.getArrayList()) {
                    if(!(mapObjConfObj instanceof JSONObject)) {
                        throw new RuntimeException(configFileName + "house battle config is invalid");
                    }
                    JSONObject mapObjConf = (JSONObject) mapObjConfObj;
                    int cellPos = mapObjConf.getInt("cell");
                    int x = cellPos % MapObject.MAP_WIDTH;
                    int y = (cellPos -  x) / MapObject.MAP_WIDTH;
                    String objTypeIdName = mapObjConf.getString("objType");
                    if(!MapObject.MAP_OBJ_CONFIG_NAME_TO_ID.containsKey(objTypeIdName)) {
                        continue;
                    }
                    int objTypeId = MapObject.MAP_OBJ_CONFIG_NAME_TO_ID.get(objTypeIdName);
                    int objId = mapObjConf.getInt("objId");
                    int level = mapObjConf.getInt("level");
                    MapObject mapObject = MapObject.createMapObject(objId, objTypeId, x, y, level);
                    if(mapObject == null) {
                        throw new RuntimeException(configFileName + " config is invalid, cannot create map obj " + objTypeId);
                    }
                    battleMap.add(mapObject);
                }
                ID_TO_BATTLE_MAP.put(mapId, battleMap);
            }
        } catch (JSONException e) {
            CommonHandle.writeErrLog(e);
            ID_TO_BATTLE_MAP = null;
        }
        if (ID_TO_BATTLE_MAP == null) {
            throw new RuntimeException("Cannot load single battle map config");
        }
    }

    public static int getDefaultGold(int id) {
        loadConfig();
        return ID_TO_DEFAULT_GOLD.get(id);
    }

    public static int getDefaultElixir(int id) {
        loadConfig();
        return ID_TO_DEFAULT_ELIXIR.get(id);
    }

    public void distributeResource() {
        if(battleMapObjects == null) {
            throw new RuntimeException("Not initial map yet");
        }
        ArrayList<GoldContainable> goldContainables = new ArrayList<>();
        ArrayList<ElixirContainable> elixirContainables = new ArrayList<>();
        int totalGoldCapacity = 0;
        int totalElixirCapacity = 0;
        for(MapObject mapObj: battleMapObjects) {
            if(mapObj instanceof GoldContainable) {
                GoldContainable goldContainable = (GoldContainable) mapObj;
                totalGoldCapacity += goldContainable.getGoldCapacity();
                goldContainables.add(goldContainable);
            }
            if(mapObj instanceof ElixirContainable) {
                ElixirContainable elixirContainable = (ElixirContainable) mapObj;
                totalElixirCapacity += elixirContainable.getElixirCapacity();
                elixirContainables.add(elixirContainable);
            }
        }
        if(availGold < 0) {
            availGold = ID_TO_DEFAULT_GOLD.get(id);
        }
        if(availElixir < 0) {
            availElixir = ID_TO_DEFAULT_ELIXIR.get(id);
        }
        if(availGold > totalGoldCapacity) {
            throw new RuntimeException("Cannot distribute resource, have gold capacity " +
                    totalGoldCapacity + " but available gold is " + availGold);
        }
        if(availElixir > totalElixirCapacity) {
            throw new RuntimeException("Cannot distribute resource, have elixir capacity " +
                    totalElixirCapacity + " but available elixir is " + availElixir);
        }
        int goldToDistribute = availGold;
        int elixirToDistribute = availElixir;

        goldContainables.sort((gc1, gc2) -> {
            if(gc1.getGoldCapacity() > gc2.getGoldCapacity()) {
                return 1;
            }
            if(gc1.getGoldCapacity() < gc2.getGoldCapacity()) {
                return -1;
            }
            return Integer.compare(((MapObject) gc1).getId(), ((MapObject) gc2).getId());
        });
        elixirContainables.sort((ec1, ec2) -> {
            if(ec1.getElixirCapacity() > ec2.getElixirCapacity()) {
                return 1;
            }
            if(ec1.getElixirCapacity() < ec2.getElixirCapacity()) {
                return -1;
            }
            return Integer.compare(((MapObject) ec1).getId(), ((MapObject) ec2).getId());
        });

        int goldIndex = 0;
        while(goldToDistribute > 0) {
            int goldAvg = goldToDistribute / (goldContainables.size() - goldIndex);
            if(goldAvg <= goldContainables.get(goldIndex).getGoldCapacity()) {
                for(int i = goldIndex; i < goldContainables.size() - 1; i++) {
                    goldContainables.get(i).setGold(goldAvg);
                    goldToDistribute -= goldAvg;
                }
                goldContainables.get(goldContainables.size() - 1).setGold(goldToDistribute);
                goldToDistribute = 0;
            }
            else {
                GoldContainable currGoldContainer = goldContainables.get(goldIndex);
                currGoldContainer.setGold(currGoldContainer.getGoldCapacity());
                goldToDistribute -= currGoldContainer.getGoldCapacity();
                goldIndex += 1;
            }
        }

        int elixirIndex = 0;
        while(elixirToDistribute > 0) {
            int elixirAvg = elixirToDistribute / (elixirContainables.size() - elixirIndex);
            if(elixirAvg <= elixirContainables.get(elixirIndex).getElixirCapacity()) {
                for(int i = elixirIndex; i < elixirContainables.size() - 1; i++) {
                    elixirContainables.get(i).setElixir(elixirAvg);
                    elixirToDistribute -= elixirAvg;
                }
                elixirContainables.get(elixirContainables.size() - 1).setElixir(elixirToDistribute);
                elixirToDistribute = 0;
            }
            else {
                ElixirContainable currElixirContainer = elixirContainables.get(elixirIndex);
                currElixirContainer.setElixir(currElixirContainer.getElixirCapacity());
                elixirToDistribute -= currElixirContainer.getElixirCapacity();
                elixirIndex += 1;
            }
        }

    }

    public void loadBattleMap() {
        loadConfig();
        if(battleMapObjects != null) {
            return;
        }
        ArrayList<MapObject> mapObjects = ID_TO_BATTLE_MAP.get(id);
        battleMapObjects = new ArrayList<>();
        for(MapObject mapObject: mapObjects) {
            battleMapObjects.add(mapObject.clone());
        }
        distributeResource();
    }

    public int getId() {
        return id;
    }

    public int getStar() {
        return star;
    }

    public int getAvailGold() {
        return availGold;
    }

    public int getAvailElixir() {
        return availElixir;
    }

    public ArrayList<MapObject> getBattleMapObjects() {
        if(battleMapObjects == null) {
            loadBattleMap();
        }
        return battleMapObjects;
    }
}