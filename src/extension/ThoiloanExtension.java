package extension;


import bitzero.engine.sessions.ISession;

import bitzero.server.config.ConfigHandle;
import bitzero.server.entities.User;
import bitzero.server.entities.managers.ConnectionStats;
import bitzero.server.extensions.BZExtension;
import bitzero.server.extensions.data.DataCmd;

import bitzero.util.ExtensionUtility;
import bitzero.util.datacontroller.business.DataController;
import bitzero.util.socialcontroller.bean.UserInfo;

import cmd.receive.authen.RequestLogin;

import event.handler.MainMapHandler;
import org.json.JSONObject;

import model.GameUser;


public class ThoiloanExtension extends BZExtension {
    private static String SERVERS_INFO =
        ConfigHandle.instance().get("servers_key") == null ? "servers" : ConfigHandle.instance().get("servers_key");


    public ThoiloanExtension() {
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
        RequestLogin reqGet = new RequestLogin(objData);
        reqGet.unpackData();
        String username = reqGet.getUsername();
        GameUser gameUser = GameUser.getGeneralInfoByUsername(username);
        if(gameUser == null) {
            gameUser = GameUser.createGameUserByUsername(username);
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId("" + gameUser.getId());
        userInfo.setUsername(username);
        User user = ExtensionUtility.instance().canLogin(userInfo, "", session);
        ExtensionUtility.instance().sendLoginOK(user);
    }


}
