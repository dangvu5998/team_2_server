package util.database;

import bitzero.util.datacontroller.business.DataController;
import com.google.gson.Gson;

import com.google.gson.GsonBuilder;
import util.server.ServerUtil;

public class DBBuiltInUtil {
    public static final Gson gson = new Gson();
    public static final Gson gsonWithExpose = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    static final String COUNTER_COLLECTION = "COUNTER";

    public static void save(String collectionName, String key, Object obj) {
        String globalKey = ServerUtil.getModelKeyName(collectionName, key);
        String sobj = gsonWithExpose.toJson(obj);
        try {
            DataController.getController().set(globalKey, sobj);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error save " + key + " in " + collectionName);
        }
    }

    public static void save(String collectionName, String key, String val) {
        String globalKey = ServerUtil.getModelKeyName(collectionName, key);
        try {
            DataController.getController().set(globalKey, val);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error save " + key + " in " + collectionName);
        }
    }

    public static void delete(String collectionName, String key) {
        String globalKey = ServerUtil.getModelKeyName(collectionName, key);
        try {
            DataController.getController().delete(globalKey);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error delete " + key + " in " + collectionName);
        }
    }

    public static Object get(String collectionName, String key, Class c) {
        String globalKey = ServerUtil.getModelKeyName(collectionName, key);
        try {
            return gsonWithExpose.fromJson((String) DataController.getController().get(globalKey), c);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error get " + key + " in " + collectionName);
        }
        return null;
    }

    public static String get(String collectionName, String key) {
        String globalKey = ServerUtil.getModelKeyName(collectionName, key);
        try {
            return (String) DataController.getController().get(globalKey);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error get " + key + " in " + collectionName);
        }
        return null;
    }

    public static int generateId(String collectionName) {
        String globalKey = ServerUtil.getModelKeyName(COUNTER_COLLECTION, collectionName);
        try {
            String counterStr = (String) DataController.getController().get(globalKey);
            if(counterStr != null) {
                int counter = Integer.parseInt(counterStr);
                DataController.getController().set(globalKey, String.valueOf(counter + 1));
                return counter;
            } else {
                DataController.getController().set(globalKey, String.valueOf(1));
                return 0;
            }
        } catch (Exception err) {
            // TODO: handle exception
            System.out.println("Error generate id collection " + collectionName);
        }
        return -1;
    }

}


