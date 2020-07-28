package util;


import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
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

}
