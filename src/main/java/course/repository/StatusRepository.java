package course.repository;

import course.dto.Passenger;
import course.dto.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {
}
