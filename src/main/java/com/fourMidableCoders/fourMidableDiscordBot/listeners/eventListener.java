package com.fourMidableCoders.fourMidableDiscordBot.listeners;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.unions.DefaultGuildChannelUnion;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


//This class contains the event listeners, which are used to react to certain events. Slash command events are handled in the commandManager class.
public class eventListener extends ListenerAdapter {


    //This method is called when a user reacts to a message.
    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        //Get the user, the reaction (as code, so it can be shown as an actual emoji in discord) and the channel from the event.
        User user = event.getUser();
        String reaction = event.getReaction().getEmoji().getAsReactionCode();
        Channel channel = event.getChannel();
        //Get the channel as a mention so it can be mentioned in the message.
        String channelMention = channel.getAsMention();

        //Create the message and send it to the default channel of the guild.
        String message = "**" + user.getAsMention() + "**" + " reacted to a message with " + reaction + " in " + channelMention + "!";
        DefaultGuildChannelUnion defaultchan = event.getGuild().getDefaultChannel();
        //.queue() is needed for every action that is done with the JDA API. It queues the action and executes it when the JDA API is ready.
        defaultchan.asTextChannel().sendMessage(message).queue();


    }


}
