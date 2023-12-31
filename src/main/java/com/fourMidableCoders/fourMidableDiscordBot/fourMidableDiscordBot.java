package com.fourMidableCoders.fourMidableDiscordBot;

import com.fourMidableCoders.fourMidableDiscordBot.listeners.eventListener;
import com.fourMidableCoders.fourMidableDiscordBot.slashCommands.commandManager;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.util.Properties;

//This class starts the bot and sets the status and the activity of the bot.
//Consider it as the main class of the bot.
public class fourMidableDiscordBot {

    private final ShardManager shardManager;

    //The constructor of the class.
    public fourMidableDiscordBot() throws LoginException {
        //Load the config.properties file as prop object.
        Properties prop = ConfigLoader.loadProperties();
        //Get the token from the prop object.
        String token = prop.getProperty("DISCORD_BOT_TOKEN");
        //create the shardManager object with the provided bot token.
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
        //Set the status and the activity of the bot.
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.customStatus("Thinking about Everyone Codes"));
        //Enable the GatewayIntents for the shardManager object.
        builder.enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.GUILD_PRESENCES);
        //Set the MemberCachePolicy to ALL to cache all members of the guilds.
        builder.setMemberCachePolicy(MemberCachePolicy.ALL);
        //Set the ChunkingFilter to ALL to cache all members of the guilds.
        builder.setChunkingFilter(ChunkingFilter.ALL);
        //Enable the CacheFlags for the shardManager object. This caches all the needed objects for the event listeners.
        builder.enableCache(CacheFlag.ACTIVITY, CacheFlag.CLIENT_STATUS, CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE, CacheFlag.ONLINE_STATUS);
        //build the shardManager object. (this starts the bot)
        shardManager = builder.build();

        //add custom event listeners to the shardManager object. The event listeners are located in the listeners package. You can add as many event listeners as you want.
        shardManager.addEventListener(new eventListener(), new commandManager());
    }

    //This is just a getter for the shardManager object. It is used in the eventListener class.
    public ShardManager getShardManager() {
        return shardManager;
    }

    //This method starts the bot and handles the LoginException if needed.
    //The LoginException is thrown if the provided token is invalid.
    public static void main(String[] args) {
        try {
            new fourMidableDiscordBot();
        } catch (LoginException e) {
            System.out.println("Invalid token provided. Please provide a valid token in the config.properties file.");
        }
    }
}
