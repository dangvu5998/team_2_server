package cmd.send;

import bitzero.server.extensions.data.BaseMsg;
import cmd.CmdDefine;
import cmd.receive.RequestBuyResource;

import java.nio.ByteBuffer;

public class ResponseBuyResource extends BaseMsg {
    private final int resType;
    private final int amount;
    private final int status;
    private final int error;

    public static final int NO_ERROR = 0;
    public static final int NOT_ENOUGH_G = 1;
    public static final int INVALID_RES_TYPE = 2;

    public ResponseBuyResource(int status, int resType, int amount, int error) {
        super(CmdDefine.BUY_RESOURCE);
        this.resType = resType;
        this.amount = amount;
        this.status = status;
        this.error = error;
    }

    public ResponseBuyResource(int status, int resType, int amount) {
        this(status, resType, amount, NO_ERROR);
    }

    @Override
    public byte[] createData() {
        ByteBuffer bf = makeBuffer();
        bf.putInt(status);
        bf.putInt(resType);
        bf.putInt(amount);
        bf.putInt(error);
        return packBuffer(bf);
    }

}
