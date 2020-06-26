package cmd.send;

import bitzero.server.extensions.data.BaseMsg;
import cmd.CmdDefine;
import model.map.MapObject;

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
            System.out.println("======");
            System.out.println(mapObject.getX() + " " + mapObject.getY());
            System.out.println(mapObject.getObjectType());
            System.out.println("======");
            switch (mapObject.getObjectType()) {
                case MapObject.TOWNHALL -> {
                    bf.putInt(0); // status
                    bf.putInt(0); // finishTime
                    bf.putInt(1); // level
                    bf.putInt(0); // gold
                    bf.putInt(0); // elixir
                    bf.putInt(0); // black elixir
                }
                case MapObject.GOLD_STORAGE, MapObject.ELIXIR_STORAGE -> {
                    bf.putInt(0); // status
                    bf.putInt(0); // finishTime
                    bf.putInt(1); // level
                    bf.putInt(0); // resources
                }
            }
        }
        return packBuffer(bf);
    }
}
