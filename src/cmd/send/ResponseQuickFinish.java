package cmd.send;

import bitzero.server.extensions.data.BaseMsg;
import cmd.CmdDefine;

import java.nio.ByteBuffer;

public class ResponseQuickFinish extends BaseMsg {
    private final int status;
    private final int mapObjId;
    private final int error;

    public static final int INVALID_MAP_OBJECT = -1;
    public static final int INVALID_MAP_OBJECT_STATUS = -2;
    public static final int NOT_ENOUGH_G = -3;
    public static final int INVALID_G = -4;
    public static final int NO_ERROR = 0;
    public ResponseQuickFinish(int status, int buildingId, int error) {
        super(CmdDefine.QUICK_FINISH_MAP_OBJECT);
        this.status = status;
        this.mapObjId = buildingId;
        this.error = error;
    }

    public ResponseQuickFinish(int status, int buildingId) {
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
