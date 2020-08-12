package util;


import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Common {

    static Random rand = new Random();

    public static int currentTimeInSecond() {
        return Long.valueOf(System.currentTimeMillis() / 1000).intValue();
    }

    public static long generateId() {
        return ((long)currentTimeInSecond() << 32) | (long) (rand.nextInt(1 << 30));
    }

    public static JSONObject loadJSONObjectFromFile(String fileName) {
        try {
            String configContent = FileUtils.readFileToString(new File(fileName), "UTF-8");
            return new JSONObject(configContent);
        } catch (IOException | JSONException ex) {
            // TODO: log error
        }
        return null;
    }

    public static double calcSquareDistance(double x1, double y1, double x2, double y2) {
        return Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2);
    }

    public static double calcEuclideanDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(calcSquareDistance(x1, y1, x2, y2));
    }

    public static double calcManhattanDistance(double x1, double y1, double x2, double y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    public static int calcManhattanDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    public static double calcGridDistance(double x1, double y1, double x2, double y2) {
        double diffX = Math.abs(x1 - x2);
        double diffY = Math.abs(y1 - y2);
        return Math.min(diffX, diffY) * 1.4 + Math.abs(diffX - diffY);
    }

    public static int[][] getNeighborPoints(double x, double y, double r) {
        ArrayList<int[]> neighbors = new ArrayList<>();
        double squareR = r * r;
        for(int i = (int) Math.ceil(x - r); i <= (int)Math.floor(x + r); i++) {
            for(int j = (int) Math.ceil(y - r); j <= (int) Math.floor(y + r); j++) {
                if(calcSquareDistance(i, j, x, y) <= squareR) {
                    neighbors.add(new int[] {i, j});
                }
            }
        }
        return (int[][]) neighbors.toArray();
    }

    public static class LinearCongruentialGenerator {
        private int seed;
        private final int a;
        private final int c;
        private final int m;
        public LinearCongruentialGenerator(int seed, int a, int c, int m) {
            this.seed = seed;
            this.a = a;
            this.c = c;
            this.m = m;
        }
        public int getNext() {
            seed = (a * seed + c) % m;
            return seed;
        }
    }
}
