package test.timezones.demo;

import java.util.Calendar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DemoRepository extends JpaRepository<DemoEntity, Integer> {

    @Query(value = "select d.utcCalendar from DemoEntity d where id=:id")
    Calendar getUtcCalendar(Integer id);

    @Query(value = "select d.gmt1Calendar from DemoEntity d where id = :id")
    Calendar getGmt1Calendar(Integer id);
}
