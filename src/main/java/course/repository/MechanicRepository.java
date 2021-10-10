package course.repository;

import course.dto.Mechanic;
import course.dto.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MechanicRepository extends JpaRepository<Mechanic, Long> {
}
