package cmd.send.battle;

import bitzero.server.extensions.data.BaseMsg;
import cmd.CmdDefine;
import cmd.ResponseConst;
import model.battle.SingleBattle;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class ResponseSingleBattleList extends BaseMsg {
    ArrayList<SingleBattle> singleBattles;
    int reqId;
    public ResponseSingleBattleList(int reqId, ArrayList<SingleBattle> singleBattles) {
        super(CmdDefine.LOAD_SINGLE_BATTLE_LIST);
        this.singleBattles = singleBattles;
        this.reqId = reqId;
    }

    @Override
    public byte[] createData() {
        ByteBuffer bf = makeBuffer();
        bf.putInt(ResponseConst.OK);
        bf.putInt(reqId);
        bf.putInt(singleBattles.size());
        for(SingleBattle singleBattle: singleBattles) {
            bf.putInt(singleBattle.getId());
            bf.putInt(singleBattle.getStar());
            bf.putInt(singleBattle.getAvailGold());
            bf.putInt(singleBattle.getAvailElixir());
        }
        return packBuffer(bf);
    }

}
