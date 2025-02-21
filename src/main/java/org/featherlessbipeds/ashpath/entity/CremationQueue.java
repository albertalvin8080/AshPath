package org.featherlessbipeds.ashpath.entity;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "cremation_queue")
@DiscriminatorValue("CREMATIONQUEUE")
@NamedQueries({
        @NamedQuery(
                name = "CremationQueue.FindByEnteredDate",
                query = "SELECT cq FROM CremationQueue cq WHERE cq.enteredDate = :enteredDate"
        ),
})
public class CremationQueue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cremation_queue_id")
    private Long id;

    @OneToMany(mappedBy = "cremationQueue", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Deceased> deceasedSet;

    @ManyToMany(mappedBy = "cremationQueueSet", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Necrotomist> necrotomistSet;

    @Column(name = "entered_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date enteredDate;

    public CremationQueue() {
        this.deceasedSet = new HashSet<>();
        this.necrotomistSet = new HashSet<>();
    }

    public CremationQueue(Date enteredDate) {
        this();
        this.enteredDate = enteredDate;
    }

    public Long getId() {
        return id;
    }

    public Set<Deceased> getDeceasedSet() {
        return Collections.unmodifiableSet(deceasedSet);
    }

    public Set<Necrotomist> getNecrotomistSet() {
        return Collections.unmodifiableSet(necrotomistSet);
    }

    public Date getEnteredDate() {
        return enteredDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEnteredDate(Date enteredDate) {
        this.enteredDate = enteredDate;
    }


    public void addDeceased(Deceased deceased) {
        deceasedSet.add(deceased);
        deceased.setCremationQueue(this);
    }

    public void removeDeceased(Deceased deceased) {
        deceasedSet.remove(deceased);
        deceased.setCremationQueue(null);
    }

    public void addNecrotomist(Necrotomist necrotomist) {
        necrotomistSet.add(necrotomist);
        necrotomist.getCremationQueueSet().add(this);
    }

    public void removeNecrotomist(Necrotomist necrotomist) {
        necrotomistSet.remove(necrotomist);
        necrotomist.getCremationQueueSet().remove(this);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CremationQueue other = (CremationQueue) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "CremationQueue{id=" + id + ", enteredDate=" + enteredDate + "}";
    }
}