package com.export.domains.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor
@Entity @Setter @Getter
@Table(name = "role")
public class Role implements Serializable {

    private static final long serialVersionUID = 372492376576347L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    // private String secureId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;
}
