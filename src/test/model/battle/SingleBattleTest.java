package model.battle;

import junit.framework.TestCase;
import model.map.ElixirContainable;
import model.map.GoldContainable;
import model.map.MapObject;

public class SingleBattleTest extends TestCase {

    public void testDistributeResource() {
        for(int mapId = 1; mapId <= 10; mapId++) {
            SingleBattle battle = new SingleBattle(mapId, 0, -1, -1);
            int defautGold = SingleBattle.getDefaultGold(mapId);
            int defautElixir = SingleBattle.getDefaultElixir(mapId);
            int totalGold = 0;
            int totalElixir = 0;
            for(MapObject mapObj: battle.getBattleMapObjects()) {
                if(mapObj instanceof GoldContainable) {
                    GoldContainable goldContainable = (GoldContainable) mapObj;
                    totalGold += goldContainable.getGold();
                }
                if(mapObj instanceof ElixirContainable) {
                    ElixirContainable elixirContainable = (ElixirContainable) mapObj;
                    totalElixir += elixirContainable.getElixir();
                }
            }
            assertEquals(defautGold, battle.getAvailGold());
            assertEquals(defautGold, totalGold);
            assertEquals(defautElixir, battle.getAvailElixir());
            assertEquals(defautElixir, totalElixir);
        }
    }
}
