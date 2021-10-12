package course.stoproute;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StopRouteRepository extends JpaRepository<StopRoute, Long> {

    List<StopRoute> findByStopId(Integer v);

    List<StopRoute> findByRouteId(Integer v);

}
