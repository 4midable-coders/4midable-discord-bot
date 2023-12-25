package com.fourMidableCoders.fourMidableDiscordBot.slashCommands;

import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.util.ArrayList;
import java.util.List;


//This class handles the slash commands.
public class commandManager extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();
        //Check if the command is "joke". If it is, send a joke.
        if (command.equals("joke")) {
            String user = event.getUser().getAsMention();
            event.reply("How many " + user + "s do you need to change a lightbulb? Five. One to hold the lightbulb and four to turn the ladder.").queue();
        }
    }


    //This method is called when the bot joins a guild. It registers the slash commands for the guild.
    @Override
    public void onGuildReady(GuildReadyEvent event) {
        List<CommandData> commandDataList = new ArrayList<>();
        commandDataList.add(Commands.slash("joke", "Tells a joke"));
        event.getGuild().updateCommands().addCommands(commandDataList).queue();
    }
}


