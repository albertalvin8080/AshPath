package org.featherlessbipeds.ashpath.entity;

import jakarta.persistence.CascadeType;
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
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
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

    @Column(name = "necrotomist_name")
    private String name;

    @Column(name = "necrotomist_specialization", nullable = false)
    private String specialization; // ex: "Autopsy technician"

    @Setter(AccessLevel.NONE)
    @ManyToMany
    @JoinTable(
            name = "necrotomist_cremation_queue",
            joinColumns = @JoinColumn(name = "necrotomist_id"),
            inverseJoinColumns = @JoinColumn(name = "cremation_queue_id")
    )
    private Set<CremationQueue> cremationQueueSet;

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "necrotomist")
    private Set<Deceased> deceasedSet;

    public void addCremationQueue(CremationQueue queue)
    {
        if (this.cremationQueueSet == null)
        {
            this.cremationQueueSet = new HashSet<>();
        }
        this.cremationQueueSet.add(queue);
        queue.getNecrotomistSet().add(this);
    }

    public void addDeceased(Deceased deceased)
    {
        if (this.deceasedSet == null)
        {
            this.deceasedSet = new HashSet<>();
        }
        this.deceasedSet.add(deceased);
        deceased.setNecrotomist(this);
    }

}
