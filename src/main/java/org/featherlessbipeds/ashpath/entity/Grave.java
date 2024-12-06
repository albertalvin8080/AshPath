package org.featherlessbipeds.ashpath.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "grave")
@Getter
@Setter
public class Grave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grave_id")
    private Long id;
    
    @Column(name = "grave_location")
    private String location; // row number in a cemetery?

    @OneToOne(mappedBy = "grave")
    private Deceased deceased;    
}
