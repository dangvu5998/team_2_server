package event.handler;

import bitzero.server.entities.User;
import bitzero.server.extensions.BaseClientRequestHandler;
import bitzero.server.extensions.data.DataCmd;

public class BattleHandler extends BaseClientRequestHandler {
    public final static short BATTLE_IDS = 5000;

    @Override
    public void handleClientRequest(User user, DataCmd dataCmd) {


    }
}
