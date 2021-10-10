package course.dto;

import course.Utils;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Data
@Table(name = "passenger")
public class Passenger {

    @Id
    private Long id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column(name = "passage_id")
    private Integer passageId;

    @Column(name = "phone")
    private String phoneNumber;

    @Column
    private Boolean payed;

    public Passenger() {
    }

    public Passenger(String name, String surname, Integer passageId, String phoneNumber, Boolean payed) {
        this.id = Utils.generateIntId();
        this.name = name;
        this.surname = surname;
        this.passageId = passageId;
        this.phoneNumber = phoneNumber;
        this.payed = payed;
    }

}
