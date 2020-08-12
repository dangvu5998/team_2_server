package cmd.receive.battle;

import bitzero.server.extensions.data.BaseCmd;
import bitzero.server.extensions.data.DataCmd;
import cmd.RequestConst;
import model.battle.BattleSession;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class RequestSelectSingleBattle extends BaseCmd {
    private int status;
    private int clientReqId;
    private int battleId;
    private ArrayList<BattleSession.SoldierNumber> soldierNumbers;

    public RequestSelectSingleBattle(DataCmd dataCmd) {
        super(dataCmd);
        unpackData();
    }

    @Override
    public void unpackData() {
        ByteBuffer bf = makeBuffer();
        soldierNumbers = new ArrayList<>();
        try {
            clientReqId = readInt(bf);
            battleId = readInt(bf);
            int nbOfSoldierTypes = readInt(bf);
            for(int i = 0; i < nbOfSoldierTypes; i++) {
                String soldierType = readString(bf);
                int nbOfSoldier = readInt(bf);
                soldierNumbers.add(new BattleSession.SoldierNumber(soldierType, nbOfSoldier));
            }
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

    public ArrayList<BattleSession.SoldierNumber> getSoldierNumbers() {
        return soldierNumbers;
    }

    public int getBattleId() {
        return battleId;
    }
}
