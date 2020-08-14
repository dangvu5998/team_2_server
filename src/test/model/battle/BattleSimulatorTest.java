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
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 17, 33, 513));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 17, 33, 522));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 17, 33, 533));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 18, 32, 544));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 19, 31, 554));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 20, 31, 564));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 21, 30, 575));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 22, 29, 586));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 23, 27, 596));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 24, 26, 607));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 25, 26, 618));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 26, 25, 628));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 26, 24, 639));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 26, 23, 649));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 26, 22, 660));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 26, 21, 667));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 26, 20, 678));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 26, 19, 683));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 25, 17, 694));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 25, 16, 696));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 24, 15, 707));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 24, 15, 717));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 23, 13, 727));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 23, 13, 737));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 22, 12, 745));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 19, 14, 755));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 19, 14, 766));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 19, 14, 776));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 25, 16, 787));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 25, 16, 797));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 26, 17, 808));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 26, 17, 818));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 27, 18, 828));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 26, 19, 839));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 26, 20, 850));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 26, 20, 857));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 26, 20, 861));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 26, 20, 871));
        singleBattle.simulateBattle(2260, dropSoldiers, true);
        try {
            FileUtils.writeStringToFile(new File("logs/battleStateServer.txt"), singleBattle.getBattleSimulator().getStateLogger());
        } catch (IOException e) {
            System.out.println("error write file");
        }
    }

}
