package course.tables.status;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatusRepository extends JpaRepository<Status, Long> {

    List<Status> findByNameStartsWithIgnoreCase(String v);

    List<Status> findByCompleteness(Double v);

    List<Status> findByTotalStops(Integer v);

}
