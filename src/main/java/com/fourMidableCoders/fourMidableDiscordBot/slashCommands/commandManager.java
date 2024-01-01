package com.fourMidableCoders.fourMidableDiscordBot.slashCommands;

import com.fourMidableCoders.fourMidableDiscordBot.service.GoogleService;
import com.fourMidableCoders.fourMidableDiscordBot.service.JokeService;
import com.fourMidableCoders.fourMidableDiscordBot.service.TimeRange;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;


//This class handles the slash commands.
public class commandManager extends ListenerAdapter {


    //This method sends the calendar entries of a certain TimeRange as a message to the channel where the slash command was used.
    static void sendCalendarAsMessage(SlashCommandInteractionEvent event, TimeRange.TimeRangeType timeRange) {
        try {
            event.reply(GoogleService.getEventsByTimeRange(timeRange)).queue();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //This method is called when the bot is ready. It adds the slash commands to the server.
    //Without it, slash command would not work at all, regardless of whether they are defined in onSlashCommandInteraction.
    @Override
    public void onGuildReady(GuildReadyEvent event) {
        List<CommandData> commandDataList = new ArrayList<>();
        commandDataList.add(Commands.slash("joke", "Tells a joke"));
        commandDataList.add(Commands.slash("calendartoday", "Displays the google calendar entries for today"));
        commandDataList.add(Commands.slash("calendartomorrow", "Displays the google calendar entries for tomorrow"));
        commandDataList.add(Commands.slash("calendarweek", "Displays the google calendar entries for the week"));
        commandDataList.add(Commands.slash("evaluations", "Displays the google calendar entries of the evaluation days"));
        event.getGuild().updateCommands().addCommands(commandDataList).queue();
    }


    //This method is called when a slash command is used. It checks which command was used and then executes the appropriate code.
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();
        String user = event.getUser().getAsMention();
        //Check if the command is "joke". If it is, send a joke.
        switch (command) {
            case "joke": {
                event.reply(JokeService.getJoke()).queue();
                break;
            }
            //Check if the command is "calendartoday". If it is, send the calendar entries for today.
            case "calendartoday": {
                sendCalendarAsMessage(event, TimeRange.TimeRangeType.TODAY);
                break;
            }//Check if the command is "calendartomorrow". If it is, send the calendar entries for tomorrow.
            case "calendartomorrow": {
                sendCalendarAsMessage(event, TimeRange.TimeRangeType.TOMORROW);
                break;
            }
            //Check if the command is "calendarweek". If it is, send the calendar entries for the week.
            case "calendarweek": {
                sendCalendarAsMessage(event, TimeRange.TimeRangeType.WEEK);
                break;
                }
            case "evaluations": {
                event.reply(GoogleService.getEventsByName("Evaluation Week")).queue();
                break;
            }
        }
    }
}

