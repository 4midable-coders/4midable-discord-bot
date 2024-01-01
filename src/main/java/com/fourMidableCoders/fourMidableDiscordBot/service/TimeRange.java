package com.fourMidableCoders.fourMidableDiscordBot.service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


//This class is used to calculate the time range for the calendar events.
public class TimeRange {

    //This enum is used to determine the time range.
    //Using an enum makes it easier to add more time ranges later on, and ensures that only valid time ranges can be used.
    public enum TimeRangeType {
        TODAY, TOMORROW, WEEK, YEAR
    }

    //The start and end of the time range.
    private ZonedDateTime start;
    private ZonedDateTime end;

    //The DateTimeFormatter is used to format the start and end of the time range. This is needed for the Google Calendar API to work properly.
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");


    //The constructor of the class. It takes the time range type as a parameter. Refer to the TimeRange enum for the possible time range types.
    public TimeRange(TimeRangeType rangeType) {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.systemDefault());
        switch (rangeType) {
            case TODAY:
                this.start = now.truncatedTo(ChronoUnit.DAYS);
                this.end = this.start.plusDays(1);
                break;
            case TOMORROW:
                this.start = now.truncatedTo(ChronoUnit.DAYS).plusDays(1);
                this.end = this.start.plusDays(1);
                break;
            case WEEK:
                this.start = now.truncatedTo(ChronoUnit.DAYS);
                this.end = this.start.plusWeeks(1);
                break;
            case YEAR:
                this.start = now.truncatedTo(ChronoUnit.DAYS);
                this.end = this.start.plusYears(1);
                break;
            default:
                throw new IllegalArgumentException("Invalid time range type");
        }
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

    //These methods return the start and end of the time range as a formatted string. This is needed for the Google Calendar API to work properly.
    public String getStartFormatted() {
        return start.format(formatter);
    }

    public String getEndFormatted() {
        return end.format(formatter);
    }
}
