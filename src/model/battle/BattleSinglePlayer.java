package model.battle;

import com.google.gson.annotations.Expose;
import util.database.DBBuiltInUtil;

import java.util.ArrayList;

public class BattleSinglePlayer {
    @Expose
    private int id;
    @Expose
    private ArrayList<SingleBattle> battles;

    private static final String COLLECTION_NAME = "BattleSinglePlayer_P";

    public BattleSinglePlayer(int id,ArrayList<SingleBattle> battles) {
        this.id = id;
        this.battles = battles;
    }

    /**
     * Create new battle single for player who hasn't have battle single list yet
     * @param id user id
     * @return new battle single player
     */
    public static BattleSinglePlayer createBattleSinglePlayer(int id) {
        ArrayList<SingleBattle> battles = new ArrayList<>();
        for(int battleId = SingleBattle.MIN_ID; battleId <= SingleBattle.MAX_ID; battleId++) {
            battles.add(new SingleBattle(battleId));
        }
        return new BattleSinglePlayer(id, battles);
    }

    public void save() {
        DBBuiltInUtil.save(COLLECTION_NAME, String.valueOf(id), this);
    }

    public static BattleSinglePlayer getBattleSinglePlayerById(int id) {
        return (BattleSinglePlayer) DBBuiltInUtil.get(COLLECTION_NAME, String.valueOf(id), BattleSinglePlayer.class);
    }

    public static void deleteBattleSinglePlayerById(int id) {
        DBBuiltInUtil.delete(COLLECTION_NAME, String.valueOf(id));
    }
}
