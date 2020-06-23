package util.database;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import bitzero.server.config.ConfigHandle;

public class MongodbDatabase {
    private static MongodbDatabase instance;
    private MongoClient mongoClient;
    private MongoDatabase database;

    private MongodbDatabase() {
        String mongoUrl = ConfigHandle.instance().get("mongo_url");
        String mongoDatabase = ConfigHandle.instance().get("mongo_db");
        System.out.println(mongoUrl);
        mongoClient = MongoClients.create(mongoUrl);
        database = mongoClient.getDatabase(mongoDatabase);
    }

    public static MongodbDatabase getInstance() {
        if (instance == null) {
            synchronized (MongodbDatabase.class) {
                if (instance == null) {
                    instance = new MongodbDatabase();
                }
            }
        }
        return instance;
    }

    public MongoDatabase getDatabase() {
        return this.database;
    }



}
