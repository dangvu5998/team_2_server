package cmd.receive.battle;

import bitzero.server.extensions.data.BaseCmd;
import bitzero.server.extensions.data.DataCmd;
import cmd.RequestConst;

import java.nio.ByteBuffer;

public class RequestSingleBattleList extends BaseCmd {
    private int status;
    private int clientReqId;

    public RequestSingleBattleList(DataCmd dataCmd) {
        super(dataCmd);
        unpackData();
    }

    @Override
    public void unpackData() {
        ByteBuffer bf = makeBuffer();
        try {
            clientReqId = readInt(bf);
            status = RequestConst.OK;
        } catch (Exception e) {
            status = RequestConst.INVALID;
        }
    }

    public int getStatus() {
        return status;
    }

    public int getClientReqId() {
        return clientReqId;
    }

}
