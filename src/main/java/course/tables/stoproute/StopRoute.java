package course.tables.stoproute;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
