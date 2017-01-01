package org.ishausa.transport.carpool.store;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

/**
 * Created by tosri on 12/31/2016.
 */
public class MongoDb {
    private static volatile MongoClient mongoClient;

    public static MongoDatabase getDatabase() {
        if (mongoClient == null) {
            mongoClient = new MongoClient("localhost");
        }

        return mongoClient.getDatabase("carpool-app");
    }
}
