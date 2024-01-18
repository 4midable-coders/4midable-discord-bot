package com.fourMidableCoders.fourMidableDiscordBot.service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


//This class is used to calculate the time range for the calendar events.
public class TimeRange {

    //This enum is used to determine the time range.
    //Using an enum makes it easier to add more time ranges later on, and ensures that only valid time ranges can be used.
    public enum TimeRangeType {
        TODAY, TOMORROW, WEEK, MONTH, YEAR
    }

    //The start and end of the time range.
    private ZonedDateTime start;
    private ZonedDateTime end;

    //The DateTimeFormatter is used to format the start and end of the time range. This is needed for the Google Calendar API to work properly.
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");


    //The constructor of the class. It takes the time range type as a parameter. Refer to the TimeRange enum for the possible time range types.
    public TimeRange(TimeRangeType rangeType) {
        //Coordinated Universal Time (UTC) is used as the time zone for the time range.
        ZonedDateTime now = ZonedDateTime.now(ZoneId.systemDefault());
        //The switch statement is used to determine the start and end of the time range.
        switch (rangeType) {
            //The start is set to the current time and the end is set to the current time plus one day to represent the time range of today.
            case TODAY:
                this.start = now;
                this.end = this.start.plusDays(1);
                break;
            //The start is set to the current time plus one day and the end is set to the current time plus two days to represent the time range of tomorrow.
            case TOMORROW:
                this.start = now.plusDays(1);
                this.end = this.start.plusDays(1);
                break;
            //The start is set to the current time and the end is set to the current time plus one week to represent the time range of the next 7 days.
            case WEEK:
                this.start = now;
                this.end = this.start.plusWeeks(1);
                break;
            //The start is set to the current time and the end is set to the current time plus one year to represent the time range of the next 365 days.
            case YEAR:
                this.start = now;
                this.end = this.start.plusYears(1);
                break;
            //The start is set to the current time and the end is set to the current time plus one month to represent the time range of the next 30 days.
            case MONTH:
                this.start = now;
                this.end = this.start.plusMonths(1);
                break;
            default:
                throw new IllegalArgumentException("Invalid time range type");
        }
    }

    //These methods return the start and end of the time range as a formatted string. This is needed for the Google Calendar API to work properly.
    public String getStartFormatted() {
        return start.format(formatter);
    }

    public String getEndFormatted() {
        return end.format(formatter);
    }
}
