package model.battle;

import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BattleSimulatorTest extends TestCase {

    public void testMap1() {
        SingleBattle singleBattle = new SingleBattle(3, 0, 500, 500);
        singleBattle.loadBattleMap();
        ArrayList<BattleSession.DropSoldier> dropSoldiers = new ArrayList<>();
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 6, 7, 1));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 6, 7, 9));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 9, 6, 19));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 10, 6, 30));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 13, 5, 41));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 17, 6, 51));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 20, 10, 60));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 28, 16, 80));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 29, 18, 91));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 29, 20, 102));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 28, 22, 111));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 27, 22, 121));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 24, 22, 131));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 24, 21, 142));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 23, 22, 152));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 25, 22, 162));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 27, 18, 324));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 27, 17, 334));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 27, 16, 345));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 25, 13, 355));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 21, 9, 366));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 17, 8, 376));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 9, 11, 387));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 8, 16, 398));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 10, 26, 408));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 7, 27, 419));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 11, 19, 429));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 27, 15, 459));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 28, 19, 639));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 28, 17, 650));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 26, 12, 660));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 22, 8, 670));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 18, 9, 680));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 15, 11, 691));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 14, 12, 701));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 13, 13, 712));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 9, 13, 723));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 6, 16, 733));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 4, 22, 743));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 4, 19, 753));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 8, 26, 761));
        singleBattle.simulateBattle(1262, dropSoldiers, true);
//        System.out.println("\ngold:" + singleBattle.getAvailGoldBattle() + "\nelixir:" + singleBattle.getAvailElixirBattle() + "\n%" + singleBattle.getDestroyedBattle());
        try {
            FileUtils.writeStringToFile(new File("logs/battleStateServer.txt"), singleBattle.getBattleSimulator().getStateLogger());
        } catch (IOException e) {
            System.out.println("error write file");
        }
    }

}
