package com.fourMidableCoders.fourMidableDiscordBot.slashCommands;
import com.fourMidableCoders.fourMidableDiscordBot.service.GoogleService;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import static com.fourMidableCoders.fourMidableDiscordBot.service.GoogleService.TimeRange.TODAY;


//This class handles the slash commands.
public class commandManager extends ListenerAdapter {

    static void sendCalendarAsMessage(SlashCommandInteractionEvent event, GoogleService.TimeRange timeRange) {
        try {
            event.reply(GoogleService.getEvents(timeRange).toString()).queue();
        } catch (GeneralSecurityException e) {
            event.reply("Something went wrong. Please try again later.").queue();
        } catch (IOException e) {
            event.reply("Something went wrong. Please try again later.").queue();
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
                event.reply("How many " + user + "s do you need to change a lightbulb? Five. One to hold the lightbulb and four to turn the ladder.").queue();
                break;
            }
            //Check if the command is "calendar". If it is, send the upcoming calendar entries using the getEvents method from the GoogleService class.
            case "calendartoday": {
                sendCalendarAsMessage(event, TODAY);
                break;
            }
            case "calendartomorrow": {
                sendCalendarAsMessage(event, GoogleService.TimeRange.TOMORROW);
                break;
            }
            case "calendarweek": {
                sendCalendarAsMessage(event, GoogleService.TimeRange.WEEK);
                break;
            }
        }
    }
}

