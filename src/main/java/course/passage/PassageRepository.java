package course.passage;

import course.passage.Passage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassageRepository extends JpaRepository<Passage, Long> {
}
