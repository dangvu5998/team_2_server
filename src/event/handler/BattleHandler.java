package event.handler;

import bitzero.server.entities.User;
import bitzero.server.extensions.BaseClientRequestHandler;
import bitzero.server.extensions.data.DataCmd;
import cmd.CmdDefine;
import cmd.RequestConst;
import cmd.ResponseConst;
import cmd.receive.battle.RequestSingleBattleList;
import cmd.send.battle.ResponseSingleBattleList;
import model.battle.SingleBattlePlayer;

public class BattleHandler extends BaseClientRequestHandler {
    public final static short BATTLE_IDS = 5000;

    @Override
    public void handleClientRequest(User user, DataCmd dataCmd) {
        if(dataCmd.getId() == CmdDefine.LOAD_SINGLE_BATTLE_LIST)
            processLoadSingleBattleList(user, dataCmd);



    }

    private void processLoadSingleBattleList(User user, DataCmd dataCmd) {
        System.out.println(user.getId());
        RequestSingleBattleList requestSingleBattleList = new RequestSingleBattleList(dataCmd);
        if(requestSingleBattleList.getStatus() == RequestConst.OK) {
            int reqId = requestSingleBattleList.getClientReqId();
            int id = user.getId();
            SingleBattlePlayer singleBattlePlayer = SingleBattlePlayer.getBattleSinglePlayerById(id);
            if(singleBattlePlayer == null) {
                singleBattlePlayer = SingleBattlePlayer.createBattleSinglePlayer(id);
                singleBattlePlayer.save();
            }
            send(new ResponseSingleBattleList(reqId, singleBattlePlayer.getBattles()), user);


        }
    }
}
