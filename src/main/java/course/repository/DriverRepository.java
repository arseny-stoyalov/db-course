package course.repository;

import course.dto.Driver;
import course.dto.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, Long> {
}