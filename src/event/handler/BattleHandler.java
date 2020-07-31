package event.handler;

import bitzero.server.entities.User;
import bitzero.server.extensions.BaseClientRequestHandler;
import bitzero.server.extensions.data.DataCmd;
import cmd.CmdDefine;
import cmd.RequestConst;
import cmd.receive.battle.RequestDropSoldier;
import cmd.receive.battle.RequestEndBattle;
import cmd.receive.battle.RequestSelectSingleBattle;
import cmd.receive.battle.RequestSingleBattleList;
import cmd.send.battle.ResponseDropSoldier;
import cmd.send.battle.ResponseSelectSingleBattle;
import cmd.send.battle.ResponseSingleBattleList;
import model.battle.BattleSession;
import model.battle.SingleBattlePlayer;
import model.soldier.Soldier;
import util.Common;

import java.util.ArrayList;

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
            int userId = user.getId();
            int reqId = requestDropSoldier.getClientReqId();
            int sessId = requestDropSoldier.getSessBattleId();
            ArrayList<BattleSession.DropSoldier> dropSoldiers = requestDropSoldier.getDropSoldiers();
            BattleSession battleSession = BattleSession.getOrCreateBattleSessionById(userId);
            if(sessId != battleSession.getBattleId()) {
                send(new ResponseDropSoldier(reqId, ResponseDropSoldier.INVALID_SESSION_ID), user);
                return;
            }
            ArrayList<BattleSession.DropSoldier> currDropSodiers = battleSession.getDropSoldiers();
            boolean isInvalidSoldierType = false;
            for(BattleSession.DropSoldier dropSoldier: dropSoldiers) {
                // TODO: validate number soldier
                if(Soldier.SOLDIER_TYPES.contains(dropSoldier.getSoldierType())) {
                    currDropSodiers.add(dropSoldier);
                }
                else
                {
                    isInvalidSoldierType = true;
                    break;
                }
            }
            if(isInvalidSoldierType) {
                send(new ResponseDropSoldier(reqId, ResponseDropSoldier.INVALID_SOLDIER_TYPE), user);
                return;
            }
            battleSession.setDropSoldiers(currDropSodiers);
            battleSession.save();
            send(new ResponseDropSoldier(reqId), user);
        }
    }

    private void processSelectBattle(User user, DataCmd dataCmd) {
        RequestSelectSingleBattle requestSelectSingleBattle = new RequestSelectSingleBattle(dataCmd);
        if(requestSelectSingleBattle.getStatus() == RequestConst.OK) {
            int reqId = requestSelectSingleBattle.getClientReqId();
            int battleId = requestSelectSingleBattle.getBattleId();
            ArrayList<BattleSession.SoldierNumber> soldierNumbers = requestSelectSingleBattle.getSoldierNumbers();
            int id = user.getId();
            BattleSession battleSession = BattleSession.getOrCreateBattleSessionById(id);
            battleSession.setBattleId(battleId);
            // TODO: check valid available soldier
            battleSession.setAvailSoldiers(soldierNumbers);
            int currTime = Common.currentTimeInSecond();
            battleSession.setStartTime(currTime);
            battleSession.setSessionId(currTime);
            battleSession.setDropSoldiers(new ArrayList<>());
            battleSession.save();
            send(new ResponseSelectSingleBattle(reqId, currTime), user);
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
