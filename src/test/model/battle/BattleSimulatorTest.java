package model.battle;

import junit.framework.TestCase;

import java.util.ArrayList;

public class BattleSimulatorTest extends TestCase {
    protected void setUp() {
        System.out.println("hello");
    }

    public void testMap1() {
        SingleBattle singleBattle = new SingleBattle(1, 0, 500, 500);
        singleBattle.loadBattleMap();
        ArrayList<BattleSession.DropSoldier> dropSoldiers = new ArrayList<>();
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 9, 29, 1));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 9, 30, 25));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 11, 31, 58));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 13, 31, 99));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 15, 32, 143));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1" , 0, 30, 169));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 23, 29, 215));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1" , 5, 27, 291));

        singleBattle.simulateBattle(5000, dropSoldiers);
    }

    public void testMap2() {
        System.out.println("test map 2");
    }
}
