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
    public ResponseSelectSingleBattle(int reqId, int battleSessId) {
        super(CmdDefine.SELECT_SINGLE_BATTLE);
        this.reqId = reqId;
        this.battleSessId = battleSessId;
        this.status = ResponseConst.OK;
        this.error = 0;
    }

    @Override
    public byte[] createData() {
        ByteBuffer bf = makeBuffer();
        bf.putInt(status);
        bf.putInt(reqId);
        bf.putInt(battleSessId);
        bf.putInt(error);
        return packBuffer(bf);
    }
}
