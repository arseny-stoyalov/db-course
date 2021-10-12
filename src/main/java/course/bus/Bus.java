package course.bus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bus")
public class Bus {

    @Id
    private String registration;

    @Column(name = "repair_needed")
    private Boolean repairNeeded;

    @Column(name = "stop_id")
    private Integer stopId;

    @Column(name = "parking_id")
    private Integer parkingId;

}
