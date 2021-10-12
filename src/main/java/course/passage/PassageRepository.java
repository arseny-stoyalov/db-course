package course.passage;

import course.bus.Bus;
import course.passage.Passage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.List;

public interface PassageRepository extends JpaRepository<Passage, Long> {

    List<Passage> findBySchedule(LocalTime v);

    List<Passage> findByStatusId(Integer v);

    List<Passage> findByRouteId(Integer v);

    List<Passage> findByBusId(Integer v);

    List<Passage> findByDispatcherId(Integer v);

    List<Passage> findByDriverId(Integer v);

}
