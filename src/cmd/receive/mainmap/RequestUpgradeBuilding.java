package cmd.receive.mainmap;
import bitzero.server.extensions.data.BaseCmd;
import bitzero.server.extensions.data.DataCmd;
import cmd.RequestConst;

import java.nio.ByteBuffer;


public class RequestUpgradeBuilding extends BaseCmd {
    private int buildingId;
    private int status;

    public RequestUpgradeBuilding(DataCmd dataCmd) {
        super(dataCmd);
        unpackData();
    }

    @Override
    public void unpackData() {
        ByteBuffer bf = makeBuffer();
        try {
            buildingId = readInt(bf);
            status = RequestConst.OK;
        } catch (Exception e) {
            status = RequestConst.INVALID;
        }
    }

    public int getBuildingId() {
        return buildingId;
    }

    public int getStatus() {
        return status;
    }
}
