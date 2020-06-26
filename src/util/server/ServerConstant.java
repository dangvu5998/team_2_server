package util.server;

import bitzero.server.config.ConfigHandle;

public class ServerConstant {
    public static final String GAME_DATA_KEY_PREFIX = ConfigHandle.instance().get("game_data_key_prefix").trim();
    public static final String USER_INFO_KEY = ConfigHandle.instance().get("user_info_key").trim();
    public static final String LAST_SNAPSHOT_KEY = ConfigHandle.instance().get("last_snapshot_key").trim();
    public static final String SEPERATOR = ConfigHandle.instance().get("key_name_seperator").trim();

    public static final int CACHE_EXP_TIME = 259200;
}
