package cmd.receive.mainmap;

import bitzero.server.extensions.data.BaseCmd;
import bitzero.server.extensions.data.DataCmd;
import bitzero.util.common.business.CommonHandle;
import cmd.RequestConst;

import java.nio.ByteBuffer;

public class RequestMoveBuilding extends BaseCmd {
    private int buildingId;
    private int status;
    private int x;
    private int y;

    public int getBuildingId() {
        return buildingId;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getStatus() {
        return status;
    }

    public RequestMoveBuilding(DataCmd dataCmd) {
        super(dataCmd);
        unpackData();
    }

    @Override
    public void unpackData() {
        ByteBuffer bf = makeBuffer();
        try {
            buildingId = readInt(bf);
            x = readInt(bf);
            y = readInt(bf);
            status = RequestConst.OK;
        } catch (Exception e) {
            status = RequestConst.INVALID;
            CommonHandle.writeErrLog(e);
        }
    }
}
