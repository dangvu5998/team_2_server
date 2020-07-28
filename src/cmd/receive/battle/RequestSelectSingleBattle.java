package cmd.receive.battle;

import bitzero.server.extensions.data.BaseCmd;
import bitzero.server.extensions.data.DataCmd;
import cmd.RequestConst;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class RequestSelectSingleBattle extends BaseCmd {
    private int status;
    private int clientReqId;
    private ArrayList<SoldierNumber> soldierNumbers;

    public static class SoldierNumber {
        private final String soldierType;
        private final int nbOfSoldier;

        public SoldierNumber(String type, int number) {
            soldierType = type;
            nbOfSoldier = number;
        }

        public String getSoldierType() {
            return soldierType;
        }

        public int getNbOfSoldier() {
            return nbOfSoldier;
        }
    }

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
            int nbOfSoldierTypes = readInt(bf);
            for(int i = 0; i < nbOfSoldierTypes; i++) {
                String soldierType = readString(bf);
                int nbOfSoldier = readInt(bf);
                soldierNumbers.add(new SoldierNumber(soldierType, nbOfSoldier));
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

    public ArrayList<SoldierNumber> getSoldierNumbers() {
        return soldierNumbers;
    }
}
