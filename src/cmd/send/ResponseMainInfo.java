package cmd.send;

import bitzero.server.extensions.data.BaseMsg;
import cmd.CmdDefine;
import model.GameUser;

import java.nio.ByteBuffer;

public class ResponseMainInfo extends BaseMsg {
    private int status;
    private GameUser gameUser;
    public ResponseMainInfo(int status, GameUser gameUser) {
        super(CmdDefine.MAIN_GAME_INFO);
        this.status = status;
        this.gameUser = gameUser;
    }

    @Override
    public byte[] createData() {
        ByteBuffer bf = makeBuffer();
        // 0 status
        bf.putInt(status);
        // 1 id
        bf.putInt(gameUser.getId());
        // 2 username
        putStr(bf, gameUser.getUsername());
        // 3 frameScore
        bf.putInt(gameUser.getFrameScore());
        // 4 userLevel
        bf.putInt(gameUser.getUserLevel());
        // 5 userXP
        bf.putInt(gameUser.getUserXP());
        // 6 current nb of sodiers
        bf.putInt(0);
        // 7 max nb of sodiers
        bf.putInt(0);
        // 8 current of available builder
        bf.putInt(gameUser.getNbOfAvaiBuilder());
        // 9 max nb of builders
        bf.putInt(gameUser.getNbOfBuilder());
        // 10 shield expired time
        bf.putInt(0);
        // 11 gold
        bf.putInt(gameUser.getGold());
        // 12 gold capacity
        bf.putInt(gameUser.getGoldCapacity());
        // 13 elixir
        bf.putInt(gameUser.getElixir());
        // 14 elixir capacity
        bf.putInt(gameUser.getElixirCapacity());
        // 15 dark elixir
        bf.putInt(0);
        // 16 dark elixir capacity
        bf.putInt(0);
        // 17 g
        bf.putInt(gameUser.getG());
        // 18 music
        if(gameUser.isMusic()) {
            bf.putInt(1);
        }
        else {
            bf.putInt(0);
        }
        // 19 sound
        if(gameUser.isSound()) {
            bf.putInt(1);
        }
        else {
            bf.putInt(0);
        }
        // 20 notifications
        if(gameUser.isNotifications()) {
            bf.putInt(1);
        }
        else {
            bf.putInt(0);
        }
        return packBuffer(bf);
    }
}
