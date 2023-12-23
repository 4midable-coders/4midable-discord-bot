package com.fourMidableCoders.fourMidableDiscordBot;


//This class loads the config.properties file and returns a Properties object.
//This object is used to get the token and the prefix from the config.properties file.
//The token and the prefix are used in the Main class to start the bot and to set the prefix for the commands.
//The config.properties file is not uploaded to GitHub because it contains the token which is used to start the bot.
//The token is a secret and should not be uploaded to GitHub.


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    public static Properties loadProperties() {
        Properties prop = new Properties();
        try (InputStream input = new FileInputStream("/Users/peterliebhart/IdeaProjects/EC-4midable-discord-bot/4midable-discord-bot/config.properties")) {
            prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return prop;
    }
}
