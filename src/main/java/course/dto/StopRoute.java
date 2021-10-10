package course.dto;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "stop_route")
public class StopRoute {

    @Id
    private Long id;

    @Column(name = "route_id")
    private Integer routeId;

    @Column(name = "stop_id")
    private Integer stopId;

}
