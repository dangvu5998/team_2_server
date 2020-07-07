package cmd.send;

import bitzero.server.extensions.data.BaseMsg;
import cmd.CmdDefine;

import java.nio.ByteBuffer;

public class ResponseCancelBuilding extends BaseMsg {
    private final int status;
    private final int buildingId;
    private final int error;

    public static final int INVALID_BUILDING_ID = -1;
    public static final int INVALID_BUILDING_STATUS = -2;
    public static final int NO_ERROR = 0;
    public ResponseCancelBuilding(int status, int buildingId, int error) {
        super(CmdDefine.CANCEL_BUILDING);
        this.status = status;
        this.buildingId = buildingId;
        this.error = error;
    }

    public ResponseCancelBuilding(int status, int buildingId) {
        this(status, buildingId, NO_ERROR);
    }

    @Override
    public byte[] createData() {
        ByteBuffer bf = makeBuffer();
        bf.putInt(status);
        bf.putInt(buildingId);
        bf.putInt(error);
        return packBuffer(bf);
    }
}
