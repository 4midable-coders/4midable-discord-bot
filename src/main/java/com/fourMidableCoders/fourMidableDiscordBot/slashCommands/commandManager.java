package com.fourMidableCoders.fourMidableDiscordBot.slashCommands;
import com.fourMidableCoders.fourMidableDiscordBot.service.GoogleService;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import com.google.api.services.calendar.Calendar;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;


//This class handles the slash commands.
public class commandManager extends ListenerAdapter {

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        List<CommandData> commandDataList = new ArrayList<>();
        commandDataList.add(Commands.slash("joke", "Tells a joke"));
        commandDataList.add(Commands.slash("calendar", "Displays the upcoming google calendar entries for Intake 1"));
        event.getGuild().updateCommands().addCommands(commandDataList).queue();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();
        //Check if the command is "joke". If it is, send a joke.
        if (command.equals("joke")) {
            String user = event.getUser().getAsMention();
            event.reply("How many " + user + "s do you need to change a lightbulb? Five. One to hold the lightbulb and four to turn the ladder.").queue();
        } else if (command.equals("calendar")) {
            try {
                event.reply(GoogleService.getEvents().toString()).queue();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (GeneralSecurityException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

