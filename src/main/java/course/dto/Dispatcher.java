package course.dto;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "dispatcher")
public class Dispatcher {

    @Id
    private Long id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private Integer phone;

    @Column(name = "passage_count")
    private Integer passageCount;

}
