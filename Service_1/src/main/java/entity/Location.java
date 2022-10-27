package entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.*;


@Entity
@EqualsAndHashCode
@Data
@NoArgsConstructor
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "x")
    private int x;

    @Column(name = "y")
    private float y;

    @Column(name = "z")
    private Long z; //Поле не может быть null

    @Column(name = "name")
    private String name; //Поле не может быть null
}
