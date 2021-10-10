package course.repository;

import course.dto.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingRepository extends JpaRepository<Passenger, Long> {
}
