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
@DataJpaTest
public class TimezonesTest3 {

    @Autowired
    DemoRepository demoRepository;

    @Test
    public void testTimezones3() {

        Calendar utcCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        utcCalendar.set(2019, Calendar.MARCH, 7, 10, 0, 0);

        logCalendar("Original UTC Cal", utcCalendar);

        utcCalendar = changeToGMT(utcCalendar);

        logCalendar("UTC Cal being written  ", utcCalendar);

        Calendar gmt1Calendar = Calendar.getInstance();
        gmt1Calendar.set(2019, Calendar.MARCH, 7, 10, 0, 0);

        logCalendar("GMT+1 Cal being written", gmt1Calendar);


        DemoEntity demoEntity1 = DemoEntity.builder()
                .id(1)
                .utcCalendar(utcCalendar)
                .gmt1Calendar(gmt1Calendar)
                .build();

        demoRepository.saveAndFlush(demoEntity1);


        Calendar utcCalendar2 = demoRepository.getUtcCalendar(demoEntity1.getId());
        Calendar gmt1Calendar2 = demoRepository.getGmt1Calendar(demoEntity1.getId());

        logCalendar("Read GMT+1 Cal", gmt1Calendar2);
        logCalendar("Read UTC Cal  ", utcCalendar2);

        Assertions.assertThat(gmt1Calendar2).as("Check GMT Calendar").isEqualTo(gmt1Calendar);
        Assertions.assertThat(utcCalendar2).as("Check UTC Calendar").isEqualTo(utcCalendar);
    }

    private Calendar changeToGMT(Calendar calendar) {
        Calendar toGmtCalendar = Calendar.getInstance();
        toGmtCalendar.setTimeInMillis(calendar.getTimeInMillis());

        return toGmtCalendar;
    }
}
