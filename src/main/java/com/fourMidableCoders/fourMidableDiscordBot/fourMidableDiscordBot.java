package com.fourMidableCoders.fourMidableDiscordBot;


import com.fourMidableCoders.fourMidableDiscordBot.listeners.eventListener;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.DefaultShardManager;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.security.auth.login.LoginException;
import java.util.Properties;


//This class starts the bot and sets the status and the activity of the bot.
//Consider it as the main class of the bot.
public class fourMidableDiscordBot {

    private final ShardManager shardManager;


    //The constructor of the class.
    public fourMidableDiscordBot() throws LoginException {
        Properties prop = ConfigLoader.loadProperties(); //Loads the properties of the config.properties file.
        String token = prop.getProperty("DISCORD_BOT_TOKEN"); //The token of the bot.
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
        builder.setStatus(OnlineStatus.ONLINE); //sets the bots status. The status can be changed to Online, Idle, DoNotDisturb and Invisible.
        builder.setActivity(Activity.customStatus("Thinking about Everyone Codes")); //sets the bots activity. The activity can be changed to playing, streaming, listening and watching.
        shardManager = builder.build(); //builds the shardManager object.

        //add custom event listeners to the shardManager object. The event listeners are located in the listeners package. You can add as many event listeners as you want.
        shardManager.addEventListener(new eventListener());
    }

    //This method returns the ShardManager object.
    //The shardManager object is used to get the guilds, the users, the channels and the roles of the bot.
    //The shardManager object is also used to get the event listeners of the bot.
    //That is why the shardManager object is needed in the Main class.
    public ShardManager getShardManager() {
        return shardManager;
    }

    //This method starts the bot and handles the LoginException if needed.
    //The LoginException is thrown if the provided token is invalid.
    public static void main(String[] args) {
        try {
            fourMidableDiscordBot bot = new fourMidableDiscordBot();
        } catch (LoginException e) {
            System.out.println("ERROR: Provided bot token is invalid.");
        }
    }
}
