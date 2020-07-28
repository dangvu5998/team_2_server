package cmd.receive.battle;

import bitzero.server.extensions.data.BaseCmd;
import bitzero.server.extensions.data.DataCmd;
import cmd.RequestConst;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class RequestDropSoldier extends BaseCmd {
    private int status;
    private int clientReqId;
    private int sessBattleId;
    private ArrayList<SoldierDrop> soldierDrops;

    public class SoldierDrop {
        private final String soldierType;
        private final int x;
        private final int y;
        private final int timestep;

        public SoldierDrop(String soldierType, int x, int y, int timestep) {
            this.soldierType = soldierType;
            this.x = x;
            this.y = y;
            this.timestep = timestep;
        }

        public String getSoldierType() {
            return soldierType;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getTimestep() {
            return timestep;
        }
    }

    public RequestDropSoldier(DataCmd dataCmd) {
        super(dataCmd);
        unpackData();
    }

    @Override
    public void unpackData() {
        ByteBuffer bf = makeBuffer();
        soldierDrops = new ArrayList<>();
        try {
            clientReqId = readInt(bf);
            sessBattleId = readInt(bf);
            int nbOfSoldDrops = readInt(bf);
            for(int i = 0; i < nbOfSoldDrops; i++) {
                String soldType = readString(bf);
                int x = readInt(bf);
                int y = readInt(bf);
                int timestep = readInt(bf);
                soldierDrops.add(new SoldierDrop(soldType, x, y, timestep));
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

    public ArrayList<SoldierDrop> getSoldierDrops() {
        return soldierDrops;
    }

    public int getSessBattleId() {
        return sessBattleId;
    }
}
