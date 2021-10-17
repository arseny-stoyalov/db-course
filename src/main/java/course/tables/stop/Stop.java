package course.tables.stop;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "stop")
public class Stop {

    @Id
    private Long id;

    @Column
    private String name;

}
