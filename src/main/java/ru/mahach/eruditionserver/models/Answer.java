package ru.mahach.eruditionserver.models;

import javax.persistence.*;

@Entity
@Table(name = "answers")
public class Answer {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "answers_id_seq"
    )
    @SequenceGenerator(
            name = "answers_id_seq",
            sequenceName = "answers_id_seq"
    )
    @Column(
            name = "id",
            nullable = false,
            unique = true,
            insertable = false,
            updatable = false
    )
    private Long id;

    @Column(
            name = "text"
    )
    private String text;

    @Column(
            name = "question"
    )
    private Long question;
}
