package course.tables.passenger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "passenger")
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column(name = "seat_id")
    private Integer seatId;

    @Column(name = "phone")
    private String phoneNumber;

    @Column
    private Boolean payed;

}
