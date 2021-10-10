package course.repository;

import course.dto.Passenger;
import course.dto.Route;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepository extends JpaRepository<Route, Long> {
}
