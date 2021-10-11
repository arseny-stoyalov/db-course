package course.mechanic;

import course.mechanic.Mechanic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MechanicRepository extends JpaRepository<Mechanic, Long> {
}
