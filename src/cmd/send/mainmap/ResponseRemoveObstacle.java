package cmd.send.mainmap;

import bitzero.server.extensions.data.BaseMsg;
import cmd.CmdDefine;

import java.nio.ByteBuffer;

public class ResponseRemoveObstacle extends BaseMsg {

    public static final int INVALID_MAP_OBJ_ID = -1;
    public static final int INVALID_MAP_OBJ_STATUS = -2;
    public static final int NOT_ENOUGH_BUILDER = -3;
    public static final int NOT_ENOUGH_RESOURCES = -4;
    public static final int NO_ERROR = 0;

    private final int status;
    private final int mapObjId;
    private final int error;
    public ResponseRemoveObstacle(int status, int buildingId, int error) {
        super(CmdDefine.REMOVE_OBSTACLE);
        this.status = status;
        this.mapObjId = buildingId;
        this.error = error;
    }

    public ResponseRemoveObstacle(int status, int buildingId) {
        this(status, buildingId, NO_ERROR);
    }

    @Override
    public byte[] createData() {
        ByteBuffer bf = makeBuffer();
        bf.putInt(status);
        bf.putInt(mapObjId);
        bf.putInt(error);
        return packBuffer(bf);
    }
}
