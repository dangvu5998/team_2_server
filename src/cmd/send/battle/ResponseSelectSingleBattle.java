package cmd.send.battle;

import bitzero.server.extensions.data.BaseMsg;
import cmd.CmdDefine;
import cmd.ResponseConst;

import java.nio.ByteBuffer;

public class ResponseSelectSingleBattle extends BaseMsg {
    private final int reqId;
    private final int status;
    private final int error;
    private final int battleSessId;

    public static final int NO_ERROR = 0;
    public static final int INVALID_BATTLE_ID = -1;
    public ResponseSelectSingleBattle(int error, int reqId, int battleSessId) {
        super(CmdDefine.SELECT_SINGLE_BATTLE);
        this.reqId = reqId;
        this.battleSessId = battleSessId;
        this.status = ResponseConst.OK;
        this.error = error;
    }

    public ResponseSelectSingleBattle(int error, int reqId) {
        this(error, reqId, -1);
    }

    @Override
    public byte[] createData() {
        ByteBuffer bf = makeBuffer();
        if(error == NO_ERROR) {
            bf.putInt(status);
            bf.putInt(reqId);
            bf.putInt(battleSessId);
        }
        else {
            bf.putInt(status);
            bf.putInt(reqId);
            bf.putInt(error);
        }
        return packBuffer(bf);
    }
}
