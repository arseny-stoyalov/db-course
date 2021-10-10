package course.dto;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "passage")
public class Passage {

    @Id
    private Long id;

    @Column
    private LocalTime schedule;

    @Column(name = "driver_id")
    private Integer driverId;

    @Column(name = "route_id")
    private Integer routeId;

    @Column(name = "bus_id")
    private String busId;

    @Column(name = "dispatcher_id")
    private Integer dispatcherId;

    @Column(name = "status_id")
    private Integer statusId;

}
