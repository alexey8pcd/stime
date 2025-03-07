package ru.alejov.util.stime;

import java.time.format.DateTimeFormatter;

/**
 * @author Ovcharov Alexey
 */
class Constants {
    static final String TIME_FORMAT = "HH:mm:ss";
    static final String REPORT_TIME_FORMAT = "HH 'h' mm 'm' ss 's'";
    static final String PAUSE = "<<pause>>";
    static final String BEGIN_DAY = "<<begin day>>";
    static final String END_DAY = "<<end day>>";

    static final DateTimeFormatter REPORT_FORMATTER = DateTimeFormatter.ofPattern(REPORT_TIME_FORMAT);
    static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);

    private Constants() {

    }
}
