package org.featherlessbipeds.ashpath.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "deceased")
// Dead guy
public class Deceased
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deceased_id")
    private Long id;
    
    @Column(name = "deceased_name", nullable = false)
    private String name;

    @Column(name = "deceased_cause_of_death", nullable = false)
    private String causeOfDeath;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deceased_birth_date", nullable = false)
    private Date birthDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deceased_death_date", nullable = false)
    private Date deathDate;
    
    @OneToOne
    @JoinColumn(name = "grave_id", nullable = false)
    private Grave grave;

    @ManyToOne
    @JoinColumn(name = "cremation_queue_id", nullable = true)
    private CremationQueue cremationQueue;
    
    @ManyToOne
    @JoinColumn(name = "necrotomist_id")
    private Necrotomist necrotomist;
}
