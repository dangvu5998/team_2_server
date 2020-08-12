package cmd.receive.battle;

import bitzero.server.extensions.data.BaseCmd;
import bitzero.server.extensions.data.DataCmd;
import cmd.RequestConst;

import java.nio.ByteBuffer;

public class RequestEndBattle extends BaseCmd {
    private int status;
    private int clientReqId;
    private int timestep;
    private int sessBattleId;
    private int availGold;
    private int availElixir;
    private int proportionDestroyed;

    public RequestEndBattle(DataCmd dataCmd) {
        super(dataCmd);
        unpackData();
    }

    @Override
    public void unpackData() {
        ByteBuffer bf = makeBuffer();
        try {
            clientReqId = readInt(bf);
            sessBattleId = readInt(bf);
            timestep = readInt(bf);
            availGold = readInt(bf);
            availElixir = readInt(bf);
            proportionDestroyed = readInt(bf);
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

    public int getTimestep() {
        return timestep;
    }

    public int getSessBattleId() {
        return sessBattleId;
    }

    public int getAvailGold() {
        return availGold;
    }

    public int getAvailElixir() {
        return availElixir;
    }

    public int getProportionDestroyed() {
        return proportionDestroyed;
    }
}
