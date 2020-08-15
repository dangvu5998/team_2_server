package model.battle;

import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BattleSimulatorTest extends TestCase {

    public void testMap1() {
        SingleBattle singleBattle = new SingleBattle(1, 0, 500, 500);
        singleBattle.loadBattleMap();
        ArrayList<BattleSession.DropSoldier> dropSoldiers = new ArrayList<>();
        singleBattle.simulateBattle(0, dropSoldiers, true);
//        System.out.println("\ngold:" + singleBattle.getAvailGoldBattle() + "\nelixir:" + singleBattle.getAvailElixirBattle() + "\n%" + singleBattle.getDestroyedBattle());
        try {
            FileUtils.writeStringToFile(new File("logs/battleStateServer.txt"), singleBattle.getBattleSimulator().getStateLogger());
        } catch (IOException e) {
            System.out.println("error write file");
        }
    }

}
