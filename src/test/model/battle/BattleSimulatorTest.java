package model.battle;

import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BattleSimulatorTest extends TestCase {

    public void testMap1() {
        SingleBattle singleBattle = new SingleBattle(9, 0, 500, 500);
        singleBattle.loadBattleMap();
        ArrayList<BattleSession.DropSoldier> dropSoldiers = new ArrayList<>();
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", -1, 6, 1));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 1, 7, 17));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", -1, 18, 54));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 6, -1, 96));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 27, -1, 187));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 28, -1, 304));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 28, -1, 313));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 39, 40, 455));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 39, 40, 466));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 39, 39, 476));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 41, 36, 487));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 41, 37, 497));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 40, 22, 684));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 40, 22, 693));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 41, 17, 706));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 40, 28, 742));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 40, 22, 761));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 40, 22, 771));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 40, 8, 832));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 33, 6, 871));
        singleBattle.simulateBattle(4389, dropSoldiers, true);
        try {
            FileUtils.writeStringToFile(new File("logs/battleStateServer.txt"), singleBattle.getBattleSimulator().getStateLogger());
        } catch (IOException e) {
            System.out.println("error write file");
        }
    }

}
