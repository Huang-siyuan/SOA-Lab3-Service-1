package ru.itmo.FirstService.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Data
@NoArgsConstructor
@Table(name = "coordinates")
public class Coordinates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "x")
    private float x;

    @Column(name = "y")
    private Integer y;

    public Coordinates(float x, Integer y) {
        this.x = x;
        this.y = y;
    }
}