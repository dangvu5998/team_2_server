package model.battle;

import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BattleSimulatorTest extends TestCase {
    protected void setUp() {
        System.out.println("hello");
    }

    public void testMap1() {
        SingleBattle singleBattle = new SingleBattle(2, 0, 500, 500);
        singleBattle.loadBattleMap();
        ArrayList<BattleSession.DropSoldier> dropSoldiers = new ArrayList<>();
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 13, 27, 1));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 13, 27, 9));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 13, 27, 20));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 16, 30, 30));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 16, 30, 41));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 19, 30, 51));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 23, 29, 61));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 24, 29, 71));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 22, 30, 82));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 19, 31, 93));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 16, 30, 103));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 15, 30, 114));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 15, 28, 125));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 15, 27, 135));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 15, 27, 145));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 15, 26, 156));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 15, 25, 166));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 15, 25, 176));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 15, 25, 186));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 15, 25, 196));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 15, 25, 207));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 15, 25, 217));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 15, 25, 228));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 15, 25, 238));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 15, 25, 248));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 15, 25, 259));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 15, 25, 269));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 15, 25, 280));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 15, 25, 290));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 15, 25, 301));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 15, 25, 311));
        singleBattle.simulateBattle(1307, dropSoldiers, true);
        try {
            FileUtils.writeStringToFile(new File("logs/battleStateServer.txt"), singleBattle.getBattleSimulator().getStateLogger());
        } catch (IOException e) {
            System.out.println("error write file");
        }
    }

    public void testMap2() {
        System.out.println("test map 2");
    }
}
