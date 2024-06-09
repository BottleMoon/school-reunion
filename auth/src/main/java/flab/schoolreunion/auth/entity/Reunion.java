package flab.schoolreunion.auth.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Reunion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private School school;

    private Integer grade;

    private Integer year;
}