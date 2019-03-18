package test.timezones.demo;

import java.util.Calendar;
import java.util.TimeZone;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static test.timezones.demo.Utils.logCalendar;

@RunWith(SpringRunner.class)
@DataJpaTest(properties = {"spring.jpa.properties.hibernate.jdbc.time_zone=UTC"})
public class TimezonesTest4 {

    @Autowired
    DemoRepository demoRepository;

    @Test
    public void testTimezones4() {

        Calendar utcCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        utcCalendar.set(2019, Calendar.MARCH, 7, 10, 0, 0);

        logCalendar("UTC Cal being written  ", utcCalendar);

        Calendar gmt1CalendarBefore = Calendar.getInstance();
        gmt1CalendarBefore.set(2019, Calendar.MARCH, 7, 10, 0, 0);

        logCalendar("Original GMT+1 cal", gmt1CalendarBefore);

        Calendar gmt1CalendarAfter = changeToUTC(gmt1CalendarBefore);

        logCalendar("GMT+1 Cal being written", gmt1CalendarAfter);

        DemoEntity demoEntity1 = DemoEntity.builder()
                .id(1)
                .utcCalendar(utcCalendar)
                .gmt1Calendar(gmt1CalendarAfter)
                .build();

        demoRepository.saveAndFlush(demoEntity1);


        Calendar utcCalendar2 = demoRepository.getUtcCalendar(demoEntity1.getId());
        Calendar gmt1Calendar2 = demoRepository.getGmt1Calendar(demoEntity1.getId());

        logCalendar("Read UTC Cal  ", utcCalendar2);
        logCalendar("Read GMT+1 Cal", gmt1Calendar2);

        Assertions.assertThat(changeToUTC(utcCalendar2)).as("Check UTC Calendar").isEqualTo(utcCalendar);
        Assertions.assertThat(gmt1Calendar2).as("Check GMT Calendar").isEqualTo(gmt1CalendarBefore);
    }

    private Calendar changeToUTC(Calendar calendar) {
        Calendar toUtcCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        toUtcCalendar.setTimeInMillis(calendar.getTimeInMillis());

        return toUtcCalendar;
    }
}
