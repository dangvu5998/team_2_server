package event.handler;

import bitzero.server.entities.User;
import bitzero.server.extensions.BaseClientRequestHandler;
import bitzero.server.extensions.data.DataCmd;
import cmd.CmdDefine;
import cmd.send.ResponseTimeStamp;
import util.Common;

public class SyncHandler extends BaseClientRequestHandler {
    public static final short SYNC_IDS = 4000;

    @Override
    public void handleClientRequest(User user, DataCmd dataCmd) {
        if(dataCmd.getId() == CmdDefine.CURRENT_TIME) {
            processGetCurrentTime(user);
        }
    }

    private void processGetCurrentTime(User user) {
        send(new ResponseTimeStamp(Common.currentTimeInSecond()), user);
    }


}
