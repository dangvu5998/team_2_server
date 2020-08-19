package cmd.send.battle;

import bitzero.server.extensions.data.BaseMsg;
import cmd.CmdDefine;
import cmd.ResponseConst;

import java.nio.ByteBuffer;

public class ResponseDropSoldier extends BaseMsg {
    private final int reqId;
    private final int status;
    private final int error;

    public static final int NO_ERROR = 0;
    public static final int INVALID_SOLDIER_TYPE = -1;
    public static final int INVALID_SESSION_ID = -3;
    public static final int INVALID_TIME_STEP = -4;

    public ResponseDropSoldier(int reqId) {
        super(CmdDefine.DROP_SOLDIERS);
        this.reqId = reqId;
        status = ResponseConst.OK;
        error = NO_ERROR;
    }

    public ResponseDropSoldier(int reqId, int error) {
        super(CmdDefine.DROP_SOLDIERS);
        status = ResponseConst.OK;
        this.reqId = reqId;
        this.error = error;
    }

    @Override
    public byte[] createData() {
        ByteBuffer bf = makeBuffer();
        bf.putInt(status);
        bf.putInt(reqId);
        bf.putInt(error);
        return packBuffer(bf);
    }
}
