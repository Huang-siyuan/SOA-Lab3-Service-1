package entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Data
@Table(name = "route")
@NoArgsConstructor
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id; // The value of the field must be greater than 0, The value of this field must be unique, The value of this field must be generated automatically

    @Column(name = "name")
    private String name; // Field cannot be null, String cannot be empty


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "coordinates_id")
    private Coordinates coordinates; // Field cannot be null

    @Column(name = "creation_date")
    private java.time.LocalDateTime creationDate; // The field cannot be null, the value of this field must be generated automatically

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "from_id")
    private Location fromLocation; // Field can be null

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "to_id")
    private Location to; // Field can be null

    @Column(name = "distance")
    private Integer distance; // The field can be null, the field value must be greater than 1

    public Route(String name, Coordinates coordinates, Location fromLocation, Location to, Integer distance) {
        this.name = name;
        this.coordinates = coordinates;
        this.fromLocation = fromLocation;
        this.to = to;
        this.distance = distance;
        this.creationDate = java.time.LocalDateTime.now();
    }
}
