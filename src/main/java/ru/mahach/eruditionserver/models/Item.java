package ru.mahach.eruditionserver.models;

import javax.persistence.*;

@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "items_id_seq"
    )
    @SequenceGenerator(
            name = "items_id_seq",
            sequenceName = "items_id_seq"
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
            name = "name",
            nullable = false,
            length = 50
    )
    private String name;

    @Column(
            name = "image_path"
    )
    private String imagePath;
}
