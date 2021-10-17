package course.tables.parking;

import course.tables.passenger.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingRepository extends JpaRepository<Passenger, Long> {
}
