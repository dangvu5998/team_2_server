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
        SingleBattle singleBattle = new SingleBattle(3, 0, 500, 500);
        singleBattle.loadBattleMap();
        ArrayList<BattleSession.DropSoldier> dropSoldiers = new ArrayList<>();
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 23, 21, 1));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 24, 22, 33));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 24, 20, 75));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 16, 17, 159));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 18, 17, 179));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 17, 26, 210));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 16, 25, 236));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 23, 21, 1124));
        singleBattle.simulateBattle(1268, dropSoldiers, true);
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
