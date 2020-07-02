package cmd.receive.mainmap;

import bitzero.server.extensions.data.BaseCmd;
import bitzero.server.extensions.data.DataCmd;
import bitzero.util.common.business.CommonHandle;
import cmd.RequestConst;

import java.nio.ByteBuffer;

public class RequestBuyBuilding extends BaseCmd {
    private int buildingTypeId;
    private int x;
    private int y;
    private int status;

    public RequestBuyBuilding(DataCmd dataCmd) {
        super(dataCmd);
        unpackData();
    }

    @Override
    public void unpackData() {
        ByteBuffer bf = makeBuffer();
        try {
            buildingTypeId = readInt(bf);
            x = readInt(bf);
            y = readInt(bf);
            status = RequestConst.OK;
        } catch (Exception e) {
            status = RequestConst.INVALID;
            CommonHandle.writeErrLog(e);
        }
    }

    public int getBuildingTypeId() {
        return buildingTypeId;
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
}
