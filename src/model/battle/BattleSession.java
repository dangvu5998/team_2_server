package model.battle;

import com.google.gson.annotations.Expose;
import model.GameUser;
import util.database.DBBuiltInUtil;

import java.util.ArrayList;

public class BattleSession {
    public static class SoldierNumber {
        @Expose
        private final String soldierType;
        @Expose
        private final int number;

        public SoldierNumber(String type, int number) {
            soldierType = type;
            this.number = number;
        }

        public String getSoldierType() {
            return soldierType;
        }

        public int getNumber() {
            return number;
        }
    }

    public static class DropSoldier {
        @Expose
        private final String soldierType;
        @Expose
        private final int x;
        @Expose
        private final int y;
        @Expose
        private final int timestep;

        public DropSoldier(String soldierType, int x, int y, int timestep) {
            this.soldierType = soldierType;
            this.x = x;
            this.y = y;
            this.timestep = timestep;
        }

        public String getSoldierType() {
            return soldierType;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getTimestep() {
            return timestep;
        }
    }

    @Expose
    private int userId;
    @Expose
    private int startTime;
    @Expose
    private int battleId;
    @Expose
    private ArrayList<SoldierNumber> availSoldiers;
    @Expose
    private ArrayList<DropSoldier> dropSoldiers;
    @Expose
    private int sessionId;

    public static final String COLLECTION_NAME = "BattleSession";

    public BattleSession(int userId) {
        this.userId = userId;
        this.startTime = 0;
        this.battleId = -1;
    }

    public void save() {
        DBBuiltInUtil.save(COLLECTION_NAME, String.valueOf(userId), this);
    }

    public static BattleSession getBattleSessionById(int id) {
        return (BattleSession) DBBuiltInUtil.get(COLLECTION_NAME, String.valueOf(id), BattleSession.class);
    }

    public static BattleSession getOrCreateBattleSessionById(int id) {
        BattleSession battleSession = getBattleSessionById(id);
        if(battleSession == null) {
            return new BattleSession(id);
        }
        else
            return battleSession;
    }

    public int getUserId() {
        return userId;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getBattleId() {
        return battleId;
    }

    public void setBattleId(int battleId) {
        this.battleId = battleId;
    }

    public ArrayList<SoldierNumber> getAvailSoldiers() {
        return availSoldiers;
    }

    public void setAvailSoldiers(ArrayList<SoldierNumber> availSoldiers) {
        this.availSoldiers = availSoldiers;
    }

    public ArrayList<DropSoldier> getDropSoldiers() {
        return dropSoldiers;
    }

    public void setDropSoldiers(ArrayList<DropSoldier> dropSoldiers) {
        this.dropSoldiers = dropSoldiers;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }
}
