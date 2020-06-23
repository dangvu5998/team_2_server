package util;


import java.util.Random;

public class Common {

    static Random rand = new Random();

    public static int currentTimeInSecond() {
        return Long.valueOf(System.currentTimeMillis() / 1000).intValue();
    }

    public static long generateId() {
        return ((long)currentTimeInSecond() << 32) | (long) (rand.nextInt(1 << 30));
    }


}
