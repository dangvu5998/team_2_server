package event.handler;

import bitzero.server.entities.User;
import bitzero.server.extensions.BaseClientRequestHandler;
import bitzero.server.extensions.IServerEventHandler;
import bitzero.server.extensions.data.DataCmd;
import bitzero.server.core.IBZEvent;

public class MainMapHandler extends BaseClientRequestHandler implements IServerEventHandler {
    public final static short MAIN_MAP_IDS = 2000;
    public MainMapHandler() {
        super();
    }

    @Override
    public void handleClientRequest(User user, DataCmd dataCmd) {
        System.out.println("Here");
        System.out.println(user);
    }

    public void handleServerEvent(IBZEvent ibzevent) {
        System.out.println(ibzevent.getType());
    }
}
