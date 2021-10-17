package course.views.passengersinfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Immutable
@Table(name = "passenger_info")
public class PassengerInfo {

    @Id
    private Integer id;

    private String name;

    private String surname;

    private String phone;

    private Boolean payed;

    private String seat;

    private LocalTime schedule;

    @Column(name = "route_number")
    private Integer routeNumber;

    private String route;

}
