package course.views.busschedule;

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
@Table(name = "bus_schedule")
public class BusSchedule {

    @Id
    private Integer id;

    private LocalTime schedule;

    @Column(name = "route_number")
    private Integer routeNumber;

    private String route;

    private String status;

    private Double completeness;

}
