package com.fourMidableCoders.fourMidableDiscordBot.listeners;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.unions.DefaultGuildChannelUnion;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class eventListener extends ListenerAdapter {

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        User user = event.getUser();
        String reaction = event.getReaction().getEmoji().getAsReactionCode();
        Channel channel = event.getChannel();
        String channelMention = channel.getAsMention();

        String message = user.getAsMention() + " reacted to message with " + reaction + " in channel " + channelMention + "!";
        DefaultGuildChannelUnion defaultchan = event.getGuild().getDefaultChannel();
        defaultchan.asTextChannel().sendMessage(message).queue();

    }
}
