package com.wylxbot.wylxcore.mongodb;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.ReplaceOptions;
import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class DatabaseManager {
    private final MongoClient client;
    private final MongoDatabase database;
    private final DatabaseCollection<Document> globalsCollection;

    public DatabaseManager(String appName, String dbUrl, String dbName, String pojoPackage) {
        ConnectionString connStr = new ConnectionString(dbUrl);
        client = MongoClients.create(MongoClientSettings.builder()
                .applyConnectionString(connStr)
                .applicationName(appName)
                .applyToConnectionPoolSettings(
                        builder -> builder.maxWaitTime(1000, TimeUnit.MILLISECONDS)
                )
                .build());

        // Combine default codec with our POJOs to represent data objects
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().register(pojoPackage).build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
        database = client.getDatabase(dbName).withCodecRegistry(pojoCodecRegistry);

        globalsCollection = new DatabaseCollection<>(
                (String id) -> new Document(),
                database,
                "Globals",
                Document.class
        );
    }

    public void close() {
        client.close();
    }

    public <T> DatabaseCollection<T> createCollection(Function<String, T> defaultSupplier,
                                                      String name,
                                                      Class<T> clazz
    ) {
        return new DatabaseCollection<>(defaultSupplier, database, name, clazz);
    }

    public DatabaseCollection<Document> getGlobalsCollection() {
        return globalsCollection;
    }
}
