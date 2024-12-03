package org.featherlessbipeds.ashpath.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "necrotomist")
@Getter
@Setter
// The guy who performs autopsies and shit
public class Necrotomist
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "necrotomist_id")
    private Long id;
    
    @Column(name = "necrotomist_specialization")
    private String specialization; // ex: "Autopsy technician"
    
    @ManyToOne
    @JoinColumn(name = "cremation_queue_id")
    private CremationQueue cremationQueue;
    
    @ManyToMany(mappedBy = "necrotomistSet")
    private Set<Deceased> deceasedSet;
}
