package event.handler;

import bitzero.server.entities.User;
import bitzero.server.extensions.BaseClientRequestHandler;
import bitzero.server.extensions.IServerEventHandler;
import bitzero.server.extensions.data.DataCmd;
import bitzero.server.core.IBZEvent;
import cmd.CmdDefine;
import cmd.RequestConst;
import cmd.ResponseConst;
import cmd.receive.mainmap.RequestMoveBuilding;
import cmd.send.ResponseLoadMainMap;
import cmd.send.ResponseMoveBuilding;
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
        switch (dataCmd.getId()) {
            case CmdDefine.LOAD_MAIN_MAP -> processLoadMainMap(user, gameUser);
            case CmdDefine.MOVE_BUILDING -> processMoveBuilding(user, gameUser, dataCmd);
        }
    }

    public void handleServerEvent(IBZEvent ibzevent) {
        System.out.println(ibzevent.getType());
    }

    private void processLoadMainMap(User user, GameUser gameUser) {
        send(new ResponseLoadMainMap(ResponseConst.OK, gameUser.getAllMapObjects()), user);
    }

    private void processMoveBuilding(User user, GameUser gameUser, DataCmd dataCmd) {
        RequestMoveBuilding requestMoveBuilding = new RequestMoveBuilding(dataCmd);
        if(requestMoveBuilding.getStatus() == RequestConst.OK) {
            int buildingId = requestMoveBuilding.getBuildingId();
            int x = requestMoveBuilding.getX();
            int y = requestMoveBuilding.getY();
            if(x < 0 || x >= MapObject.MAP_WIDTH || y < 0 || y >= MapObject.MAP_HEIGHT) {
                send(new ResponseMoveBuilding(ResponseConst.USER_REQUEST_INVALID, buildingId, x, y), user);
                return;
            }
            boolean isMoved = gameUser.moveBuildingById(buildingId, x, y);
            if(isMoved) {
                send(new ResponseMoveBuilding(ResponseConst.OK, buildingId, x, y), user);
            }
            else {
                send(new ResponseMoveBuilding(ResponseConst.USER_REQUEST_INVALID, buildingId, x, y), user);
            }
        }

    }
}
