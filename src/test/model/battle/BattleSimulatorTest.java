package model.battle;

import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BattleSimulatorTest extends TestCase {

    public void testMap1() {
        SingleBattle singleBattle = new SingleBattle(7, 0, 500, 500);
        singleBattle.loadBattleMap();
        ArrayList<BattleSession.DropSoldier> dropSoldiers = new ArrayList<>();
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 1, 39, 1));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 1, 39, 10));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 0, 38, 32));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 0, 38, 40));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 0, 33, 73));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 0, 32, 80));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 0, 29, 98));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", -1, 21, 166));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", -1, 15, 196));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 0, 8, 265));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 0, 8, 273));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 0, 8, 282));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", -1, 3, 474));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 1, 2, 491));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 1, 1, 502));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 4, -1, 536));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 7, 0, 564));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 7, 0, 573));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 7, 0, 589));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 7, 0, 597));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 9, 0, 610));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 9, 0, 619));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 16, 2, 669));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 19, 0, 707));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 19, 0, 717));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 19, 0, 725));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 24, 0, 811));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 24, 0, 826));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 27, 0, 838));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 32, 1, 1120));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 32, 1, 1128));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 32, 1, 1136));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 40, 9, 1169));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 40, 9, 1187));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 40, 9, 1207));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 40, 19, 1294));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 40, 19, 1301));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 39, 2, 1413));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 39, 2, 1421));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 37, 1, 1436));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 39, 9, 1489));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 39, 40, 1636));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 39, 40, 1645));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 39, 40, 1654));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 39, 40, 1675));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 39, 40, 1696));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 37, 41, 1716));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 37, 41, 1727));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 34, 41, 1757));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 34, 40, 1770));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 33, 40, 1786));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 24, 40, 1846));
        singleBattle.simulateBattle(2543, dropSoldiers, true);
        try {
            FileUtils.writeStringToFile(new File("logs/battleStateServer.txt"), singleBattle.getBattleSimulator().getStateLogger());
        } catch (IOException e) {
            System.out.println("error write file");
        }
    }

}
