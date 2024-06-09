package flab.schoolreunion.auth.entity;

import jakarta.persistence.*;

@Entity
public class ReunionMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    private Reunion reunion;
}