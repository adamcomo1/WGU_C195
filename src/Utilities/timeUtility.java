package Utilities;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * Class to hold different time converting methods.
 */
public class timeUtility {

    public static String convertToUTC(String dateTime) {
        Timestamp currentTimeStamp = Timestamp.valueOf(String.valueOf(dateTime));
        LocalDateTime localDateTime = currentTimeStamp.toLocalDateTime();
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
        ZonedDateTime UTCDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime localOUT = UTCDateTime.toLocalDateTime();
        return localOUT.format(DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss"));
        }
    public static LocalTime utcToEasternTime(String dateTimeString) {
        String string = dateTimeString.substring(11, 19);
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime localTime = LocalTime.parse(string, timeFormat);
        LocalTime easternTime = localTime.minusHours(5);
        return easternTime;
    }

}


