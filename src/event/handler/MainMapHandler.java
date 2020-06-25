package event.handler;

import bitzero.server.entities.User;
import bitzero.server.extensions.BaseClientRequestHandler;
import bitzero.server.extensions.IServerEventHandler;
import bitzero.server.extensions.data.DataCmd;
import bitzero.server.core.IBZEvent;
import cmd.CmdDefine;
import cmd.send.ResponseLoadMainMap;

public class MainMapHandler extends BaseClientRequestHandler implements IServerEventHandler {
    public final static short MAIN_MAP_IDS = 2000;
    public MainMapHandler() {
        super();
    }

    @Override
    public void handleClientRequest(User user, DataCmd dataCmd) {
        switch (dataCmd.getId()) {
            case CmdDefine.LOAD_MAIN_MAP:
                processLoadMainMap(user);
        }
    }

    public void handleServerEvent(IBZEvent ibzevent) {
        System.out.println(ibzevent.getType());
    }

    private void processLoadMainMap(User user) {

        send(new ResponseLoadMainMap(200), user);
    }
}
