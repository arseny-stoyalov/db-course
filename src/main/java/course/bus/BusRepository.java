package course.bus;

import course.bus.Bus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusRepository extends JpaRepository<Bus, String> {

    List<Bus> findByRepairNeeded(Boolean v);

    List<Bus> findByStopId(Integer v);

    List<Bus> findByParkingId(Integer v);

}
