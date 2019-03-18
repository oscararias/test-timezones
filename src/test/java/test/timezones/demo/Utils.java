package test.timezones.demo;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {

    private static final Logger log = LoggerFactory.getLogger(Utils.class);

    public static void logCalendar(String name, Calendar calendar) {
        log.info("{} - epoch: {} - hour of day: {} - timezone: {}", name, calendar.getTimeInMillis(),
                calendar.get(Calendar.HOUR_OF_DAY), calendar.getTimeZone().getID());
    }
}
