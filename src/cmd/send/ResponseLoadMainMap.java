package cmd.send;

import bitzero.server.extensions.data.BaseMsg;
import cmd.CmdDefine;
import model.map.*;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class ResponseLoadMainMap extends BaseMsg {
    private int status;
    private ArrayList<MapObject> mapObjects;
    public ResponseLoadMainMap(int status, ArrayList<MapObject> mapObjects) {
        super(CmdDefine.LOAD_MAIN_MAP);
        this.status = status;
        this.mapObjects = mapObjects;
    }


    @Override
    public byte[] createData() {
        ByteBuffer bf = makeBuffer();
        bf.putInt(status);
        bf.putInt(mapObjects.size());
        for(MapObject mapObject: mapObjects) {
            bf.putInt(mapObject.getId());
            bf.putInt(mapObject.getObjectType());
            bf.putInt(mapObject.getX());
            bf.putInt(mapObject.getY());

            if(mapObject instanceof Building) {
                // for building
                Building building = (Building) mapObject;
                // status
                bf.putInt(building.getStatus());
                // finish time
                bf.putInt(building.getFinishTime());
                // level
                bf.putInt(building.getLevel());
                if(building instanceof MineBuilding) {
                    // for miner
                    MineBuilding mineBuilding = (MineBuilding) building;
                    System.out.println(mineBuilding.getObjectType());
                    // last time collectted
                    bf.putInt(mineBuilding.getLastTimeCollected());
                }
                // for gold and elixir storage
                if(building instanceof GoldStorage) {
                    GoldStorage goldStorage = (GoldStorage) building;
                    bf.putInt(goldStorage.getGold());
                }
                if(building instanceof ElixirStorage) {
                    ElixirStorage elixirStorage = (ElixirStorage) building;
                    bf.putInt(elixirStorage.getElixir());
                }
                if(building instanceof Townhall) {
                    Townhall townhall = (Townhall) building;
                    bf.putInt(townhall.getGold());
                    bf.putInt(townhall.getElixir());
                    bf.putInt(townhall.getDarkElixir());
                }
            }
            if(mapObject instanceof Obstacle) {
                Obstacle obstacle = (Obstacle) mapObject;
                // status
                bf.putInt(obstacle.getStatus());
                // finish time
                bf.putInt(obstacle.getFinishTime());
            }
        }
        return packBuffer(bf);
    }
}
