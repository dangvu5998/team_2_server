package model.battle;

import junit.framework.TestCase;

public class SingleBattlePlayerTest extends TestCase {

    int testId = 987654321;

    protected void setUp() {
        SingleBattlePlayer battleSinglePlayer = SingleBattlePlayer.getBattleSinglePlayerById(testId);
        if(battleSinglePlayer != null) {
            SingleBattlePlayer.deleteBattleSinglePlayerById(testId);
        }
    }

    public void testCreateSingleBattlePlayer() {
        SingleBattlePlayer battleSinglePlayer = SingleBattlePlayer.createBattleSinglePlayer(testId);
        assertNotNull(battleSinglePlayer);
        battleSinglePlayer.save();
        battleSinglePlayer = SingleBattlePlayer.getBattleSinglePlayerById(testId);
        assertNotNull(battleSinglePlayer);
    }

    protected void tearDown() {
        SingleBattlePlayer.deleteBattleSinglePlayerById(testId);
    }
}