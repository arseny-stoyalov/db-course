package course.driver;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "mechanic")
public class Driver {

    @Id
    private Long id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String phone;

    @Column
    private Boolean busy;

}
