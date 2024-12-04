package org.featherlessbipeds.ashpath.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "necrotomist")
// The guy who performs autopsies and stuff
public class Necrotomist
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "necrotomist_id")
    private Long id;

    @Column(name = "necrotomist_specialization", nullable = false)
    private String specialization; // ex: "Autopsy technician"

    @ManyToMany
    @JoinTable(
            name = "necrotomist_cremation_queue",
            joinColumns = @JoinColumn(name = "necrotomist_id"),
            inverseJoinColumns = @JoinColumn(name = "cremation_queue_id")
    )
    private Set<CremationQueue> cremationQueueSet;

    @OneToMany(mappedBy = "necrotomist")
    private Set<Deceased> deceasedSet;
}
