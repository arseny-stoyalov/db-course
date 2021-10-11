package course.mechanic;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "mechanic")
public class Mechanic {

    @Id
    private Long id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String phone;

    @Column(name = "parking_id")
    private String parkingId;

}
