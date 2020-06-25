package util.database;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import bitzero.server.config.ConfigHandle;
import org.bson.Document;

public class MongodbDatabase {
    private static MongodbDatabase instance;
    private MongoClient mongoClient;
    private MongoDatabase database;
    private static final String ID_COLLECTION_NAME = "uniqueIdentifierCounter";

    private MongodbDatabase() {
        String mongoUrl = ConfigHandle.instance().get("mongo_url");
        String mongoDatabase = ConfigHandle.instance().get("mongo_db");
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

    public static int generateId(String collectionName) {
        MongoCollection<Document> idCollection = getInstance().getDatabase().getCollection(ID_COLLECTION_NAME);
        Document query = new Document("_id", collectionName);
        Document update = new Document("$inc", new Document("count", 1));
        Document res =  idCollection.findOneAndUpdate(query, update);
        if (res == null) {
            // TODO: handle error
            return -1;
        }
        return res.getDouble("count").intValue();
    }

}
