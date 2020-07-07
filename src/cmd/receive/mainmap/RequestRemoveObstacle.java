package cmd.receive.mainmap;

import bitzero.server.extensions.data.BaseCmd;
import bitzero.server.extensions.data.DataCmd;
import cmd.RequestConst;

import java.nio.ByteBuffer;

public class RequestRemoveObstacle extends BaseCmd {
    private int mapObjId;
    private int status;

    public RequestRemoveObstacle(DataCmd dataCmd) {
        super(dataCmd);
        unpackData();
    }

    @Override
    public void unpackData() {
        ByteBuffer bf = makeBuffer();
        try {
            mapObjId = readInt(bf);
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

}
