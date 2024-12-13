package main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.io.FileWriter;
import java.io.IOException;

public class Config implements Runnable {
    GamePanel gp;
    GameConfig gc = new GameConfig();

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
    public void saveConfigToMongoDB() {
        try (MongoClient mongoClient = MongoClients.create(MONGO_URI)) {
            MongoCollection<Document> collection = mongoClient
                    .getDatabase(DATABASE_NAME)
                    .getCollection(COLLECTION_NAME);

            // Create a config object and serialize to JSON
            gc.currentMap = gp.currentMap;
            gc.totalScore = gp.player.totalScore;
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(gc);

            // Convert JSON to BSON (MongoDB document)
            Document doc = Document.parse(json);
            doc.put("_id", CONFIG_ID); // Use a fixed ID for single config

            // Replace existing document or insert if not exists
            collection.replaceOne(new Document("_id", CONFIG_ID), doc, new com.mongodb.client.model.ReplaceOptions().upsert(true));

            System.out.println("Config saved to MongoDB successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Load configuration from MongoDB
    public void loadConfigFromMongoDB() {
        try (MongoClient mongoClient = MongoClients.create(MONGO_URI)) {
            MongoCollection<Document> collection = mongoClient
                    .getDatabase(DATABASE_NAME)
                    .getCollection(COLLECTION_NAME);

            // Retrieve the document by its ID
            Document doc = collection.find(new Document("_id", CONFIG_ID)).first();

            if (doc != null) {
                // Convert BSON (MongoDB document) to JSON
                String json = doc.toJson();

                // Deserialize JSON to a config object
                Gson gson = new Gson();
                GameConfig gameConfig = gson.fromJson(json, GameConfig.class);

                // Set game panel properties from the config
                if (gameConfig != null) {
                    gp.currentMap = gameConfig.currentMap;
                }

                System.out.println("Config loaded from MongoDB successfully.");
            } else {
                System.out.println("No config found in MongoDB. Using default settings.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Autosaving...");
                Thread.sleep(interval); // Wait for the specified interval
                saveToLocal();
            } catch (InterruptedException e) {
                System.out.println("Autosave thread interrupted.");
                break;
            }
        }
    }

    private synchronized void saveToLocal() {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("currentMap=" + gc.currentMap + "\n");
            writer.write("currentMap=" + gc.totalScore + "\n");
            System.out.println("Autosave completed.");
        } catch (IOException e) {
            System.out.println("Failed to save config file: " + e.getMessage());
        }
    }

    // Inner class to represent the configuration format
    static class GameConfig {
        public int currentMap;
        public int totalScore;
    }
}
