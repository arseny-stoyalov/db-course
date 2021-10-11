package course.dispatcher;

import course.dispatcher.Dispatcher;
import course.passenger.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DispatcherRepository extends JpaRepository<Dispatcher, Long> {

    List<Dispatcher> findByNameStartsWithIgnoreCase(String name);

    List<Dispatcher> findBySurnameStartsWithIgnoreCase(String surname);

    List<Dispatcher> findByPassageCount(Integer total);

    List<Dispatcher> findByPhoneStartsWithIgnoreCase(String phoneNumber);

}
