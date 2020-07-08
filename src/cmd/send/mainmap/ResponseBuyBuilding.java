package cmd.send.mainmap;

import bitzero.server.extensions.data.BaseMsg;
import cmd.CmdDefine;

import java.nio.ByteBuffer;

public class ResponseBuyBuilding extends BaseMsg {
    private final int status;
    private final int buildingTypeId;
    private final int x;
    private final int y;
    private final int buildingId;

    public static final int OBJECT_TYPE_INVALID = -1;
    public static final int NOT_ENOUGH_BUILDERS = -2;
    public static final int FULL_BUILDINGS = -3;
    public static final int BUILDING_OVERLAP = -4;
    public static final int NOT_ENOUGH_GOLD = -5;
    public static final int NOT_ENOUGH_ELIXIR = -6;
    public static final int NOT_ENOUGH_G = -7;

    public ResponseBuyBuilding(int status, int buildingTypeId, int x, int y, int buildingId) {
        super(CmdDefine.BUY_BUILDING);
        this.status = status;
        this.x = x;
        this.y = y;
        this.buildingId = buildingId;
        this.buildingTypeId = buildingTypeId;
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
