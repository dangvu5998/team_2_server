package cmd.send;

import bitzero.server.extensions.data.BaseMsg;
import cmd.CmdDefine;
import cmd.ResponseConst;

import java.nio.ByteBuffer;

public class ResponseTimeStamp extends BaseMsg {
    private final int currentTime;
    public ResponseTimeStamp(int currentTime) {
        super(CmdDefine.CURRENT_TIME);
        this.currentTime = currentTime;
    }

    @Override
    public byte[] createData() {
        ByteBuffer bf = makeBuffer();
        bf.putInt(ResponseConst.OK);
        bf.putInt(currentTime);
        return packBuffer(bf);
    }
}
