package course.stop;

import course.stop.Stop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StopRepository extends JpaRepository<Stop, Long> {
}
