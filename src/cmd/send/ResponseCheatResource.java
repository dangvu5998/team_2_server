package cmd.send;

import bitzero.server.extensions.data.BaseMsg;
import cmd.CmdDefine;

import java.nio.ByteBuffer;

public class ResponseCheatResource extends BaseMsg {
    private final int reqClientId;
    private final int status;
    private final int error;

    public static final int NO_ERROR = 0;
    public static final int INVALID_AMOUNT = -1;
    public static final int INVALID_RES_TYPE = -2;

    public ResponseCheatResource(int status, int reqClientId, int error) {
        super(CmdDefine.CHEAT_RESOURCE);
        this.status = status;
        this.reqClientId = reqClientId;
        this.error = error;
    }

    public ResponseCheatResource(int status, int reqClientId) {
        this(status, reqClientId, NO_ERROR);
    }

    @Override
    public byte[] createData() {
        ByteBuffer bf = makeBuffer();
        bf.putInt(status);
        bf.putInt(reqClientId);
        bf.putInt(error);
        return packBuffer(bf);
    }
}
