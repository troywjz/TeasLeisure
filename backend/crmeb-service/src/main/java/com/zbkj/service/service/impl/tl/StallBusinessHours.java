package com.zbkj.service.service.impl.tl;

import com.zbkj.common.model.tl.StallDailyStatus;

import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Strict parser and evaluator for one day's expected stall hours. */
public final class StallBusinessHours {
    private static final Pattern FORMAT = Pattern.compile("^([01]\\d|2[0-3]):([0-5]\\d)-([01]\\d|2[0-3]):([0-5]\\d)$");

    private StallBusinessHours() { }

    public static TimeWindow parse(String value) {
        if (value == null) return null;
        Matcher matcher = FORMAT.matcher(value.trim());
        if (!matcher.matches()) return null;
        LocalTime start = LocalTime.of(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
        LocalTime end = LocalTime.of(Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)));
        if (start.equals(end)) return null;
        return new TimeWindow(start, end);
    }

    public static boolean isValid(String value) { return parse(value) != null; }

    /** Unknown/malformed records return null. */
    public static Boolean currentOpen(StallDailyStatus today, StallDailyStatus yesterday, LocalTime now) {
        if (today != null) {
            if (!Boolean.TRUE.equals(today.getIsOpen())) return Boolean.FALSE;
            TimeWindow window = parse(today.getHours());
            if (window == null) return null;
            if (window.isOvernight()) return !now.isBefore(window.getStart());
            return !now.isBefore(window.getStart()) && now.isBefore(window.getEnd());
        }
        if (yesterday == null || !Boolean.TRUE.equals(yesterday.getIsOpen())) return null;
        TimeWindow previous = parse(yesterday.getHours());
        if (previous == null || !previous.isOvernight()) return null;
        return now.isBefore(previous.getEnd()) ? Boolean.TRUE : null;
    }

    public static final class TimeWindow {
        private final LocalTime start;
        private final LocalTime end;

        private TimeWindow(LocalTime start, LocalTime end) { this.start = start; this.end = end; }
        public LocalTime getStart() { return start; }
        public LocalTime getEnd() { return end; }
        public boolean isOvernight() { return end.isBefore(start); }
    }
}
