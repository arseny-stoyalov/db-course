package course.repository;

import course.dto.Bus;
import course.dto.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusRepository extends JpaRepository<Bus, Long> {
}
