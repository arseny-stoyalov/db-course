package course.parking;

import course.passenger.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingRepository extends JpaRepository<Passenger, Long> {
}
