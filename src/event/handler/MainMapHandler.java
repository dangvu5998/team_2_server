package event.handler;

import bitzero.server.entities.User;
import bitzero.server.extensions.BaseClientRequestHandler;
import bitzero.server.extensions.IServerEventHandler;
import bitzero.server.extensions.data.DataCmd;
import bitzero.server.core.IBZEvent;
import bitzero.util.common.business.CommonHandle;
import cmd.CmdDefine;
import cmd.RequestConst;
import cmd.ResponseConst;
import cmd.receive.mainmap.*;
import cmd.send.*;
import model.GameUser;
import model.map.MapObject;

public class MainMapHandler extends BaseClientRequestHandler implements IServerEventHandler {
    public final static short MAIN_MAP_IDS = 2000;
    public MainMapHandler() {
        super();
    }

    @Override
    public void handleClientRequest(User user, DataCmd dataCmd) {
        GameUser gameUser = GameUser.getGameUserById(user.getId());
        if(gameUser == null) {
            // TODO: handle game user null
            return;
        }
        switch (dataCmd.getId()) {
            case CmdDefine.LOAD_MAIN_MAP -> processLoadMainMap(user, gameUser);
            case CmdDefine.MAIN_GAME_INFO -> processMainGameInfo(user, gameUser);
            case CmdDefine.MOVE_BUILDING -> processMoveBuilding(user, gameUser, dataCmd);
            case CmdDefine.BUY_BUILDING -> processBuyBuilding(user, gameUser, dataCmd);
            case CmdDefine.CANCEL_BUILDING -> processCancelBuilding(user, gameUser, dataCmd);
            case CmdDefine.UPGRADE_BUILDING -> processUpgradeBuilding(user, gameUser, dataCmd);
            case CmdDefine.QUICK_FINISH_MAP_OBJECT -> processQuickFinish(user, gameUser, dataCmd);
            case CmdDefine.REMOVE_OBSTACLE -> processRemoveObstacle(user, gameUser, dataCmd);
        }
    }

    private void processMainGameInfo(User user, GameUser gameUser) {
        send(new ResponseMainInfo(ResponseConst.OK, gameUser), user);
    }

    public void handleServerEvent(IBZEvent ibzevent) {
        System.out.println(ibzevent.getType());
    }

    private void processLoadMainMap(User user, GameUser gameUser) {
        try {
            send(new ResponseLoadMainMap(ResponseConst.OK, gameUser.getAllMapObjects()), user);
        } catch ( Exception e) {
            CommonHandle.writeErrLog(e);
        }
    }

    private void processMoveBuilding(User user, GameUser gameUser, DataCmd dataCmd) {
        RequestMoveBuilding requestMoveBuilding = new RequestMoveBuilding(dataCmd);
        if(requestMoveBuilding.getStatus() == RequestConst.OK) {
            int buildingId = requestMoveBuilding.getBuildingId();
            int x = requestMoveBuilding.getX();
            int y = requestMoveBuilding.getY();
            if(x < 0 || x >= MapObject.MAP_WIDTH || y < 0 || y >= MapObject.MAP_HEIGHT) {
                send(new ResponseMoveBuilding(ResponseConst.SEMANTIC_INVALID, buildingId, x, y), user);
                return;
            }
            boolean isMoved = gameUser.moveBuildingById(buildingId, x, y);
            if(isMoved) {
                send(new ResponseMoveBuilding(ResponseConst.OK, buildingId, x, y), user);
            }
            else {
                send(new ResponseMoveBuilding(ResponseConst.SEMANTIC_INVALID, buildingId, x, y), user);
            }
        }
    }

    private void processBuyBuilding(User user, GameUser gameUser, DataCmd dataCmd) {
        RequestBuyBuilding requestBuyBuilding = new RequestBuyBuilding(dataCmd);
        if(requestBuyBuilding.getStatus() == ResponseConst.OK) {
            int buildingTypeId = requestBuyBuilding.getBuildingTypeId();
            int x = requestBuyBuilding.getX();
            int y = requestBuyBuilding.getY();
            int buyBuildingCode = gameUser.buyBuilding(buildingTypeId, x, y);
            if (buyBuildingCode > 0) {
                send(new ResponseBuyBuilding(ResponseConst.OK, buildingTypeId, x, y, buyBuildingCode), user);
            } else {
                send(new ResponseBuyBuilding(ResponseConst.SEMANTIC_INVALID, buildingTypeId, x, y, buyBuildingCode), user);
            }
        }
        else {
            send(new ResponseFormatInvalid(dataCmd.getId()), user);
        }
    }

    private void processCancelBuilding(User user, GameUser gameUser, DataCmd dataCmd) {
        RequestCancelBuilding requestCancelBuilding = new RequestCancelBuilding(dataCmd);
        if(requestCancelBuilding.getStatus() == RequestConst.OK) {
            int buildingId = requestCancelBuilding.getBuildingId();
            int cancelCode = gameUser.cancelBuilding(buildingId);
            if(cancelCode > 0) {
                send(new ResponseCancelBuilding(ResponseConst.OK, buildingId), user);
            } else
            {
                send(new ResponseCancelBuilding(ResponseConst.SEMANTIC_INVALID, buildingId, cancelCode), user);
            }
        }
        else {
            send(new ResponseFormatInvalid(dataCmd.getId()), user);
        }
    }

    private void processUpgradeBuilding(User user, GameUser gameUser, DataCmd dataCmd) {
        RequestUpgradeBuilding requestUpgradeBuilding = new RequestUpgradeBuilding(dataCmd);
        if(requestUpgradeBuilding.getStatus() == ResponseConst.OK) {
            int buildingId = requestUpgradeBuilding.getBuildingId();
            int upgradeCode = gameUser.upgradeBuilding(buildingId);
            if(upgradeCode > 0) {
                send(new ResponseUpgradeBuilding(ResponseConst.OK, buildingId), user);
            }
            else {
                send(new ResponseUpgradeBuilding(ResponseConst.SEMANTIC_INVALID, buildingId, upgradeCode), user);
            }
        }
        else {
            send(new ResponseFormatInvalid(dataCmd.getId()), user);
        }
    }

    private void processQuickFinish(User user, GameUser gameUser, DataCmd dataCmd) {
        RequestQuickFinish requestQuickFinish = new RequestQuickFinish(dataCmd);
        if (requestQuickFinish.getStatus() == ResponseConst.OK) {
            int mapObjId = requestQuickFinish.getMapObjId();
            int gToQF = requestQuickFinish.getgToQF();
            int quickFinishCode = gameUser.quickFinishMapObject(mapObjId, gToQF);
            if(quickFinishCode > 0) {
                send(new ResponseQuickFinish(ResponseConst.OK, mapObjId), user);
            }
            else {
                send(new ResponseQuickFinish(ResponseConst.SEMANTIC_INVALID, mapObjId, quickFinishCode), user);
            }
        } else {
            send(new ResponseFormatInvalid(dataCmd.getId()), user);
        }
    }

    private void processRemoveObstacle(User user, GameUser gameUser, DataCmd dataCmd) {
        RequestRemoveObstacle requestRemoveObstacle = new RequestRemoveObstacle(dataCmd);
        if(requestRemoveObstacle.getStatus() == ResponseConst.OK) {
            int mapObjId = requestRemoveObstacle.getMapObjId();
            int resCode = gameUser.removeObstacle(mapObjId);
            if(resCode > 0) {
                send(new ResponseRemoveObstacle(ResponseConst.OK, mapObjId), user);
            }
            else {
                send(new ResponseRemoveObstacle(ResponseConst.SEMANTIC_INVALID, mapObjId, resCode), user);
            }
        }
        else {
            send(new ResponseFormatInvalid(dataCmd.getId()), user);
        }
    }

}
