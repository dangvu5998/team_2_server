package model.battle;

import com.google.gson.annotations.Expose;
import util.database.DBBuiltInUtil;

import java.util.ArrayList;

public class SingleBattlePlayer {
    // is user id
    @Expose
    private final int id;
    @Expose
    private final ArrayList<SingleBattle> battles;

    private static final String COLLECTION_NAME = "BattleSinglePlayer_P";

    public SingleBattlePlayer(int id, ArrayList<SingleBattle> battles) {
        this.id = id;
        this.battles = battles;
    }

    /**
     * Create new battle single for player who hasn't have battle single list yet
     * @param id user id
     * @return new battle single player
     */
    public static SingleBattlePlayer createBattleSinglePlayer(int id) {
        ArrayList<SingleBattle> battles = new ArrayList<>();
        for(int battleId = SingleBattle.MIN_ID; battleId <= SingleBattle.MAX_ID; battleId++) {
            battles.add(new SingleBattle(battleId));
        }
        return new SingleBattlePlayer(id, battles);
    }

    public void save() {
        DBBuiltInUtil.save(COLLECTION_NAME, String.valueOf(id), this);
    }

    public static SingleBattlePlayer getBattleSinglePlayerById(int id) {
        return (SingleBattlePlayer) DBBuiltInUtil.get(COLLECTION_NAME, String.valueOf(id), SingleBattlePlayer.class);
    }

    public static SingleBattlePlayer getOrCreateBattleSinglePlayerById(int id) {
        SingleBattlePlayer singleBattlePlayer = getBattleSinglePlayerById(id);
        if(singleBattlePlayer == null) {
            singleBattlePlayer = createBattleSinglePlayer(id);
        }
        return singleBattlePlayer;
    }

    public int getMaxSingleBattleCanPlayed() {
        int maxBattle = 0;
        for(int i = battles.size() - 1; i > 0; i--) {
            SingleBattle battle = battles.get(i);
            if(battle.getStar() > 0) {
                maxBattle = i;
                break;
            }
        }
        return maxBattle + 1;
    }

    public SingleBattle getSingleBattleById(int id) {
        for(SingleBattle singleBattle: battles) {
            if(singleBattle.getId() == id) {
                return singleBattle;
            }
        }
        return null;
    }

    public static void deleteBattleSinglePlayerById(int id) {
        DBBuiltInUtil.delete(COLLECTION_NAME, String.valueOf(id));
    }

    public ArrayList<SingleBattle> getBattles() {
        return battles;
    }
}
