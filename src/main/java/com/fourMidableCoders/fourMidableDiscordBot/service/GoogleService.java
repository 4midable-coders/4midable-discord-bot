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
import java.io.*;
import java.security.GeneralSecurityException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

import java.util.List;

/* The class for the google API */
public class GoogleService {
    // enum for the time range
    public enum TimeRange {
        TODAY,
        TOMORROW,
        WEEK
    }

    // method to get calendar service object. This is used to make API calls to Google Calendar.
    public static Calendar getCalendarService() throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar calendarService =
                new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                        .setApplicationName(APPLICATION_NAME)
                        .build();
        return calendarService;
    }
    // method to get events from calendar as a list of strings
    public static String getEvents(TimeRange timeRange) throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        Calendar calendarService = getCalendarService();
        //Declare timeMin and timeMax variables to set the time range for the events.
        DateTime timeMin;
        DateTime timeMax;
        //Get the current time and format it to the RFC3339 format.
        ZonedDateTime now = ZonedDateTime.now(ZoneId.systemDefault());
        //Set the time range based on the time range enum.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
        switch (timeRange) {
            case TODAY:
                timeMin = new DateTime(now.toLocalDate().atStartOfDay(now.getZone()).format(formatter));
                timeMax = new DateTime(now.toLocalDate().plusDays(1).atStartOfDay(now.getZone()).format(formatter));
                break;
            case TOMORROW:
                timeMin = new DateTime(now.toLocalDate().plusDays(1).atStartOfDay(now.getZone()).format(formatter));
                timeMax = new DateTime(now.toLocalDate().plusDays(2).atStartOfDay(now.getZone()).format(formatter));
                break;
            case WEEK:
                timeMin = new DateTime(now.toLocalDate().atStartOfDay(now.getZone()).format(formatter));
                timeMax = new DateTime(now.toLocalDate().plusWeeks(1).atStartOfDay(now.getZone()).format(formatter));
                break;
            default:
                throw new IllegalArgumentException("Invalid time range");
        }
        // List the events from the primary calendar within the time range.
        Events events = calendarService.events().list("c_classroomb5302f41@group.calendar.google.com")
                .setMaxResults(10)
                .setTimeMin(timeMin)
                .setTimeMax(timeMax)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();
        List<String> eventList = new ArrayList<>();
        for (Event event : items) {
            String start;
            if (event.getStart().getDateTime() != null) {
                start = event.getStart().getDateTime().toStringRfc3339();
            } else {
                start = event.getStart().getDate().toStringRfc3339();
            }
            eventList.add(event.getSummary() + " (" + start + ")");
        }
        String formattedEvents = String.join("\n", eventList);
        return formattedEvents;
    }

    /**
     * Application name.
     */
    private static final String APPLICATION_NAME = "4midable-discord-bot";
    /**
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    /**
     * Directory to store authorization tokens for this application.
     */
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES =
            Collections.singletonList(CalendarScopes.CALENDAR_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
            throws IOException {
        // Load client secrets.
        InputStream in = new FileInputStream("/Users/peterliebhart/Desktop/Private Projects/4midable-discord-bot/credentials.json");
        if (in == null) {
            throw new FileNotFoundException(CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
        //returns an authorized Credential object.
        return credential;
    }

}
