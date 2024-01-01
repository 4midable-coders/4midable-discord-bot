package com.fourMidableCoders.fourMidableDiscordBot.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* The class for the Google API */
public class GoogleService {

    // method to get calendar service object. This is used to make API calls to Google Calendar.
    public static Calendar getCalendarService() {
        try {
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            return new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT)).setApplicationName(APPLICATION_NAME).build();
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
            return null;
        }
    }

    // method to get events from calendar as a list of strings
    public static String getEventsByTimeRange(TimeRange.TimeRangeType timeRangeType) throws IOException, GeneralSecurityException {
        Calendar calendarService = getCalendarService();
        TimeRange timeRange = new TimeRange(timeRangeType);
        Events events = calendarService.events().list("c_classroomb5302f41@group.calendar.google.com").setTimeMin(new DateTime(timeRange.getStartFormatted())).setTimeMax(new DateTime(timeRange.getEndFormatted())).setOrderBy("startTime").setSingleEvents(true).execute();
        // List the events from the primary calendar within the time range.
        List<String> eventList = getEventList(events);
        return String.join("\n", eventList);
    }

    @NotNull
    private static List<String> getEventList(Events events) {
        List<Event> items = events.getItems();
        List<String> eventList = new ArrayList<>();
        String calendarEmoji = "\uD83D\uDCC5";
        String clockEmoji = "\uD83D\uDD52";
        //loop through all events and add them to the eventList as long as they are not all-day events
        for (Event event : items) {
            String startDateTime;
            //check if the event is an all-day event or not
            if (event.getStart().getDateTime() != null) {
                startDateTime = calendarEmoji + " " + event.getStart().getDateTime().toString().substring(0, 10) + "  " + clockEmoji + event.getStart().getDateTime().toString().substring(11, 16);
            } else {
                //skip all-day events
                continue;
            }
            eventList.add(" " + startDateTime + "   [" + event.getSummary() + "]");
        }
        return eventList;
    }

    public static String getEventsByName(String eventName) {
        try {
            Calendar calendarService = getCalendarService();
            TimeRange timeRange = new TimeRange(TimeRange.TimeRangeType.YEAR);
            String calendarEmoji = "\uD83D\uDCC5";
            Events events = calendarService.events().list("c_classroomb5302f41@group.calendar.google.com").setMaxResults(1000000).setTimeMin(new DateTime(timeRange.getStartFormatted())).setTimeMax(new DateTime(timeRange.getEndFormatted())).setOrderBy("startTime").setSingleEvents(true).execute();

            List<String> matchingEvents = new ArrayList<>();
            for (Event event : events.getItems()) {
                if (event.getSummary() != null && event.getSummary().contains(eventName)) {
                    String startDate = event.getStart().getDate().toString();
                    matchingEvents.add(calendarEmoji + " " + startDate + " - " + event.getSummary());
                }
            }
            return matchingEvents.isEmpty() ? "No matching all-day events found." : String.join("\n", matchingEvents);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Application name.
     */
    //Saves the application name in a variable so it can easily be reused and changed.
    private static final String APPLICATION_NAME = "4midable-discord-bot";
    /**
     * Global instance of the JSON factory.
     */
    //This is used in reading the credentials.json file.
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    /**
     * Directory to store authorization tokens for this application.
     */
    //this is the directory where the token is stored. It is used to prevent the user from having to authorize the bot every time it is started.
    //do not upload the token to github. It contains sensitive information.
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    //This is the scope of the Google Calendar API. It is used to determine what the bot can do with the API. In this case, it can only read the calendar.
    private static final List<String> SCOPES =
            Collections.singletonList(CalendarScopes.CALENDAR_READONLY);
    //This is the path to the credentials.json file. It is used to authorize the bot with the Google Calendar API.
    private static final String CREDENTIALS_FILE_PATH = "/Users/peterliebhart/Desktop/Private Projects/4midable-discord-bot/credentials.json";

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    //This method is used to authorize the bot with the Google Calendar API. It is called in the getCalendarService method.
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
            throws IOException {
        // Load client secrets.
        InputStream in = new FileInputStream(CREDENTIALS_FILE_PATH);
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        //returns an authorized Credential object.
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

}

