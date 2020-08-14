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
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 33, 33, 1));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 33, 32, 10));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 33, 28, 20));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 32, 24, 29));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 30, 20, 39));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 28, 17, 49));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 24, 14, 60));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 20, 14, 70));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 17, 15, 81));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 9, 26, 124));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 12, 13, 268));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 15, 12, 278));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 24, 14, 289));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 32, 19, 300));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 31, 30, 310));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 37, 36, 321));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 39, 39, 331));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 39, 39, 341));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 39, 39, 352));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 39, 39, 363));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 39, 39, 373));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 39, 39, 384));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 39, 39, 395));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 39, 37, 404));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 41, 33, 414));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 41, 32, 424));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 41, 32, 435));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 41, 32, 445));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 41, 32, 456));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 41, 32, 467));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 41, 32, 477));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 41, 32, 488));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_4", 41, 32, 498));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 30, 39, 664));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 30, 39, 675));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 30, 39, 685));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 30, 39, 696));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 30, 39, 706));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 31, 38, 716));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 31, 38, 726));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_2", 31, 38, 737));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 32, 39, 1129));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 33, 38, 1140));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 34, 38, 1151));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 34, 38, 1161));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 34, 38, 1171));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 34, 38, 1181));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 34, 38, 1191));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 34, 38, 1202));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 34, 38, 1212));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 34, 38, 1222));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 34, 38, 1232));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 34, 38, 1242));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 34, 38, 1253));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 34, 38, 1263));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 34, 38, 1273));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 34, 38, 1283));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 34, 38, 1294));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 34, 38, 1304));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 34, 38, 1315));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 34, 38, 1326));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 34, 38, 1336));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 34, 38, 1346));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 34, 38, 1357));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 34, 38, 1367));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 34, 38, 1377));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 34, 38, 1387));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 34, 38, 1397));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 34, 38, 1408));
        dropSoldiers.add(new BattleSession.DropSoldier("ARM_1", 34, 38, 1419));
        singleBattle.simulateBattle(2466, dropSoldiers, true);
        try {
            FileUtils.writeStringToFile(new File("logs/battleStateServer.txt"), singleBattle.getBattleSimulator().getStateLogger());
        } catch (IOException e) {
            System.out.println("error write file");
        }
    }

}
