package com.example.OrdforandeLista.entities;

import jakarta.persistence.*;

@Entity
public class TagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tag_id;

    @Column(name="tag_name")
    private String tagName;
}

