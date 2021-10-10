package course.dto;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "route")
public class Route {

    @Id
    private Long number;

    @Column
    private String name;

}
