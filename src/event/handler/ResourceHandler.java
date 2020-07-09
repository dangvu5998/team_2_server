package event.handler;

import bitzero.server.entities.User;
import bitzero.server.extensions.BaseClientRequestHandler;
import bitzero.server.extensions.data.DataCmd;
import cmd.CmdDefine;
import cmd.RequestConst;
import cmd.ResponseConst;
import cmd.receive.RequestBuyResource;
import cmd.send.ResponseBuyResource;
import cmd.send.ResponseFormatInvalid;
import model.GameUser;
import model.ResourceExchange;

public class ResourceHandler extends BaseClientRequestHandler {
    public final static short RESOURCE_IDS = 3000;
    public ResourceHandler() {
        super();
    }

    @Override
    public void handleClientRequest(User user, DataCmd dataCmd) {
        GameUser gameUser = GameUser.getGameUserById(user.getId());
        if (gameUser == null) {
            // TODO: handle game user null
            return;
        }
        if (dataCmd.getId() == CmdDefine.BUY_RESOURCE) {
            processBuyResource(user, gameUser, dataCmd);
        }
    }

    public void processBuyResource(User user, GameUser gameUser, DataCmd dataCmd) {
        RequestBuyResource requestBuyResource = new RequestBuyResource(dataCmd);
        if(requestBuyResource.getStatus() == RequestConst.OK) {
            int resType = requestBuyResource.getResType();
            int amount = requestBuyResource.getAmount();
            boolean invalidResType = false;
            boolean notEnoughG = false;
            if(resType == RequestBuyResource.G_TYPE) {
                gameUser.addG(amount);
            }
            else if (resType == RequestBuyResource.GOLD_TYPE) {
                int gToBuy = ResourceExchange.goldToG(amount);
                if(gToBuy <= gameUser.getG()) {
                    gameUser.deductG(gToBuy);
                    gameUser.addGold(amount);
                } else {
                    notEnoughG = true;
                }
            } else if (resType == RequestBuyResource.ELIXIR_TYPE) {
                int gToBuy = ResourceExchange.elixirToG(amount);
                if(gToBuy <= gameUser.getG()) {
                    gameUser.deductG(gToBuy);
                    gameUser.addElixir(amount);
                } else {
                    notEnoughG = true;
                }
            } else {
                invalidResType = true;
            }

            if(invalidResType) {
                send(new ResponseBuyResource(ResponseConst.SEMANTIC_INVALID, resType, amount, ResponseBuyResource.INVALID_RES_TYPE), user);
            } else if (notEnoughG) {
                send(new ResponseBuyResource(ResponseConst.SEMANTIC_INVALID, resType, amount, ResponseBuyResource.NOT_ENOUGH_G), user);
            } else {
                send(new ResponseBuyResource(ResponseConst.OK, resType, amount), user);
            }

        } else {
            send(new ResponseFormatInvalid(dataCmd.getId()), user);
        }
    }

}
