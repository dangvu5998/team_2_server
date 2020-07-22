package extension;


import bitzero.engine.sessions.ISession;

import bitzero.server.config.ConfigHandle;
import bitzero.server.entities.User;
import bitzero.server.entities.managers.ConnectionStats;
import bitzero.server.extensions.BZExtension;
import bitzero.server.extensions.data.DataCmd;

import bitzero.util.ExtensionUtility;
import bitzero.util.common.business.CommonHandle;
import bitzero.util.datacontroller.business.DataController;
import bitzero.util.socialcontroller.bean.UserInfo;

import cmd.ResponseConst;
import cmd.send.ResponseMainInfo;
import cmd.send.ResponseTimeStamp;
import cmd.send.mainmap.ResponseLoadMainMap;
import event.handler.BattleHandler;
import event.handler.MainMapHandler;
import event.handler.ResourceHandler;
import event.handler.SyncHandler;
import org.json.JSONObject;

import cmd.receive.authen.RequestLogin;
import model.GameUser;
import util.Common;


public class FresherExtension extends BZExtension {
    private static String SERVERS_INFO =
        ConfigHandle.instance().get("servers_key") == null ? "servers" : ConfigHandle.instance().get("servers_key");


    public FresherExtension() {
        super();
        setName("Thoiloan");
    }

    public void init() {

        /**
         * register new handler to catch client's packet
         */
        trace("  Register Handler ");

        /**
         * register new event
         */
        // auth handler
//        addEventHandler(BZEventType.USER_LOGIN, LoginSuccessHandler.class);
//        addEventHandler(BZEventType.USER_LOGIN, MainMapHandler.class);
//        addEventHandler(BZEventType.USER_LOGOUT, LogoutHandler.class);
//        addEventHandler(BZEventType.USER_DISCONNECT, LogoutHandler.class);

        addRequestHandler(MainMapHandler.MAIN_MAP_IDS, MainMapHandler.class);
        addRequestHandler(ResourceHandler.RESOURCE_IDS, ResourceHandler.class);
        addRequestHandler(SyncHandler.SYNC_IDS, SyncHandler.class);
        addRequestHandler(BattleHandler.BATTLE_IDS, BattleHandler.class);
    }

    @Override
    public void monitor() {
        try {
            ConnectionStats connStats = bz.getStatsManager().getUserStats();
            JSONObject data = new JSONObject();

            data.put("totalInPacket", bz.getStatsManager().getTotalInPackets());
            data.put("totalOutPacket", bz.getStatsManager().getTotalOutPackets());
            data.put("totalInBytes", bz.getStatsManager().getTotalInBytes());
            data.put("totalOutBytes", bz.getStatsManager().getTotalOutBytes());

            data.put("connectionCount", connStats.getSocketCount());
            data.put("totalUserCount", bz.getUserManager().getUserCount());

            DataController.getController().setCache(SERVERS_INFO, 60 * 5, data.toString());
        } catch (Exception e) {
            trace("Ex monitor");
        }
    }

    @Override
    public void destroy() {

    }

    /**
     *
     * @param cmdId
     * @param session
     * @param objData
     *
     * the first packet send from client after handshake success will dispatch to doLogin() function
     */
    public void doLogin(short cmdId, ISession session, DataCmd objData) {
        try {
            RequestLogin reqGet = new RequestLogin(objData);
            reqGet.unpackData();
            String username = reqGet.getUsername();
            GameUser gameUser = GameUser.getGameUserByUsername(username);
            if (gameUser == null) {
                gameUser = GameUser.createGameUserByUsername(username);
            }
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId("" + gameUser.getId());
            userInfo.setUsername(username);
            User user = ExtensionUtility.instance().canLogin(userInfo, "", session);
            ExtensionUtility.instance().sendLoginOK(user);
            try {
                send(new ResponseTimeStamp(Common.currentTimeInSecond()), user);
                send(new ResponseMainInfo(ResponseConst.OK, gameUser), user);
                send(new ResponseLoadMainMap(ResponseConst.OK, gameUser.getAllMapObjects()), user);
            } catch ( Exception e) {
                CommonHandle.writeErrLog(e);
            }
        } catch(Exception e) {
            CommonHandle.writeErrLog(e);
        }
    }


}
