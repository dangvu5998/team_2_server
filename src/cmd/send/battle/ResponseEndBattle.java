package cmd.send.battle;

import bitzero.server.extensions.data.BaseMsg;
import cmd.CmdDefine;
import cmd.ResponseConst;
import cmd.receive.battle.RequestEndBattle;

import java.nio.ByteBuffer;

public class ResponseEndBattle extends BaseMsg {
    private final int reqId;
    private final int status;
    private final int star;
    private final int goldEarned;
    private final int elixirEarned;
    private final int error;

    public static final int NO_ERROR = 0;
    public static final int INVALID_SESSION_ID = -1;

    public ResponseEndBattle(int reqId, int star, int goldEarned, int elixirEarned) {
        super(CmdDefine.END_BATTLE);
        this.status = ResponseConst.OK;
        this.reqId = reqId;
        this.star = star;
        this.goldEarned = goldEarned;
        this.elixirEarned = elixirEarned;
        error = NO_ERROR;
    }

    public ResponseEndBattle(int reqId, int error) {
        super(CmdDefine.END_BATTLE);
        status = ResponseConst.SEMANTIC_INVALID;
        this.reqId = reqId;
        this.error = error;
        this.star = 0;
        this.goldEarned = 0;
        this.elixirEarned = 0;
    }


    @Override
    public byte[] createData() {
        ByteBuffer bf = makeBuffer();
        if(status == ResponseConst.OK) {
            bf.putInt(status);
            bf.putInt(reqId);
            bf.putInt(star);
            bf.putInt(goldEarned);
            bf.putInt(elixirEarned);
            bf.putInt(error);
        }
        else {
            bf.putInt(status);
            bf.putInt(reqId);
            bf.putInt(error);
        }
        return packBuffer(bf);
    }
}
