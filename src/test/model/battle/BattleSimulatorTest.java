package model.battle;

import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BattleSimulatorTest extends TestCase {

    public void testMap1() {
        SingleBattle singleBattle = new SingleBattle(10, 0, 500, 500);
        singleBattle.loadBattleMap();
        ArrayList<BattleSession.DropSoldier> dropSoldiers = new ArrayList<>();
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 31, 35, 1));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 31, 35, 9));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 32, 34, 20));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 33, 33, 31));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 35, 32, 41));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 35, 30, 51));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 36, 28, 62));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 35, 26, 72));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 34, 23, 82));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 32, 32, 318));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 32, 32, 328));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 32, 32, 338));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 34, 31, 349));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 34, 30, 360));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 35, 28, 370));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 35, 26, 380));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 34, 21, 390));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 33, 17, 399));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 31, 14, 409));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 27, 8, 420));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 27, 8, 431));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 25, 8, 440));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 35, 29, 637));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 35, 29, 644));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 35, 29, 655));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 35, 28, 665));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 35, 27, 675));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 35, 25, 686));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 35, 24, 696));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 34, 23, 707));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 34, 23, 717));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 34, 22, 727));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 23, 31, 747));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 23, 31, 758));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 21, 31, 768));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 18, 30, 779));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 14, 29, 790));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 12, 28, 799));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 12, 26, 809));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 10, 24, 820));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 10, 22, 830));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 9, 21, 840));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 5, 23, 851));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 5, 23, 862));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 5, 24, 872));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 4, 24, 882));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 8, 28, 892));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 11, 30, 902));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 13, 31, 913));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 15, 30, 922));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 16, 31, 932));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 16, 31, 943));
        singleBattle.simulateBattle(2067, dropSoldiers, true);
        try {
            FileUtils.writeStringToFile(new File("logs/battleStateServer.txt"), singleBattle.getBattleSimulator().getStateLogger());
        } catch (IOException e) {
            System.out.println("error write file");
        }
    }

}
