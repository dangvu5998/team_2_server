package model;

public class ResourceExchange {
    public static final int goldRate = 1000;
    public static final int elixirRate = 1000;
    public static final int timeRate = 120;

    public static int divideAndRoundUp(int x, int y) {
        int z = x / y;
        if(x % y != 0) {
            z += 1;
        }
        return z;
    }

    public static int goldToG(int gold) {
        if(gold < 0) {
            return 0;
        }
        return divideAndRoundUp(gold, goldRate);
    }

    public static int elixirToG(int elixir) {
        if(elixir < 0) {
            return 0;
        }
        return divideAndRoundUp(elixir, elixirRate);
    }

    public static int timeToG(int time) {
        if(time < 0) {
            return 0;
        }
        return divideAndRoundUp(time, timeRate);
    }
}
