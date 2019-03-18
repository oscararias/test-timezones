package test.timezones.demo;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class DemoEntity {

    @Id
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar utcCalendar;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar gmt1Calendar;
}