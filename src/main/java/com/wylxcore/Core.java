package com.wylxcore;

import com.wylxcore.Database.DatabaseManager;
import com.wylxcore.Events.Event;
import com.wylxcore.Events.Package;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Core {
    private final boolean isRelease;
    private final String token;
    private final String databaseURL;
    private final DatabaseManager databaseManager;
    private final JDA jda;

    public Core(String token, String databaseURL) {
        this(token, null, databaseURL, null, true);
    }
    public Core(String releaseToken, String betaToken, String releaseDatabaseURL, String betaDatabaseURL, boolean isRelease) {
        this.isRelease = isRelease;

        // Select variables to use
        if(isRelease) {
            token = releaseToken;
            databaseURL = releaseDatabaseURL;
        } else {
            token = betaToken;
            databaseURL = betaDatabaseURL;
        }

        // Init the database
        databaseManager = new DatabaseManager(databaseURL, isRelease);

        // Init the bot
        jda = JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT) // TODO: Check if we want to actually require this by default
                .addEventListeners() // TODO: Add default event listeners
                .build();
    }

    // Register Event Package(s) with the processors
    public void registerPackage(Package... packages){

    }

    // Argue with me about the naming of this function
    // Registers an event to the default package which contains events that cannot be disabled
    public void registerEventToDefault(Event... events) {

    }

    public JDA getJda() {
        return jda;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }
}
