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

@Entity
@Table(name = "deceased")
@Getter
@Setter
// Dead guy
public class Deceased
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deceased_id")
    private Long id;
    
    @Column(name = "deceased_name")
    private String name;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deceased_birth_date")
    private Date birthDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deceased_death_date")
    private Date deathDate;
    
    @OneToOne
    @JoinColumn(name = "grave_id")
    private Grave grave;

    @ManyToOne
    @JoinColumn(name = "cremation_queue_id")
    private CremationQueue cremationQueue;

    @ManyToMany // Remember, strong side
    @JoinTable(
            name = "deceased_necrotomist",
            joinColumns = @JoinColumn(name = "id_deceased"),
            inverseJoinColumns = @JoinColumn(name = "id_necrotomist")
    )
    private Set<Necrotomist> necrotomistSet;
}
