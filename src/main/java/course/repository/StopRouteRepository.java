package course.repository;

import course.dto.Passenger;
import course.dto.StopRoute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StopRouteRepository extends JpaRepository<StopRoute, Long> {
}
