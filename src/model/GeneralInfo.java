package model;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.ReplaceOptions;
import model.map.MapObject;
import org.bson.Document;
import  util.database.MongodbDatabase;

public class GeneralInfo {
    public int getId() {
        return id;
    }

    public int getVipLevel() {
        return vipLevel;
    }

    public String getUsername() {
        return username;
    }

    public int getFrameScore() {
        return frameScore;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public int getUserXP() {
        return userXP;
    }

    public int getG() {
        return g;
    }

    public boolean isMusic() {
        return music;
    }

    public boolean isSound() {
        return sound;
    }

    public boolean isNotifications() {
        return notifications;
    }

    public boolean isCreating() {
        return creating;
    }

    private int id;
    private int vipLevel;
    private String username;
    private int frameScore;
    private int userLevel;
    private int userXP;
    private int g;
    private boolean music;
    private boolean sound;
    private boolean notifications;
    private boolean creating = false;

    private static String collectionName = "GeneralInfo";

    public GeneralInfo(int vipLevel, String username, int frameScore, int userLevel, int userXP,
                       int g, boolean music, boolean sound, boolean notifications) {
        this.id = MongodbDatabase.generateId(collectionName);
        this.creating = true;
        this.username = username;
        this.vipLevel = vipLevel;
        this.username = username;
        this.frameScore = frameScore;
        this.userLevel = userLevel;
        this.userXP = userXP;
        this.g = g;
        this.music = music;
        this.sound = sound;
        this.notifications = notifications;
    }

    public GeneralInfo(int id, int vipLevel, String username, int frameScore, int userLevel, int userXP,
                       int g, boolean music, boolean sound, boolean notifications) {
        this.id = id;
        this.username = username;
        this.vipLevel = vipLevel;
        this.username = username;
        this.frameScore = frameScore;
        this.userLevel = userLevel;
        this.userXP = userXP;
        this.g = g;
        this.music = music;
        this.sound = sound;
        this.notifications = notifications;
    }

    public static GeneralInfo getNewGeneralInfo(String username) {
        GeneralInfo generalInfo = new GeneralInfo(
                0,
                username,
                500,
                1,
                0,
                0,
                true,
                true,
                true
        );
        MapObject.initialMapForUser(generalInfo.id);
        return generalInfo;
    }

    public static MongoCollection<Document> getCollection() {
        return MongodbDatabase.getInstance().getDatabase().getCollection(collectionName);
    }

    public void save() {
        Document doc = new Document();
        doc.append("_id", id);
        doc.append("username", username);
        doc.append("vipLevel", vipLevel);
        doc.append("frameScore", frameScore);
        doc.append("userLevel", userLevel);
        doc.append("userXP", userXP);
        doc.append("g", g);
        doc.append("music", music);
        doc.append("sound", sound);
        doc.append("notifications", notifications);
        // TODO: handle uid collision

        getCollection().replaceOne(new Document("_id", id), doc, new ReplaceOptions().upsert(true));
    }

    public static GeneralInfo documentToGeneralInfo(Document doc) {
        GeneralInfo newGeneralInfo = new GeneralInfo(
                (int) doc.get("_id"),
                (int) doc.get("vipLevel"),
                (String) doc.get("username"),
                (int) doc.get("frameScore"),
                (int) doc.get("userLevel"),
                (int) doc.get("userXP"),
                (int) doc.get("g"),
                (boolean) doc.get("music"),
                (boolean) doc.get("sound"),
                (boolean) doc.get("notifications")
        );
        return newGeneralInfo;
    }

    public static GeneralInfo getGeneralInfoByUsername(String username) {
        Document doc = getCollection().find(new Document("username", username)).first();
        if(doc != null) {
            return documentToGeneralInfo(doc);
        }
        else {
            GeneralInfo newGeneralInfo = getNewGeneralInfo(username);
            newGeneralInfo.save();
            return  newGeneralInfo;
        }
    }
}
