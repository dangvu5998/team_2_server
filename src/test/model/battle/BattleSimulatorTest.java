package model.battle;

import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BattleSimulatorTest extends TestCase {

    public void testMap1() {
        SingleBattle singleBattle = new SingleBattle(5, 0, 500, 500);
        singleBattle.loadBattleMap();
        ArrayList<BattleSession.DropSoldier> dropSoldiers = new ArrayList<>();
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 12, 33, 1));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 12, 33, 9));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 12, 33, 19));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 11, 32, 29));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 9, 31, 40));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 7, 30, 51));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 7, 28, 61));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 6, 24, 71));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 6, 22, 81));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 6, 21, 92));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 7, 19, 103));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 8, 17, 113));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 8, 16, 124));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 7, 28, 579));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 7, 28, 896));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 7, 28, 907));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 7, 28, 917));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 7, 24, 928));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 7, 25, 938));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 7, 23, 949));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 6, 20, 960));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 6, 19, 969));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 7, 18, 978));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 7, 16, 989));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 8, 15, 999));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 10, 14, 1010));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 12, 14, 1020));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 14, 12, 1031));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 12, 12, 1040));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 14, 12, 1050));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 12, 12, 1060));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 12, 12, 1071));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 14, 14, 1082));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 14, 12, 1093));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 12, 12, 1103));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 12, 12, 1113));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 12, 14, 1123));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 12, 12, 1133));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 12, 14, 1143));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 12, 12, 1153));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 14, 14, 1163));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 14, 12, 1174));
        singleBattle.simulateBattle(1817, dropSoldiers, true);
        try {
            FileUtils.writeStringToFile(new File("logs/battleStateServer.txt"), singleBattle.getBattleSimulator().getStateLogger());
        } catch (IOException e) {
            System.out.println("error write file");
        }
    }

}
