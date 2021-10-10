package course.repository;

import course.dto.Passage;
import course.dto.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassageRepository extends JpaRepository<Passage, Long> {
}
