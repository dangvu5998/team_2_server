package model.battle;

import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BattleSimulatorTest extends TestCase {

    public void testMap1() {
        SingleBattle singleBattle = new SingleBattle(8, 0, 500, 500);
        singleBattle.loadBattleMap();
        ArrayList<BattleSession.DropSoldier> dropSoldiers = new ArrayList<>();
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 34, 21, 1));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 34, 21, 9));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 34, 21, 19));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 23, 11, 70));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 23, 11, 80));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 23, 11, 90));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 20, 11, 101));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 17, 11, 112));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 12, 16, 133));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 13, 19, 142));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 19, 31, 163));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 23, 32, 172));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 26, 30, 183));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 29, 30, 193));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 30, 30, 203));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 30, 30, 213));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 31, 30, 223));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 32, 30, 234));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 32, 30, 244));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 32, 30, 254));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 32, 30, 264));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 32, 30, 275));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 32, 30, 285));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 32, 30, 295));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 32, 30, 306));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 28, 36, 401));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 28, 36, 410));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 28, 36, 420));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 28, 36, 429));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 28, 36, 445));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 28, 36, 454));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 28, 38, 513));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 28, 38, 522));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 28, 38, 531));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 28, 38, 540));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 28, 38, 550));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 28, 38, 559));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 28, 38, 568));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 28, 38, 575));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 28, 38, 584));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 28, 38, 590));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 28, 38, 600));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 28, 38, 609));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 28, 38, 617));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 28, 38, 625));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 28, 38, 635));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 28, 38, 639));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 28, 38, 648));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 24, 36, 1707));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 24, 36, 1714));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 24, 36, 1725));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 24, 36, 1732));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 24, 36, 1742));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 24, 36, 1751));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 24, 36, 1761));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 24, 36, 1771));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 24, 36, 1780));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 24, 36, 1789));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 24, 36, 1797));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 24, 36, 1807));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 24, 36, 1816));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 24, 36, 1825));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 24, 36, 1833));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 24, 36, 1842));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 24, 36, 1851));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 24, 36, 1861));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 24, 36, 1872));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 24, 36, 1881));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 24, 36, 1890));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 24, 36, 1908));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 24, 36, 1918));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 24, 36, 1927));
        singleBattle.simulateBattle(1929, dropSoldiers, true);
        try {
            FileUtils.writeStringToFile(new File("logs/battleStateServer.txt"), singleBattle.getBattleSimulator().getStateLogger());
        } catch (IOException e) {
            System.out.println("error write file");
        }
    }

}
