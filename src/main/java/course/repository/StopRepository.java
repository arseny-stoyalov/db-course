package course.repository;

import course.dto.Passenger;
import course.dto.Stop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StopRepository extends JpaRepository<Stop, Long> {
}
