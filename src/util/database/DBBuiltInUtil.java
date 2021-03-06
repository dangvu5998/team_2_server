package util.database;

import bitzero.util.common.business.CommonHandle;
import bitzero.util.datacontroller.business.DataController;
import com.google.gson.Gson;

import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.server.ServerUtil;

import java.util.ArrayList;
import java.util.Map;

public class DBBuiltInUtil {
    private static final Logger logger = LoggerFactory.getLogger("DBBuiltInUtil");
    public static final Gson gsonWithExpose = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    static final String COUNTER_COLLECTION = "COUNTER_P";

    public static void save(String collectionName, String key, Object obj) {
        String globalKey = ServerUtil.getModelKeyName(collectionName, key);
        String sobj = gsonWithExpose.toJson(obj);
        try {
            DataController.getController().set(globalKey, sobj);
        } catch (Exception e) {
            CommonHandle.writeErrLog(e);
            logger.warn("Get error to save key '" + key + "' to collection '" + collectionName);
        }
    }

    public static void save(String collectionName, String key, String val) {
        String globalKey = ServerUtil.getModelKeyName(collectionName, key);
        try {
            DataController.getController().set(globalKey, val);
        } catch (Exception e) {
            CommonHandle.writeErrLog(e);
        }
    }

    public static void delete(String collectionName, String key) {
        String globalKey = ServerUtil.getModelKeyName(collectionName, key);
        try {
            DataController.getController().delete(globalKey);
        } catch (Exception e) {
            CommonHandle.writeErrLog(e);
            logger.warn("Get error to delete key '" + key + "' to collection '" + collectionName);
        }
    }

    public static Object get(String collectionName, String key, Class c) {
        String globalKey = ServerUtil.getModelKeyName(collectionName, key);
        try {
            return gsonWithExpose.fromJson((String) DataController.getController().get(globalKey), c);
        } catch (Exception e) {
            CommonHandle.writeErrLog(e);
            logger.warn("Get error to get key '" + key + "' to collection '" + collectionName);
        }
        return null;
    }

    public static String get(String collectionName, String key) {
        String globalKey = ServerUtil.getModelKeyName(collectionName, key);
        try {
            return (String) DataController.getController().get(globalKey);
        } catch (Exception e) {
            CommonHandle.writeErrLog(e);
            logger.warn("Get error to get key '" + key + "' to collection '" + collectionName);
        }
        return null;
    }

    public static ArrayList<String> multiget(String collectionName, ArrayList<String> keys) {

        ArrayList<String> globalKeys = new ArrayList<>();

        for(String key: keys) {
            globalKeys.add(ServerUtil.getModelKeyName(collectionName, key));
        }
        ArrayList<String> result = new ArrayList<>();
        try {

            Map<String, Object> res = DataController.getController().multiget(globalKeys);
            for(String globalKey: globalKeys) {
                result.add((String) res.get(globalKey));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error get " + keys + " in " + collectionName);
        }
        return result;
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
            CommonHandle.writeErrLog(err);
            throw new RuntimeException("Error generate id collection " + collectionName);
        }
    }

}


