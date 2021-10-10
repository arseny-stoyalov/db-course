package course.dto;

import lombok.Data;

import javax.persistence.*;

@Data
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
