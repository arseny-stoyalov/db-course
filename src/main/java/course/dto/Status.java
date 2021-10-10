package course.dto;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "status")
public class Status {

    @Id
    private Long id;

    @Column
    private String name;

    @Column
    private Double completeness;

}
