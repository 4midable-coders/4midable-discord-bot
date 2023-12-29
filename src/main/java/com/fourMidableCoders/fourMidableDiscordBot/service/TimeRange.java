package com.fourMidableCoders.fourMidableDiscordBot.service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class TimeRange {
    public enum TimeRangeType {
        TODAY,
        TOMORROW,
        WEEK
    }
    private ZonedDateTime start;
    private ZonedDateTime end;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");

    public TimeRange(ZonedDateTime now, TimeRangeType rangeType) {
        calculateRange(now, rangeType);
    }

    private void calculateRange(ZonedDateTime now, TimeRangeType rangeType) {
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

    public String getStartFormatted() {
        return start.format(formatter);
    }

    public String getEndFormatted() {
        return end.format(formatter);
    }
}
