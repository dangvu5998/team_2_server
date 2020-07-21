package model.battle;

import junit.framework.TestCase;

public class BattleSinglePlayerTest extends TestCase {

    int testId = 1234;

    protected void setUp() {
        BattleSinglePlayer battleSinglePlayer = BattleSinglePlayer.getBattleSinglePlayerById(testId);
        if(battleSinglePlayer != null) {
            BattleSinglePlayer.deleteBattleSinglePlayerById(testId);
        }
    }

    public void testCreateSingleBattlePlayer() {
        BattleSinglePlayer battleSinglePlayer = BattleSinglePlayer.createBattleSinglePlayer(testId);
        assertNotNull(battleSinglePlayer);
    }

    protected void tearDown() {
        BattleSinglePlayer.deleteBattleSinglePlayerById(testId);
    }
}