package cmd.receive.battle;

import bitzero.server.extensions.data.BaseCmd;
import bitzero.server.extensions.data.DataCmd;
import cmd.RequestConst;
import model.battle.BattleSession;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class RequestDropSoldier extends BaseCmd {
    private int status;
    private int clientReqId;
    private int sessBattleId;
    private ArrayList<BattleSession.DropSoldier> dropSoldiers;

    public RequestDropSoldier(DataCmd dataCmd) {
        super(dataCmd);
        unpackData();
    }

    @Override
    public void unpackData() {
        ByteBuffer bf = makeBuffer();
        dropSoldiers = new ArrayList<>();
        try {
            clientReqId = readInt(bf);
            sessBattleId = readInt(bf);
            int nbOfSoldDrops = readInt(bf);
            for(int i = 0; i < nbOfSoldDrops; i++) {
                String soldType = readString(bf);
                int x = readInt(bf);
                int y = readInt(bf);
                int timestep = readInt(bf);
                dropSoldiers.add(new BattleSession.DropSoldier(soldType, x, y, timestep));
            }
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

    public ArrayList<BattleSession.DropSoldier> getDropSoldiers() {
        return dropSoldiers;
    }

    public int getSessBattleId() {
        return sessBattleId;
    }
}
