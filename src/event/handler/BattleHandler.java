package event.handler;

import bitzero.server.entities.User;
import bitzero.server.extensions.BaseClientRequestHandler;
import bitzero.server.extensions.data.DataCmd;
import bitzero.util.common.business.CommonHandle;
import cmd.CmdDefine;
import cmd.RequestConst;
import cmd.receive.battle.RequestDropSoldier;
import cmd.receive.battle.RequestEndBattle;
import cmd.receive.battle.RequestSelectSingleBattle;
import cmd.receive.battle.RequestSingleBattleList;
import cmd.send.battle.ResponseDropSoldier;
import cmd.send.battle.ResponseEndBattle;
import cmd.send.battle.ResponseSelectSingleBattle;
import cmd.send.battle.ResponseSingleBattleList;
import model.GameUser;
import model.battle.BattleSession;
import model.battle.SingleBattle;
import model.battle.SingleBattlePlayer;
import model.soldier.Soldier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Common;

import java.util.ArrayList;

public class BattleHandler extends BaseClientRequestHandler {
    public static final Logger logger = LoggerFactory.getLogger("BattleHandler");
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
                try {
                    processEndBattle(user, dataCmd);
                }catch (Exception e) {
                    CommonHandle.writeErrLog(e);
                }
                break;
        }
    }

    private void processEndBattle(User user, DataCmd dataCmd) {
        RequestEndBattle requestEndBattle = new RequestEndBattle(dataCmd);
        if(requestEndBattle.getStatus() == RequestConst.OK) {
            int userId = user.getId();
            int endTimestep = requestEndBattle.getTimestep();
            int clientReqId = requestEndBattle.getClientReqId();
            int sessBattleId = requestEndBattle.getSessBattleId();
            int availGoldEndClient = requestEndBattle.getAvailGold();
            int availElixirEndClient = requestEndBattle.getAvailElixir();
            int proportionDestroyedEndClient = requestEndBattle.getProportionDestroyed();
            BattleSession battleSession = BattleSession.getBattleSessionById(userId);
            if(battleSession == null || battleSession.getSessionId() != sessBattleId) {
                send(new ResponseEndBattle(clientReqId, ResponseEndBattle.INVALID_SESSION_ID), user);
                return;
            }
            SingleBattlePlayer singleBattlePlayer = SingleBattlePlayer.getBattleSinglePlayerById(userId);
            if(singleBattlePlayer == null) {
                logger.error("Error null end battle " + battleSession.getBattleId());
                logger.error("Error user id " + userId);
                return;
            }
            SingleBattle singleBattle = singleBattlePlayer.getSingleBattleById(battleSession.getBattleId());
            if(singleBattle == null) {
                logger.error("Error end battle " + battleSession.getBattleId());
                logger.error("Error user id " + userId);
                return;
            }
            int availGold = singleBattle.getAvailGold();
            int availElixir = singleBattle.getAvailElixir();
            // TODO: get this by simulate battle
            int earnedGold = availGold - availGoldEndClient;
            int earnedElixir = availElixir - availElixirEndClient;
            singleBattle.setAvailElixir(availElixirEndClient);
            singleBattle.setAvailGold(availGoldEndClient);
            int star = 2;
            if(proportionDestroyedEndClient == 10000) {
                star = 3;
            }
            singleBattle.setStar(star);
            send(new ResponseEndBattle(clientReqId, star, earnedGold, earnedElixir), user);
            singleBattlePlayer.save();
            GameUser gameUser = GameUser.getGameUserById(userId);
            if(gameUser == null) {
                logger.error("Error null user id " + userId);
                return;
            }
            gameUser.addGold(earnedGold);
            gameUser.addElixir(earnedElixir);

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
            if(sessId != battleSession.getSessionId()) {
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
            SingleBattlePlayer singleBattlePlayer = SingleBattlePlayer.getBattleSinglePlayerById(id);
            if(singleBattlePlayer == null) {
                singleBattlePlayer = SingleBattlePlayer.createBattleSinglePlayer(id);
                singleBattlePlayer.save();
            }
            if(singleBattlePlayer.getMaxSingleBattleCanPlayed() < battleId) {
                send(new ResponseSelectSingleBattle(ResponseSelectSingleBattle.INVALID_BATTLE_ID, reqId), user);
                return;
            }
            BattleSession battleSession = BattleSession.getOrCreateBattleSessionById(id);
            battleSession.setBattleId(battleId);
            // TODO: check valid available soldier
            battleSession.setAvailSoldiers(soldierNumbers);
            int currTime = Common.currentTimeInSecond();
            battleSession.setStartTime(currTime);
            // use currTime as battle session id
            battleSession.setSessionId(currTime);
            battleSession.setDropSoldiers(new ArrayList<>());
            battleSession.save();
            send(new ResponseSelectSingleBattle(ResponseSelectSingleBattle.NO_ERROR, reqId, currTime), user);
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
