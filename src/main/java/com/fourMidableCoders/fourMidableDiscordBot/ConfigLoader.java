package com.fourMidableCoders.fourMidableDiscordBot;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

//This class loads the config.properties file and returns a Properties object.
public class ConfigLoader {
    public static Properties loadProperties() {
        //Load the config.properties file as prop object.
        Properties prop = new Properties();
        //Try to load the config.properties file. If it fails, print the stacktrace.
        try (InputStream input = new FileInputStream("/Users/peterliebhart/Desktop/Private Projects/4midable-discord-bot/config.properties")) {
            prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return prop;
    }
}
