package course.parking;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "parking")
public class Parking {

    @Id
    private Long id;

    @Column
    private String locality;

    @Column
    private String address;

}
