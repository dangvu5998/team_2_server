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
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 28, 27, 1));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 28, 27, 11));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 28, 27, 22));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 26, 28, 32));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 24, 28, 42));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 21, 27, 52));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 20, 27, 62));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 18, 26, 72));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 17, 24, 83));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 17, 23, 93));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 17, 23, 103));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 17, 23, 114));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 16, 24, 155));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 17, 26, 165));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 19, 28, 175));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 21, 29, 185));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 22, 28, 196));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 23, 28, 206));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 24, 27, 217));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 24, 27, 227));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 24, 27, 237));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 24, 27, 247));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 25, 27, 258));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 24, 27, 268));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 18, 28, 279));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 17, 27, 290));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 17, 27, 300));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 17, 26, 311));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 17, 26, 321));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 17, 26, 331));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 17, 25, 341));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 17, 25, 352));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 17, 25, 362));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 17, 24, 371));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 18, 17, 443));

        singleBattle.simulateBattle(1134, dropSoldiers, true);
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
