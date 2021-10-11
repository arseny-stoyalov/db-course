package course.passenger;

import course.passenger.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    List<Passenger> findByNameStartsWithIgnoreCase(String name);

    List<Passenger> findBySurnameStartsWithIgnoreCase(String surname);

    List<Passenger> findByPassageId(Integer passageId);

    List<Passenger> findByPayed(Boolean payed);

    List<Passenger> findByPhoneNumberStartsWithIgnoreCase(String phoneNumber);

}
