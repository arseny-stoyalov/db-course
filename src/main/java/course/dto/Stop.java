package course.dto;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "stop")
public class Stop {

    @Id
    private Long id;

    @Column
    private String name;

}
