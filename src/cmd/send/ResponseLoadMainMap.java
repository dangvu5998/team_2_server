package cmd.send;

import bitzero.server.extensions.data.BaseMsg;
import cmd.CmdDefine;

import java.nio.ByteBuffer;

public class ResponseLoadMainMap extends BaseMsg {
    private int status;
    public ResponseLoadMainMap(int status) {
        super(CmdDefine.LOAD_MAIN_MAP);
        this.status = status;
    }


    @Override
    public byte[] createData() {
        ByteBuffer bf = makeBuffer();
        bf.putInt(status);
        return packBuffer(bf);
    }
}
