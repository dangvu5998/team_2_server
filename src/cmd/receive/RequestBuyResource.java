package cmd.receive;

import bitzero.server.extensions.data.BaseCmd;
import bitzero.server.extensions.data.DataCmd;
import cmd.RequestConst;

import java.nio.ByteBuffer;

public class RequestBuyResource extends BaseCmd {
    private int resType;
    private int amount;
    private int status;

    public static final int G_TYPE = 0;
    public static final int GOLD_TYPE = 1;
    public static final int ELIXIR_TYPE = 2;

    public RequestBuyResource(DataCmd dataCmd) {
        super(dataCmd);
        unpackData();
    }

    @Override
    public void unpackData() {
        ByteBuffer bf = makeBuffer();
        try {
            resType = readInt(bf);
            amount = readInt(bf);
            status = RequestConst.OK;
        } catch (Exception e) {
            status = RequestConst.INVALID;
        }
    }

    public int getStatus() {
        return status;
    }

    public int getAmount() {
        return amount;
    }

    public int getResType() {
        return resType;
    }

}
