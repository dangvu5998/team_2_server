package cmd.receive.mainmap;
import bitzero.server.extensions.data.BaseCmd;
import bitzero.server.extensions.data.DataCmd;
import cmd.RequestConst;

import java.nio.ByteBuffer;

public class RequestQuickFinish extends BaseCmd {
    private int mapObjId;
    private int status;
    private int gToQF;

    public RequestQuickFinish(DataCmd dataCmd) {
        super(dataCmd);
        unpackData();
    }

    @Override
    public void unpackData() {
        ByteBuffer bf = makeBuffer();
        try {
            mapObjId = readInt(bf);
            gToQF = readInt(bf);
            status = RequestConst.OK;
        } catch (Exception e) {
            status = RequestConst.INVALID;
        }
    }

    public int getMapObjId() {
        return mapObjId;
    }

    public int getStatus() {
        return status;
    }

    public int getgToQF() {
        return gToQF;
    }

}
