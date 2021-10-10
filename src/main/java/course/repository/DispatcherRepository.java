package course.repository;

import course.dto.Dispatcher;
import course.dto.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DispatcherRepository extends JpaRepository<Dispatcher, Long> {
}
