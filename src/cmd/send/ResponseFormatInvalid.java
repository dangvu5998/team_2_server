package cmd.send;

import bitzero.server.extensions.data.BaseMsg;
import cmd.ResponseConst;

import java.nio.ByteBuffer;

public class ResponseFormatInvalid extends BaseMsg {
    public ResponseFormatInvalid(short cmdId) {
        super(cmdId);
    }

    @Override
    public byte[] createData() {
        ByteBuffer bf = makeBuffer();
        bf.putInt(ResponseConst.FORMAT_INVALID);
        return packBuffer(bf);
    }
}
