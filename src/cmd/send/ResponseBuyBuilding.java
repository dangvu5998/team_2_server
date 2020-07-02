package cmd.send;

import bitzero.server.extensions.data.BaseMsg;
import cmd.CmdDefine;
import cmd.RequestConst;
import model.map.Building;

import java.nio.ByteBuffer;

public class ResponseBuyBuilding extends BaseMsg {
    private final int status;
    private final int buildingTypeId;
    private final int x;
    private final int y;
    private final int buildingId;

    public ResponseBuyBuilding(int status, int buildingTypeId, int x, int y, int buildingId) {
        super(CmdDefine.BUY_BUILDING);
        this.status = status;
        this.x = x;
        this.y = y;
        this.buildingId = buildingId;
        this.buildingTypeId = buildingTypeId;
    }

    public ResponseBuyBuilding(int status, int buildingTypeId, int x, int y) {
        this(status, buildingTypeId, x, y, -1);
        if(status == RequestConst.OK) {
            throw new RuntimeException("Success buy building response need building id");
        }
    }

    @Override
    public byte[] createData() {
        ByteBuffer bf = makeBuffer();
        bf.putInt(status);
        bf.putInt(buildingTypeId);
        bf.putInt(x);
        bf.putInt(y);
        bf.putInt(buildingId);
        return packBuffer(bf);
    }
}
