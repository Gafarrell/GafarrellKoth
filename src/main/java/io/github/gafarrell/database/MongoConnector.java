package io.github.gafarrell.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoConnector {
    private MongoClient client;
    private MongoDatabase database;

    public MongoConnector(String dbURI)
    {
         client = MongoClients.create(dbURI);
    }

    public void select(String dbName)
    {
        database = client.getDatabase(dbName);
    }

    public MongoCollection<Document> getCollection(String collectionName){
        return database.getCollection(collectionName);
    }


}
