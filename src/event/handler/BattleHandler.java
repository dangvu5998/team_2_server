package event.handler;

import bitzero.server.entities.User;
import bitzero.server.extensions.BaseClientRequestHandler;
import bitzero.server.extensions.data.DataCmd;
import cmd.CmdDefine;
import cmd.RequestConst;
import cmd.ResponseConst;
import cmd.receive.battle.RequestDropSoldier;
import cmd.receive.battle.RequestEndBattle;
import cmd.receive.battle.RequestSelectSingleBattle;
import cmd.receive.battle.RequestSingleBattleList;
import cmd.send.battle.ResponseSingleBattleList;
import model.battle.SingleBattlePlayer;

public class BattleHandler extends BaseClientRequestHandler {
    public final static short BATTLE_IDS = 5000;

    @Override
    public void handleClientRequest(User user, DataCmd dataCmd) {
        switch (dataCmd.getId()) {
            case CmdDefine.LOAD_SINGLE_BATTLE_LIST:
                processLoadSingleBattleList(user, dataCmd);
                break;
            case CmdDefine.SELECT_SINGLE_BATTLE:
                processSelectBattle(user, dataCmd);
                break;
            case CmdDefine.DROP_SOLDIERS:
                processDropSoldiers(user, dataCmd);
                break;
            case CmdDefine.END_BATTLE:
                processEndBattle(user, dataCmd);
                break;
        }
    }

    private void processEndBattle(User user, DataCmd dataCmd) {
        RequestEndBattle requestEndBattle = new RequestEndBattle(dataCmd);
        if(requestEndBattle.getStatus() == RequestConst.OK) {

        }
    }

    private void processDropSoldiers(User user, DataCmd dataCmd) {
        RequestDropSoldier requestDropSoldier = new RequestDropSoldier(dataCmd);
        if(requestDropSoldier.getStatus() == RequestConst.OK) {

        }
    }

    private void processSelectBattle(User user, DataCmd dataCmd) {
        RequestSelectSingleBattle requestSelectSingleBattle = new RequestSelectSingleBattle(dataCmd);
        if(requestSelectSingleBattle.getStatus() == RequestConst.OK) {
            int reqId = requestSelectSingleBattle.getClientReqId();
            int id = user.getId();
        }
    }

    private void processLoadSingleBattleList(User user, DataCmd dataCmd) {
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
