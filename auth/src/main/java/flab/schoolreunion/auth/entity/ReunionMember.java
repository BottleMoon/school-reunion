package flab.schoolreunion.board.entity;

import jakarta.persistence.*;

@Entity
public class SchoolMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private School school;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
}
