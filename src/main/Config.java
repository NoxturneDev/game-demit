package main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Config implements Runnable {
    GamePanel gp;
    GameConfig gc = new GameConfig();
    List<ProfileData> profiles = new ArrayList<>();
    List<String[]> leaderboard = new ArrayList<>();


    private static final String filePath = "config.txt";
    private static final String MONGO_URI = "mongodb://localhost:27017"; // MongoDB connection URI
    private static final String DATABASE_NAME = "game_demit"; // MongoDB database
    private static final String COLLECTION_NAME = "config"; // MongoDB collection
    private static final String CONFIG_ID = "gameConfig"; // ID for the configuration document
    public long interval;

    public Config(GamePanel gp) {
        this.gp = gp;
    }

    // Save configuration to MongoDB
    private String currentProfile; // Tracks the currently selected profile

    public void saveNewProfile(String username) {
        try (MongoClient mongoClient = MongoClients.create(MONGO_URI)) {
            MongoCollection<Document> collection = mongoClient
                    .getDatabase(DATABASE_NAME)
                    .getCollection(COLLECTION_NAME);

            // Create a new document for the profile
            Document doc = new Document("_id", username)
                    .append("username", username)
                    .append("totalScore", 0)
                    .append("currentLevel", 1);

            // Insert the document into the collection
            collection.insertOne(doc);

            System.out.println("New profile '" + username + "' created successfully.");
            setCurrentProfile(username);
            saveConfigToMongoDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Save configuration for the current profile
    public void saveConfigToMongoDB() {
        if (currentProfile == null || currentProfile.isEmpty()) {
            System.out.println("No profile selected. Please select a profile before saving.");
            return;
        }

        try (MongoClient mongoClient = MongoClients.create(MONGO_URI)) {
            MongoCollection<Document> collection = mongoClient
                    .getDatabase(DATABASE_NAME)
                    .getCollection(COLLECTION_NAME);

            // Populate the GameConfig object with current game state
            gc.currentMap = gp.getCurrentStats().currentMap;
            gc.totalScore = gp.getCurrentStats().player.totalScore;
            gc.username = gp.player.username;
            gc.currentHealth = gp.getCurrentStats().player.life;
            gc.currentLevel = gp.getCurrentStats().currentLevel;
            gc.lastPositionX = gp.getCurrentStats().player.worldX;
            gc.lastPositionY = gp.getCurrentStats().player.worldY;
            gc.itemsCollected = new int[]{1, 2, 3, 4, 5};
            gc.characterStats = gp.getCurrentStats().player.level;

            // Serialize to JSON
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(gc);

            // Convert JSON to BSON (MongoDB document)
            Document doc = Document.parse(json);
            doc.put("_id", currentProfile); // Use currentProfile as unique identifier

            // Replace existing document or insert if not exists
            collection.replaceOne(new Document("_id", currentProfile), doc, new com.mongodb.client.model.ReplaceOptions().upsert(true));

            syncProfileWithLeaderboard();
            System.out.println("Config for profile '" + currentProfile + "' saved to MongoDB successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void syncProfileWithLeaderboard() {
        try (MongoClient mongoClient = MongoClients.create(MONGO_URI)) {
            MongoCollection<Document> leaderboardCollection = mongoClient
                    .getDatabase(DATABASE_NAME)
                    .getCollection("leaderboard");

            // Fetch the profile data
            Document profile = new Document("_id", currentProfile);
            Document doc = mongoClient.getDatabase(DATABASE_NAME)
                    .getCollection(COLLECTION_NAME)
                    .find(profile)
                    .first();

            if (doc != null) {
                // Create or update leaderboard entry
                Document leaderboardDoc = new Document("username", doc.getString("username"))
                        .append("level", doc.getInteger("currentLevel", 0))
                        .append("totalScore", doc.getInteger("totalScore", 0));

                leaderboardCollection.replaceOne(
                        new Document("username", doc.getString("username")),
                        leaderboardDoc,
                        new com.mongodb.client.model.ReplaceOptions().upsert(true)
                );

                System.out.println("Profile synced to leaderboard.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fetchLeaderboardData() {
        try (MongoClient mongoClient = MongoClients.create(MONGO_URI)) {
            MongoCollection<Document> leaderboardCollection = mongoClient
                    .getDatabase(DATABASE_NAME)
                    .getCollection("leaderboard");

            // Sort by totalScore in descending order and limit to 5 results
            List<Document> topPlayers = leaderboardCollection.find()
                    .sort(new Document("totalScore", -1))
                    .limit(5)
                    .into(new ArrayList<>());

            for (Document doc : topPlayers) {
                String[] playerData = new String[3];
                playerData[0] = doc.getString("username");
                playerData[1] = String.valueOf(doc.getInteger("level", 0));
                playerData[2] = String.valueOf(doc.getInteger("totalScore", 0));
                leaderboard.add(playerData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Set the current profile
    public void setCurrentProfile(String profileId) {
        this.currentProfile = profileId;
        System.out.println("Current profile set to: " + profileId);
    }


    // Load configuration from MongoDB
    // Load configuration for a specific profile
    public void loadConfigFromMongoDB(int profileIndex) {
        System.out.println(profileIndex);
        if (profiles.isEmpty() || profileIndex < 0 || profileIndex >= profiles.size()) {
            System.out.println("Invalid profile index.");
            return;
        }

        String profileId = profiles.get(profileIndex).username;
        System.out.println(profileId);
        try (MongoClient mongoClient = MongoClients.create(MONGO_URI)) {
            MongoCollection<Document> collection = mongoClient
                    .getDatabase(DATABASE_NAME)
                    .getCollection(COLLECTION_NAME);

            // Retrieve the document by its profile ID
            Document doc = collection.find(new Document("_id", profileId)).first();

            if (doc != null) {
                // Convert BSON (MongoDB document) to JSON
                String json = doc.toJson();

                // Deserialize JSON to a GameConfig object
                Gson gson = new Gson();
                GameConfig gc = gson.fromJson(json, GameConfig.class);

                // Set game panel properties from the config
                if (gc != null) {
                    gp.currentMap = gc.currentMap;
                    gp.player.totalScore = gc.totalScore;
                    gp.player.username = gc.username;
                    gp.player.life = gc.currentHealth;
                    gp.currentLevel = gc.currentLevel;
                    gp.player.worldX = gc.lastPositionX;
                    gp.player.worldY = gc.lastPositionY;
//                    gp.itemsCollected = gc.itemsCollected;
                    gp.player.level = gc.characterStats;

                    // Set the current profile to the loaded profile
                    setCurrentProfile(profileId);
                }

                System.out.println("Config for profile '" + profileId + "' loaded from MongoDB successfully.");
            } else {
                System.out.println("No config found for profile '" + profileId + "'. Using default settings.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listProfile() {
        profiles = new ArrayList<>();

        try (MongoClient mongoClient = MongoClients.create(MONGO_URI)) {
            MongoCollection<Document> collection = mongoClient.getDatabase(DATABASE_NAME).getCollection(COLLECTION_NAME);

            for (Document doc : collection.find()) {
                String username = doc.getString("username");
                int totalScore = doc.getInteger("totalScore", 0); // Default to 0 if field is missing
                int level = doc.getInteger("currentLevel", 1); // Default to 1 if field is missing

                profiles.add(new ProfileData(username, totalScore, level));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error retrieving profiles from MongoDB.");
        }

    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Autosaving...");
                Thread.sleep(interval); // Wait for the specified interval
                saveConfigToMongoDB();
//                saveToLocal();
            } catch (InterruptedException e) {
                System.out.println("Autosave thread interrupted.");
                break;
            }
        }
    }

    private synchronized void saveToLocal() {
        try (FileWriter writer = new FileWriter(filePath)) {
            gc.currentMap = gp.getCurrentStats().currentMap;
            gc.totalScore = gp.getCurrentStats().player.totalScore;
            writer.write("currentMap=" + gc.currentMap + "\n");
            writer.write("totalScore=" + gc.totalScore + "\n");
            System.out.println("Autosave completed.");
        } catch (IOException e) {
            System.out.println("Failed to save config file: " + e.getMessage());
        }
    }

    // Inner class to represent the configuration format
    static class GameConfig {
        public int currentMap;
        public int totalScore;
        public String username;
        public int currentHealth;
        public int currentLevel;
        public int lastPositionX;
        public int lastPositionY;
        public int[] itemsCollected;
        public int characterStats;
    }
}
